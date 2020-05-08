package edu.brown.cs.student.chat.gui;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
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
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests PlanMyDayHandler Class (user input type validity).
 */
public class PlanMyDayHandlerTest {
    @Test
    public void testFunctionality() throws UnirestException {
        TripAdvisorQuerier querier = new TripAdvisorQuerier();
        double lat = 38.9959461;
        double lon = -77.0276231;
        double boundaryOffset = Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES;
        // Query restaurants params
        Map<String, Object> restaurantParams = new HashMap<>();
        restaurantParams.put("latitude", lat);
        restaurantParams.put("longitude", lon);
        restaurantParams.put("limit", "30");
        restaurantParams.put("currency", "USD");
        restaurantParams.put("distance", "10");

        // Create request object and get list of restaurants
        RestaurantRequest restaurantRequest = new RestaurantRequest(restaurantParams);
        List<Restaurant> allRestaurants = querier.getRestaurants(restaurantRequest);

        // Query attractions params
        Map<String, Object> attractionParams = new HashMap<>();
        attractionParams.put("lunit", "mi");
        attractionParams.put("currency", "USD");
        attractionParams.put("limit", "30");
        attractionParams.put("distance", "10");
        attractionParams.put("lang", "en_US");
        attractionParams.put("tr_latitude", lat + boundaryOffset);
        attractionParams.put("tr_longitude", lon + boundaryOffset);
        attractionParams.put("bl_latitude", lat - boundaryOffset);
        attractionParams.put("bl_longitude", lon - boundaryOffset);

        // Create request object and get list of attractions
        AttractionRequest attractionRequest = new AttractionRequest(attractionParams);
        List<Attraction> allAttractions = querier.getAttractions(attractionRequest);

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
        startLoc.setLatitude(lat); // In handler, we will get from queryParamsMap
        startLoc.setLongitude(lon); // In handler, we will get from queryParamsMap
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
        double distWeight = Double.parseDouble("1.0"); // In handler, we will get from queryParamsMap
        double priceWeight = Double.parseDouble("1.0"); // In handler, we will get from queryParamsMap
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
            for (ItemVertex pathVertex : path) {
                for (ItemVertex restVertex : restaurantAsNodes) { // Remove from restaurant vertices
                    List<WayEdge> filteredEdges = restVertex.getEdges();
                    filteredEdges.removeIf(edge -> edge.getEnd().equals(pathVertex));
                    restVertex.setEdges(filteredEdges);
                }
            }
            totalPath.addAll(path);
        }
        if (totalPath.size() < 7) totalPath = new LinkedList<>();
        totalPath.forEach(v -> System.out.println("--------------------\n" + v + "\n--------------------"));
        // The list of ItemVertices (restaurants and attractions) that we give to the user.
    }

    @Test
    public void testRemoveFromGraph() {
        Set<ItemVertex> vertexSet = new HashSet<>();

        Restaurant restaurant = new Restaurant();
        restaurant.setName("RESTAURANT");
        ItemVertex v1 = new ItemVertex(restaurant);

        Attraction a1 = new Attraction();
        a1.setName("a1");
        ItemVertex v2 = new ItemVertex(a1);

        Attraction a2 = new Attraction();
        a2.setName("a2");
        ItemVertex v3 = new ItemVertex(a2);

        Attraction a3 = new Attraction();
        a3.setName("a3");
        ItemVertex v4 = new ItemVertex(a3);

        Attraction a4 = new Attraction();
        a4.setName("a4");
        ItemVertex v5 = new ItemVertex(a4);

        vertexSet.add(v1);
        vertexSet.add(v2);
        vertexSet.add(v3);
        vertexSet.add(v4);
        vertexSet.add(v5);

        // These should be deleted
        WayEdge v2v1 = new WayEdge(v2, v1);
        WayEdge v3v1 = new WayEdge(v1, v1);
        WayEdge v4v1 = new WayEdge(v4, v1);
        // These should remain
        WayEdge v1v2 = new WayEdge(v1, v2);
        WayEdge v2v3 = new WayEdge(v1, v3);
        WayEdge v4v2 = new WayEdge(v4, v2);
        WayEdge v5v3 = new WayEdge(v5, v3);

        // Add to edge sets
        v2.addEdge(v2v1);
        v3.addEdge(v3v1);
        v4.addEdge(v4v1);
        v1.addEdge(v1v2);
        v2.addEdge(v2v3);
        v4.addEdge(v4v2);
        v5.addEdge(v5v3);

        // Remove edges
        for (ItemVertex v : vertexSet) {
            List<WayEdge> edges = v.getEdges();
            edges.removeIf(edge -> edge.getEnd().equals(v1));
            v.setEdges(edges);
        }
        // Check total edge set, making sure none lead to v1 (i.e. none have end Item = Restaurant)
        vertexSet.forEach(v -> v.getEdges()
                .forEach(e -> System.out.println(e.getEnd().getItem().getClass())));
    }

    /**
     * Removes a vertex from the graph. Specifically, we remove all incoming edges to a node.
     * This is necessary after we add nodes to the user's path, as we do not want to add the same activity twice.
     *
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
    public List<Restaurant> getRandomRestaurants(List<Restaurant> list, int totalItems) {
        Random rand = new Random();

        List<Restaurant> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            int randIndex = rand.nextInt(list.size());

            newList.add(list.get(randIndex));

            list.remove(randIndex);
        }
        return newList;
    }

    @Test
    public void testCorrectParams() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("limit", Constants.LIMIT);
        fields.put("lang", Constants.LANG);
        fields.put("currency", Constants.CURRENCY);
        fields.put("latitude", "12.91285");
        fields.put("longitude", "100.87808");

        assertEquals(paramsAreValid(fields), "");
    }

    @Test
    public void testMissingLat() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("limit", Constants.LIMIT);
        fields.put("lang", Constants.LANG);
        fields.put("currency", Constants.CURRENCY);
        fields.put("longitude", "100.87808");

        assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is missing.");
    }

    @Test
    public void testMissingLon() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("limit", Constants.LIMIT);
        fields.put("lang", Constants.LANG);
        fields.put("currency", Constants.CURRENCY);
        fields.put("latitude", "12.91285");

        assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is missing.");
    }

    @Test
    public void testLatLonNotNumber() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("limit", Constants.LIMIT);
        fields.put("lang", Constants.LANG);
        fields.put("currency", Constants.CURRENCY);
        fields.put("latitude", "a");
        fields.put("longitude", "hello");

        assertEquals(paramsAreValid(fields), "ERROR: Latitude or longitude is not a number.");
    }

    /**
     * Checks if each element in the params is valid and is in the correct
     * type/format.
     *
     * @param params parameters
     * @return "" if all params are valid, error messages otherwise
     */
    public String paramsAreValid(Map<String, Object> params) {
        // Latitude and longitude are required parameters. Query cannot be run without
        // them.
        if (!params.containsKey("latitude") || !params.containsKey("longitude")) {
            return "ERROR: Latitude or longitude is missing.";
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble((String) params.get("latitude"));
            longitude = Double.parseDouble((String) params.get("longitude"));
        } catch (NumberFormatException nfe) {
            return "ERROR: Latitude or longitude is not a number.";
        }

        if (!(latitude >= Constants.MIN_LATITUDE && latitude <= Constants.MAX_LATITUDE
                && longitude >= Constants.MIN_LONGITUDE && longitude <= Constants.MAX_LONGITUDE)) {
            return "ERROR: Latitude or longitude is out of range.";
        }

        /*
         * Other variables for Attraction or Restaurant querying do not need to be
         * checked as they're options from dropdown menus that we know are all valid.
         */

        return "";
    }
}
