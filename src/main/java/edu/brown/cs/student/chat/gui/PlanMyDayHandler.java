package edu.brown.cs.student.chat.gui;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Item;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;
import edu.brown.cs.student.plan_my_day.distance.Penalizer;
import edu.brown.cs.student.plan_my_day.distance.PlanMyDayPenalizer;
import edu.brown.cs.student.plan_my_day.graph.ItemVertex;
import edu.brown.cs.student.plan_my_day.graph.PlanMyDayGraph;
import edu.brown.cs.student.plan_my_day.graph.WayEdge;
import edu.brown.cs.student.plan_my_day.shortest_path_finder.AStar;
import edu.brown.cs.student.plan_my_day.shortest_path_finder.ShortestPathFinder;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

/**
 * Class that handles requests for activities and restaurants for PlanMyDay
 * feature.
 */
public class PlanMyDayHandler implements Route {
    @Override
    public JSONArray handle(Request request, Response response) throws Exception {
        try {
            QueryParamsMap qm = request.queryMap();
            String[] locationStrings = qm.value("location").split(" ");
            double lat = Double.parseDouble(locationStrings[0]);
            double lon = Double.parseDouble(locationStrings[1]);
            List<Restaurant> allRestaurants = queryRestaurants(qm);
            List<Attraction> allAttractions = queryActivities(qm);
            if (allRestaurants == null || allAttractions == null) {
                System.out.println("ERROR: Could not query restaurants and/or attractions.");
                return new JSONArray();
            }
            // Pick three random restaurants from the list we queried
            List<Restaurant> randomRestaurants = getRandomRestaurants(allRestaurants, 3);

            // Create Graph, passing in the 3 random restaurants and all attractions
            PlanMyDayGraph graph = new PlanMyDayGraph(randomRestaurants, allAttractions);
            Map<ItemVertex, List<WayEdge>> graphMap = graph.buildGraph(); // We don't use this map -- will prob delete.

            // Retrieves the 3 restaurants, as nodes
            List<ItemVertex> restaurantAsNodes = graph.getRestaurantsAsNodes();

            // Create starting location node and create edges from and to start: start -> restaurant1, restaurant3 -> start
            Attraction startLoc = new Attraction(); // Dummy object -- start may/may not be an attraction
            startLoc.setName("STARTING_LOCATION");
            startLoc.setLatitude(lat);
            startLoc.setLongitude(lon);
            ItemVertex startLocVertex = new ItemVertex(startLoc);
            WayEdge firstRestaurantEdge = new WayEdge(startLocVertex, restaurantAsNodes.get(0)); // start -> restaurant1
            WayEdge lastRestaurantEdge = new WayEdge(restaurantAsNodes.get(restaurantAsNodes.size() - 1), startLocVertex); // restaurant3 -> start

            // Adding edges above to their proper edge sets
            startLocVertex.addEdge(firstRestaurantEdge);
            graphMap.put(startLocVertex, startLocVertex.getEdges());
            restaurantAsNodes.get(restaurantAsNodes.size() - 1).addEdge(lastRestaurantEdge);

            // ALGORITHM
            List<ItemVertex> totalPath = new LinkedList<>(); // start -> rest1 -> ... -> last attraction or rest3 -> start
            totalPath.add(startLocVertex);
            double distWeight = Double.parseDouble(".5"); // In handler, we will get from queryParamsMap
            double priceWeight = Double.parseDouble("2"); // In handler, we will get from queryParamsMap
            Penalizer<ItemVertex> penalizer = new PlanMyDayPenalizer(distWeight, priceWeight); // A* penalty term
            ShortestPathFinder<ItemVertex, WayEdge> aStar = new AStar<>(penalizer); // A* searcher
            // Create the linked list we'll search over
            List<ItemVertex> pathLinkedList = new LinkedList<>();
            pathLinkedList.add(startLocVertex);
            pathLinkedList.addAll(restaurantAsNodes);
            pathLinkedList.add(startLocVertex);

            // Pathfinding.
            for (int i = 1; i < pathLinkedList.size(); i++) {
                ItemVertex source = pathLinkedList.get(i - 1);
                ItemVertex target = pathLinkedList.get(i);
                aStar.findShortestPath(source, target);
                List<ItemVertex> path = aStar.getShortestPath();
                path.forEach(v -> removeFromGraph(v, graphMap)); // Ensures we don't revisit the same attraction
                totalPath.addAll(path);
            }

            JSONArray pathArray = new JSONArray();
            // First and last element are always startingLocation -- ignore.
            for (ItemVertex itemVertex : totalPath.subList(1, totalPath.size() - 1)) {
                // Get item
                Item item = itemVertex.getItem();
                JSONObject json = new JSONObject();
                json.put("name", item.getName());
                json.put("lat", item.getLatitude());
                json.put("lon", item.getLongitude());
                json.put("location_string", item.getLocationString());
                json.put("photo_url", item.getPhotoUrl());
                // Put JSON into array
                pathArray.put(json);
            }
            return pathArray;
        } catch (Exception e) {
            System.err.println("ERROR: An error occurred browsing flights. Printing stack trace:");
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * Removes a vertex from the graph. Specifically, we remove all incoming edges to a node.
     * This is necessary after we add nodes to the user's path, as we do not want to add the same activity twice.
     * @param toRemove The vertex (i.e. activity) to remove.
     * @param graphMap The graph (represented via a Map)
     */
    private void removeFromGraph(ItemVertex toRemove, Map<ItemVertex, List<WayEdge>> graphMap) {
        for (ItemVertex v : graphMap.keySet()) {
            List<WayEdge> filteredEdges = v.getEdges();
            filteredEdges.removeIf(edge -> edge.getEnd().equals(toRemove));
            v.setEdges(filteredEdges);
            }
        }

    /*
     * Picks a set number of Restaurants randomly from list.
     */
    private List<Restaurant> getRandomRestaurants(List<Restaurant> list, int totalItems) {
        Random rand = new Random();

        List<Restaurant> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            int randIndex = rand.nextInt(list.size());

            newList.add(list.get(randIndex));

            list.remove(randIndex);
        }
        return newList;
    }

    /**
     * Returns the query results of all Attractions.
     *
     * @param queryParamsMap - queryParamsMap that has user input
     * @return list of all possible Attractions
     * @throws UnirestException - thrown with query error
     */
    private List<Attraction> queryActivities(QueryParamsMap queryParamsMap) throws UnirestException {
        TripAdvisorQuerier querier = new TripAdvisorQuerier();
        String errorMsg = "";
        Map<String, Attraction> attractionsMap = new HashMap<>();

        String[] locationStrings = queryParamsMap.value("location").split(" ");
        double lat = Double.parseDouble(locationStrings[0]);
        double lon = Double.parseDouble(locationStrings[1]);

        double boundaryOffset = 0.0;
        int maxDist = Integer.parseInt(queryParamsMap.value("maxDist"));
        if (maxDist == 1) {
            boundaryOffset = Constants.LAT_LON_BOUNDARY_OFFSET_1_MILES;
        } else if (maxDist == 2) {
            boundaryOffset = Constants.LAT_LON_BOUNDARY_OFFSET_2_MILES;
        } else if (maxDist == 5) {
            boundaryOffset = Constants.LAT_LON_BOUNDARY_OFFSET_5_MILES;
        } else if (maxDist == 10) {
            boundaryOffset = Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES;
        }

        /*
         * a string of activity categories of the form "type1,type2,type3"; (there are
         * no spaces after commas) options: All, Boat Tours & Water Sports, Fun & Game,
         * Nature & Parks, Sights & Landmarks, Shopping, Transportation, Museums,
         * Outdoor Activities, Spas & Wellness, Classes & Workshops, Tours, Nightlife
         */
        String activityTypes = queryParamsMap.value("activityTypes");
        String[] activities = activityTypes.split(",");
        for (int i = 0; i < activities.length; i++) {
            /*
             * Can only run query using unique code corresponding to each activity type
             * (attraction subcategory). Conversion from activity type name to code is
             * necessary.
             */
            activities[i] = Constants.ATTRACTION_SUBCATEGORY_TO_CODE.get(activities[i]);
        }

        /*
         * Attraction query based on different subcategories, or types, cannot be run
         * all at once on the API. Only one activity type is allowed per query.
         * Therefore need to run multiple queries for multiple activity types, while
         * making sure that there are no duplicates of Attraction.
         */
        for (String activity : activities) {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", Constants.LIMIT);
            params.put("lang", Constants.LANG);
            params.put("currency", Constants.CURRENCY);
            params.put("lunit", Constants.LUNIT);
            // Defined lat-lon boundary to search from using the predetermined offset.
            params.put("tr_latitude", lat + boundaryOffset);
            params.put("tr_longitude", lon + boundaryOffset);
            params.put("bl_latitude", lat - boundaryOffset);
            params.put("bl_longitude", lon - boundaryOffset);
            params.put("subcategory", activity);

            errorMsg = paramsAreValidAttractions(params);
            // Parameters are invalid.
            if (!errorMsg.equals("")) {
                System.out.println(errorMsg);
                return null;
            }

            List<Attraction> attractions = querier.getAttractions(new AttractionRequest(params));
            for (Attraction attraction : attractions) {
                /*
                 * To avoid duplicates, check if attraction has already been seen and thus
                 * should not be added again.
                 */
                if (!attractionsMap.containsKey(attraction.getName())) {
                    attractionsMap.put(attraction.getName(), attraction);
                }
            }
        }

        if (attractionsMap.isEmpty()) {
            System.out.println("ERROR: Could not find activities for PlanMyDay.");
            return null;
        }

        List<Attraction> attractionsList = new ArrayList<>(attractionsMap.values());
        List<Attraction> correctAttrs = new ArrayList<>();
        for (Attraction attraction : attractionsList) {
            // Skip this attraction if it has distance greater than specified max distance.
            if (attraction.getDistance() > maxDist) {
                continue;
            }

            correctAttrs.add(attraction);
        }

        if (correctAttrs.isEmpty()) {
            System.out.println("ERROR: Could not find activities for PlanMyDay.");
            return null;
        }

        return correctAttrs;
    }

    /**
     * Returns the query results of all Restaurants.
     *
     * @param queryParamsMap - queryParamsMap that has user input
     * @return list of all possible Restaurants
     * @throws UnirestException - thrown with query error
     */
    private List<Restaurant> queryRestaurants(QueryParamsMap queryParamsMap) throws UnirestException {
        TripAdvisorQuerier querier = new TripAdvisorQuerier();

        // max miles from location; either 1, 2, 5, or 10
        String miles = queryParamsMap.value("maxDist");

        // of the form "[lat] [lon]"
        String[] locationStrings = queryParamsMap.value("location").split(" ");
        String lat = locationStrings[0];
        String lon = locationStrings[1];

        /*
         * format: a string of cuisine categories of the form "type1,type2,type3";
         * (there are no spaces after commas) options: Any, american, barbecue, chinese,
         * italian, indian, japanese, mexican, seafood, thai
         *
         */
        String[] cuisinesArr = queryParamsMap.value("cuisineTypes").toLowerCase().split(",");
        String cuisines = "";
        if (cuisinesArr.length != 0) {
            for (int i = 0; i < cuisinesArr.length; i++) {
                /*
                 * Can only run query using unique code corresponding to each cuisine type.
                 * Conversion from cuisine type name to code is necessary.
                 */
                if (Constants.CUISINE_TYPE_TO_CODE.containsKey(cuisinesArr[i])) {
                    cuisines += Constants.CUISINE_TYPE_TO_CODE.get(cuisinesArr[i]) + ",";
                }
            }
            cuisines = cuisines.substring(0, cuisines.length() - 1);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("limit", Constants.LIMIT);
        params.put("lang", Constants.LANG);
        params.put("currency", Constants.CURRENCY);
        params.put("lunit", Constants.LUNIT);
        params.put("latitude", lat);
        params.put("longitude", lon);
        params.put("distance", miles);
        params.put("combined_food", cuisines);

        String errorMsg = paramsAreValidRestaurants(params);
        // Parameters are invalid.
        if (!errorMsg.equals("")) {
            System.out.println(errorMsg);
            return null;
        }

        List<Restaurant> restaurants = querier.getRestaurants(new RestaurantRequest(params));

        if (restaurants.isEmpty()) {
            System.out.println("ERROR: Could not find restaurants for PlanMyDay.");
            return null;
        }
        return restaurants;
    }

    /**
     * Checks if each element in the params is valid and is in the correct
     * type/format.
     *
     * @param params parameters
     * @return "" if all params are valid, error messages otherwise
     */
    private String paramsAreValidAttractions(Map<String, Object> params) {
        // Latitude and longitude are required parameters. Query cannot be run without
        // them.
        if (!params.containsKey("tr_latitude") || !params.containsKey("tr_longitude")
                || !params.containsKey("bl_latitude") || !params.containsKey("bl_longitude")) {
            return "ERROR: Latitude or longitude is missing.";
        }

        double trLatitude = ((Double) params.get("tr_latitude"));
        double trLongitude = ((Double) params.get("tr_longitude"));
        double blLatitude = ((Double) params.get("bl_latitude"));
        double blLongitude = ((Double) params.get("bl_longitude"));

        if (!(blLatitude >= Constants.MIN_LATITUDE && trLatitude <= Constants.MAX_LATITUDE
                && blLongitude >= Constants.MIN_LONGITUDE && trLongitude <= Constants.MAX_LONGITUDE)) {
            return "ERROR: Latitude or longitude is out of range.";
        }

        return "";
    }

    /**
     * Checks if each element in the params is valid and is in the correct
     * type/format.
     *
     * @param params parameters
     * @return "" if all params are valid, error messages otherwise
     */
    private String paramsAreValidRestaurants(Map<String, Object> params) {
        // Latitude and longitude are required parameters. Query cannot be run without
        // them.
        if (!params.containsKey("latitude") || !params.containsKey("longitude")) {
            return "ERROR: Latitude or longitude is missing.";
        }

        try {
            if (Double.parseDouble((String) params.get("distance")) < 0) {
                return "ERROR: Distance cannot be negative.";
            }
        } catch (NumberFormatException nfe) {
            return "ERROR: Distance is not a number.";
        }

        return "";
    }
}
