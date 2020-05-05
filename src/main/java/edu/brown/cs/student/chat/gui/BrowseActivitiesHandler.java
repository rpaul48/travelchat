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

    double lat;
    double lon;
    try {
      String[] locationStrings = qm.value("location").split(",");
      lat = Double.parseDouble(locationStrings[0].replaceAll("[^0-9.]", ""));
      lon = Double.parseDouble(locationStrings[1].replaceAll("[^0-9.]", ""));
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
    String[] actNames = activityTypes.split(",");
    String subcategory = "";
    for (String activity : actNames) {
      subcategory += Constants.ATTRACTION_NAME_TO_CODE.get(activity) + ",";
    }
    subcategory = subcategory.substring(0, subcategory.length() - 1);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT); // adjust
    params.put("lang", Constants.LANG); // adjust
    params.put("currency", Constants.CURRENCY); // adjust
    params.put("lunit", Constants.LUNIT); // adjust
    /*
     * 1 degree is approximately 69 mi; thus, 0.02 degree will give reasonable
     * distance for boundary.
     */
    params.put("tr_latitude", lat + 0.02);
    params.put("tr_longitude", lon + 0.02);
    params.put("bl_latitude", lat - 0.02);
    params.put("bl_longitude", lon - 0.02);
    params.put("subcategory", subcategory);

    List<Attraction> attractions = querier.getAttractions(new AttractionRequest(params));

    StringBuilder sb = new StringBuilder();
    if (attractions.isEmpty()) {
      sb.append("No matching result.");
    } else {
      for (Attraction attraction : attractions) {
        sb.append(attraction.toString() + "\n-----------------------------\n");
      }
    }

    Map<String, String> variables = ImmutableMap.of("activities_result", sb.toString(),
        "activities_errors", "");
    return new JSONObject(variables);
  }
}
