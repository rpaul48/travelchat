package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

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
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = request.queryMap();
    String errorMsg = "";
    StringBuilder sb = new StringBuilder();
    Map<String, Attraction> attractionsMap = new HashMap<>();

    double lat;
    double lon;
    try {
      // of the form "[lat], [lon]"
      String[] locationStrings = qm.value("location").split(" ");
      lat = Double.parseDouble(locationStrings[0]);
      lon = Double.parseDouble(locationStrings[1]);
    } catch (NumberFormatException nfe) {
      errorMsg = "Latitude or longitude is not a number.";
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("planMyDay_result", "", "planMyDay_errors",
          errorMsg);
      return new JSONObject(variables);
    }

    // format: 2020-05-30 (year-month-day)
    String date = qm.value("date");

    // max distance in miles; nonnegative integer
    String maxDist = qm.value("maxDist");

    /*
     * a string of cuisine categories of the form "type1,type2,type3"; (there are no
     * spaces after commas) options: Any, american, barbecue, chinese, italian,
     * indian, japanese, mexican, seafood, thai
     */
    String[] cuisinesArr = qm.value("cuisineTypes").toLowerCase().split(",");
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

    // Query attractions.
    for (String activity : activities) {
      Map<String, Object> params = new HashMap<>();
      params.put("limit", Constants.LIMIT);
      params.put("lang", Constants.LANG);
      params.put("currency", Constants.CURRENCY);
      params.put("lunit", Constants.LUNIT);
      params.put("tr_latitude", lat + Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES);
      params.put("tr_longitude", lon + Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES);
      params.put("bl_latitude", lat - Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES);
      params.put("bl_longitude", lon - Constants.LAT_LON_BOUNDARY_OFFSET_10_MILES);
      params.put("subcategory", activity);

      errorMsg = paramsAreValidAttractions(params);
      // Parameters are invalid.
      if (!errorMsg.equals("")) {
        System.out.println(errorMsg);
        Map<String, String> variables = ImmutableMap.of("planMyDay_result", "", "planMyDay_errors",
            errorMsg);
        return new JSONObject(variables);
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
      sb.append("No matching result.");
    } else {
      sb.append("ATTRACTIONS:<br><br>");
      for (Attraction attraction : attractionsMap.values()) {
        sb.append(attraction.toStringHTML() + "<hr>");
      }
    }

    // Query restaurants.
    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT);
    params.put("lang", Constants.LANG);
    params.put("currency", Constants.CURRENCY);
    params.put("lunit", Constants.LUNIT);
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("distance", maxDist);
    params.put("combined_food", cuisines);

    errorMsg = paramsAreValidRestaurants(params);
    // Parameters are invalid.
    if (!errorMsg.equals("")) {
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("planMyDay_result", "", "planMyDay_errors",
          errorMsg);
      return new JSONObject(variables);
    }

    List<Restaurant> restaurants = querier.getRestaurants(new RestaurantRequest(params));

    if (restaurants.isEmpty()) {
      sb.append("No matching result.");
    } else {
      sb.append("<br><br>RESTAURANTS:<br><br>");
      for (Restaurant restaurant : restaurants) {
        sb.append(restaurant.toStringHTML() + "<hr>");
      }
    }

    Map<String, String> variables = ImmutableMap.of("planMyDay_result", sb.toString(),
        "planMyDay_errors", "");
    return new JSONObject(variables);
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
