package edu.brown.cs.student.chat.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

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
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class that handles requests for activities and restaurants for PlanMyDay
 * feature.
 */
public class PlanMyDayHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response) throws Exception {
    QueryParamsMap qm = request.queryMap();
    String errorMsg = "";
    StringBuilder sb = new StringBuilder();

    List<Restaurant> allRestaurants = queryRestaurants(qm);
    List<Attraction> allAttractions = queryActivities(qm);
    if (allRestaurants == null || allAttractions == null) {
      errorMsg = "ERROR: Cannot find restaurants or activities.";
      Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors",
          errorMsg);
      return new JSONObject(variables);
    }

    // Pick three random Restaurants from all possible Restaurants.
    List<Restaurant> randomRestaurants = getRandomRestaurants(allRestaurants, 3);

    // Create Graph
    PlanMyDayGraph graph = new PlanMyDayGraph(randomRestaurants, allAttractions);
    Map<GraphNode, List<WayEdge>> graphMap = graph.buildGraph();

    /** (TODO) PlanMyDay Algorithm using graphMap here **/
    // Output of PlanMyDay Algorithm
    List<Restaurant> restaurantsInOrder = new ArrayList<>(); // CHANGE
    List<Attraction> attrsInOrder = new ArrayList<>(); // CHANGE

    if (restaurantsInOrder == null || attrsInOrder == null) {
      errorMsg = "ERROR: Cannot find restaurants or activities.";
      Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors",
          errorMsg);
      return new JSONObject(variables);
    }

    /*
     * Order of items recommended for the day: current location -> restaurant for
     * breakfast -> activity 1 -> restaurant for lunch -> activity 2 -> activity 3
     * -> restaurant for dinner -> activity 4
     */
    sb.append("<br><br>Current Location <br><br><hr><br><br><br>");
    sb.append("<strong>Restaurant for Breakfast</strong>");
    sb.append(restaurantsInOrder.get(0).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>First Activity</strong>");
    sb.append(attrsInOrder.get(0).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>Restaurant for Lunch</strong>");
    sb.append(restaurantsInOrder.get(1).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>Second Activity</strong>");
    sb.append(attrsInOrder.get(1).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>Third Activity</strong>");
    sb.append(attrsInOrder.get(2).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>Restaurant for Dinner</strong>");
    sb.append(restaurantsInOrder.get(2).toStringHTML()).append("<hr><br><br><br>");
    sb.append("<strong>Fourth Activity</strong>");
    sb.append(attrsInOrder.get(3).toStringHTML()).append("<hr><br><br><br>");
    sb.append("Current Location <br><br>");

    Map<String, String> variables = ImmutableMap.of("activities_result", sb.toString(),
        "activities_errors", "");
    return new JSONObject(variables);
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

  /**
   * Returns the query results of all Attractions.
   *
   * @param queryParamsMap - queryParamsMap that has user input
   * @return List<Attraction> - list of all possible Attractions
   * @throws UnirestException - thrown with query error
   */
  public List<Attraction> queryActivities(QueryParamsMap queryParamsMap) throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = queryParamsMap;
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

    List<Attraction> attractionsList = new ArrayList<Attraction>(attractionsMap.values());
    List<Attraction> correctAttrs = new ArrayList<>();
    for (int i = 0; i < attractionsList.size(); i++) {
      // Skip this attraction if it has distance greater than specified max distance.
      if (attractionsList.get(i).getDistance() > maxDist) {
        continue;
      }

      correctAttrs.add(attractionsList.get(i));
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
   * @return List<Restaurant> - list of all possible Restaurants
   * @throws UnirestException - thrown with query error
   */
  public List<Restaurant> queryRestaurants(QueryParamsMap queryParamsMap) throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = queryParamsMap;

    // max miles from location; either 1, 2, 5, or 10
    String miles = qm.value("miles");

    // of the form "[lat] [lon]"
    String[] locationStrings = qm.value("location").split(" ");
    String lat = locationStrings[0];
    String lon = locationStrings[1];

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
  public String paramsAreValidAttractions(Map<String, Object> params) {
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
  public String paramsAreValidRestaurants(Map<String, Object> params) {
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
