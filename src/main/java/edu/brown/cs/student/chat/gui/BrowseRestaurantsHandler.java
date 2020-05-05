package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class BrowseRestaurantsHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response) throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = request.queryMap();

    // max miles from location; either 1, 2, 5, or 10
    String miles = qm.value("miles");
    // of the form "[lat], [lon]"
    String[] locationStrings = qm.value("location").split(",");
    String lat = locationStrings[0].replaceAll("[^0-9.]", "");
    String lon = locationStrings[1].replaceAll("[^0-9.]", "");

    /*
     * format: a string of cuisine categories of the form "type1,type2,type3";
     * (there are no spaces after commas) options: Any, american, barbecue, chinese,
     * italian, indian, japanese, mexican, seafood, thai
     *
     */
    String cuisine = qm.value("cuisineTypes").toLowerCase();

    // min rating; options: any, "3 stars", "4 stars", or "5 stars"
    String rating = qm.value("rating").replaceAll("[^0-9.]", "");

    /*
     * dietary restrictions; options: "None", "Vegetarian friendly",
     * "Vegan options", "Halal", "Kosher", "Gluten-free options
     */
    String diet = qm.value("diet").toLowerCase();

    Map<String, Object> params = new HashMap<>();
    params.put("limit", Constants.LIMIT); // adjust
    params.put("lang", Constants.LANG); // adjust
    params.put("currency", Constants.CURRENCY); // adjust
    params.put("lunit", Constants.LUNIT); // adjust
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("min_rating", rating);
    params.put("dietary_restrictions", diet);
    params.put("distance", miles);
    params.put("combined_food", cuisine);

    String errorMsg = paramsAreValid(params);
    // Parameters are invalid.
    if (!errorMsg.equals("")) {
      System.out.println(errorMsg);
      Map<String, String> variables = ImmutableMap.of("restaurants_result", "",
          "restaurants_errors", errorMsg);
      return new JSONObject(variables);
    }

    List<Restaurant> restaurants = querier.getRestaurants(new RestaurantRequest(params));

    StringBuilder sb = new StringBuilder();
    if (restaurants.isEmpty()) {
      sb.append("No matching result.");
    } else {
      for (Restaurant restaurant : restaurants) {
        sb.append(restaurant.toString() + "\n-----------------------------\n");
      }
    }

    Map<String, String> variables = ImmutableMap.of("restaurants_result", sb.toString(),
        "restaurants_errors", "");
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

    if (!(latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180)) {
      return "ERROR: Latitude or longitude is out of range.";
    }

    try {
      if (Double.parseDouble((String) params.get("min_rating")) < 0) {
        return "ERROR: Min rating cannot be negative.";
      }
    } catch (NumberFormatException nfe) {
      return "ERROR: Min rating is not a number.";
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
