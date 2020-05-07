package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.graph.GraphNode;
import edu.brown.cs.student.api.graph.PlanMyDayGraph;
import edu.brown.cs.student.api.graph.WayEdge;
import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;
import org.json.JSONObject;
import org.junit.Test;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.util.*;

public class PlanMyDayHandlerTest {

    @Test
    public void test() throws UnirestException {
        // Get list of restaurants and attractions
        List<Restaurant> threeRandomRestaurants = getRandomElement(queryRestaurants(), 3);
        List<Attraction> attractions = queryActivities();
        // Create Graph
        PlanMyDayGraph graph = new PlanMyDayGraph(threeRandomRestaurants, attractions);
        Map<GraphNode, List<WayEdge>> graphMap = graph.buildGraph();
    }

  // GeeksForGeeks
  public List<Restaurant> getRandomElement(List<Restaurant> list,
                                       int totalItems) {
    Random rand = new Random();

    List<Restaurant> newList = new ArrayList<>();
    for (int i = 0; i < totalItems; i++) {

      int randomIndex = rand.nextInt(list.size());

      newList.add(list.get(randomIndex));

      list.remove(randomIndex);
    }
    return newList;
  }

    public List<Attraction> queryActivities() throws UnirestException {
        TripAdvisorQuerier querier = new TripAdvisorQuerier();
        QueryParamsMap qm = null; // REPLACE IN HANDLER
        String errorMsg = "";
        Map<String, Attraction> attractionsMap = new HashMap<>();

        String[] locationStrings = qm.value("location").split(" ");
        double lat = Double.parseDouble(locationStrings[0]);
        double lon = Double.parseDouble(locationStrings[1]);

        double boundaryOffset = 0.0;
        int maxDist = Integer.parseInt(qm.value("miles"));
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
        String activityTypes = qm.value("activityTypes");
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
        return new ArrayList<>(attractionsMap.values());
    }

  public List<Restaurant> queryRestaurants() throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = null; // REPLACE IN HANDLER

    // max miles from location; either 1, 2, 5, or 10
    String miles = qm.value("miles");
    // of the form "[lat] [lon]"
    String[] locationStrings = qm.value("location").split(" ");
    String lat = locationStrings[0];
    String lon = locationStrings[1];
    // min rating; options: any, "2 stars", "3 stars", "4 stars", or "5 stars"
    String rating = qm.value("rating").replaceAll("[^0-5.,]", "");

    if (rating.equals("")) {
      rating = "0";
    }

    /*
     * format: a string of cuisine categories of the form "type1,type2,type3";
     * (there are no spaces after commas) options: Any, american, barbecue, chinese,
     * italian, indian, japanese, mexican, seafood, thai
     *
     */
    String[] cuisinesArr = qm.value("cuisines").toLowerCase().split(",");
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

    // options: any, $, $$-$$$, $$$$
    String price = Constants.RESTAURANT_PRICE_TO_CODE.get(qm.value("price"));

    /*
     * dietary restrictions; options: "None", "Vegetarian friendly",
     * "Vegan options", "Halal", "Gluten-free options
     */
    String[] dietArr = qm.value("diet").toLowerCase().split(",");
    String dietStr = "";
    if (dietArr.length != 0) {
      for (int i = 0; i < dietArr.length; i++) {
        if (Constants.DIETARY_RESTRICTION_TO_CODE.containsKey(dietArr[i])) {
          dietStr += Constants.DIETARY_RESTRICTION_TO_CODE.get(dietArr[i]) + ",";
        }
      }
      dietStr = dietStr.substring(0, dietStr.length() - 1);
    }

    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT);
    params.put("lang", Constants.LANG);
    params.put("currency", Constants.CURRENCY);
    params.put("lunit", Constants.LUNIT);
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("min_rating", rating);
    params.put("dietary_restrictions", dietStr);
    params.put("distance", miles);
    params.put("combined_food", cuisines);
    params.put("prices_restaurants", price);

    List<Restaurant> restaurants = querier.getRestaurants(new RestaurantRequest(params));

    if (restaurants.isEmpty()) {
      System.out.println("ERROR: Could not find restaurants for PlanMyDay.");
      return null;
    }
    return restaurants;
  }
}
