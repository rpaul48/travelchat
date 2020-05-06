package edu.brown.cs.student.chat.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class that handles requests for activities.
 */
public class BrowseActivitiesHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response)
      throws JSONException, UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = request.queryMap();
    String errorMsg = "";
    Map<String, Attraction> attractionsMap = new HashMap<>();

    double lat;
    double lon;
    try {
      String[] locationStrings = qm.value("location").split(" ");

      lat = Double.parseDouble(locationStrings[0]);
      lon = Double.parseDouble(locationStrings[1]);
    } catch (NumberFormatException nfe) {
      errorMsg = "Latitude or longitude is not a number.";
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors",
          errorMsg);
      return new JSONObject(variables);
    }

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

      errorMsg = paramsAreValid(params);
      // Parameters are invalid.
      if (!errorMsg.equals("")) {
        System.out.println(errorMsg);
        Map<String, String> variables = ImmutableMap.of("activities_result", "",
            "activities_errors", errorMsg);
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

    StringBuilder sb = new StringBuilder();
    if (attractionsMap.isEmpty()) {
      sb.append("No matching result.");
    } else {
      List<Attraction> attractionsList = new ArrayList<Attraction>(attractionsMap.values());
      for (int i = 0; i < attractionsList.size() - 1; i++) {
        // Skip this attraction if it has distance greater than specified max distance.
        if (attractionsList.get(i).getDistance() > maxDist) {
          continue;
        }
        sb.append(attractionsList.get(i).toStringHTML()).append("<hr>");
      }
      sb.append(attractionsList.get(attractionsList.size() - 1).toStringHTML());
    }

    Map<String, String> variables = ImmutableMap.of("activities_result", sb.toString(),
        "activities_errors", "");
    return new JSONObject(variables);
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
}
