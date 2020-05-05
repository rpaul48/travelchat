package edu.brown.cs.student.chat.gui;

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
      String[] locationStrings = qm.value("location").split(",");
      lat = Double.parseDouble(locationStrings[0]);
      lon = Double.parseDouble(locationStrings[1]);
    } catch (NumberFormatException nfe) {
      errorMsg = "Latitude or longitude is not a number.";
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("activities_result", "", "activities_errors",
          errorMsg);
      return new JSONObject(variables);
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
      activities[i] = Constants.ATTRACTION_NAME_TO_CODE.get(activities[i]);
    }

    for (String activity : activities) {
      Map<String, Object> params = new HashMap<>();
      params.put("limit", Constants.LIMIT);
      params.put("lang", Constants.LANG);
      params.put("currency", Constants.CURRENCY);
      params.put("lunit", Constants.LUNIT);
      params.put("tr_latitude", lat + Constants.BOUNDARYOFFSET);
      params.put("tr_longitude", lon + Constants.BOUNDARYOFFSET);
      params.put("bl_latitude", lat - Constants.BOUNDARYOFFSET);
      params.put("bl_longitude", lon - Constants.BOUNDARYOFFSET);
      params.put("subcategory", activity);

      errorMsg = paramsAreValid(params);
      // Parameters are invalid.
      if (!errorMsg.equals("")) {
        System.out.println(errorMsg);
        Map<String, String> variables = ImmutableMap.of("restaurants_result", "",
            "restaurants_errors", errorMsg);
        return new JSONObject(variables);
      }

      List<Attraction> attractions = querier.getAttractions(new AttractionRequest(params));
      for (Attraction attraction : attractions) {
        // To avoid adding attractions that belong to two different categories and thus
        // are obtained twice.
        if (!attractionsMap.containsKey(attraction.getName())) {
          attractionsMap.put(attraction.getName(), attraction);
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    if (attractionsMap.isEmpty()) {
      sb.append("No matching result.");
    } else {
      for (Attraction attraction : attractionsMap.values()) {
        sb.append(attraction.toString() + "\n-----------------------------\n");
      }
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

    double latitude = ((Double) params.get("tr_latitude")) - Constants.BOUNDARYOFFSET;
    double longitude = ((Double) params.get("tr_longitude")) - Constants.BOUNDARYOFFSET;

    if (!(latitude >= Constants.MIN_LATITUDE && latitude <= Constants.MAX_LATITUDE
        && longitude >= Constants.MIN_LONGITUDE && longitude <= Constants.MAX_LONGITUDE)) {
      return "ERROR: Latitude or longitude is out of range.";
    }

    return "";
  }
}
