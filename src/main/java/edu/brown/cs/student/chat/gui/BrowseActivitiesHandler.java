package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import edu.brown.cs.student.api.tripadvisor.response.AttractionResponse;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class BrowseActivitiesHandler implements Route {
  @Override
  public JSONObject handle(Request request, Response response)
      throws JSONException, UnirestException {
    QueryParamsMap qm = request.queryMap();

    String location = qm.value("location");

    String[] locationStrings = location.split(",");
    double lat = Double.parseDouble(locationStrings[0].replaceAll("[^0-9]", ""));
    double lon = Double.parseDouble(locationStrings[1].replaceAll("[^0-9]", ""));

    Map<String, Integer> nameToCode = new HashMap<>();
    nameToCode.put("Boat Tours & Water Sports", 55);
    nameToCode.put("Fun & Game", 56);
    nameToCode.put("Nature & Parks", 57);
    nameToCode.put("Sights & Landmarks", 47);
    nameToCode.put("Food & Drink", 36);
    nameToCode.put("Shopping", 26);
    nameToCode.put("Transportation", 59);
    nameToCode.put("Museums", 49);
    nameToCode.put("Outdoor Activities", 61);
    nameToCode.put("Spas & Wellness", 40);
    nameToCode.put("Classes & Workshops", 41);
    nameToCode.put("Tours", 42);
    nameToCode.put("Nightlife", 20);
    nameToCode.put("All", 0);

    /*
     * an string of activity categories of the form "type1,type2,type3"; (there are
     * no spaces after commas) options: All, Boat Tours & Water Sports, Fun & Game,
     * Nature & Parks, Sights & Landmarks, Shopping, Transportation, Museums,
     * Outdoor Activities, Spas & Wellness, Classes & Workshops, Tours, Nightlife
     */
    String activityTypes = qm.value("activityTypes");
    String[] actNames = activityTypes.replace(" ", "").split(",");
    String subcategory = "";
    for (String activity : actNames) {
      subcategory += nameToCode.get(activity) + ",";
    }
    subcategory = subcategory.substring(0, subcategory.length() - 1);

    /*
     * (TODO) create a map/list/html string of activities with information to be
     * displayed for all search results, (TODO) and set equal to v1 in variables;
     * append all potential errors into a string and set as v2
     */
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 10); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("tr_latitude", lat + 0.1); // 1 degree is approximately 69 mi;
    params.put("tr_longitude", lon + 0.1);
    params.put("bl_latitude", lat - 0.1);
    params.put("bl_longitude", lon - 0.1);
    params.put("subcategory", subcategory);

    AttractionResponse ar = new AttractionResponse(new AttractionRequest(params));
    List<Attraction> attractions = ar.getData();

    StringBuilder sb = new StringBuilder();

    for (Attraction attraction : attractions) {
      sb.append(attraction.toString() + "\n");
    }

    String msg = "";
    if (attractions.isEmpty()) {
      msg = "No matching result.";
    }

    Map<String, String> variables = ImmutableMap.of("activities_result", sb.toString(),
        "activities_errors", msg);
    return new JSONObject(variables);
  }
}
