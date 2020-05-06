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

/**
 * Class that handles requests for restaurants.
 */
public class BrowseRestaurantsHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response) throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    QueryParamsMap qm = request.queryMap();

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

    String price = Constants.RESTAURANT_PRICE_TO_CODE.get(qm.value("price"));

    /*
     * dietary restrictions; options: "None", "Vegetarian friendly",
     * "Vegan options", "Halal", "Gluten-free options
     */
    String[] dietArr = qm.value("diet").replaceAll("-", " ").toLowerCase().split(",");
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
        sb.append(restaurant.toStringHTML() + Constants.SEPARATOR_HTML);
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

    if (!(latitude >= Constants.MIN_LATITUDE && latitude <= Constants.MAX_LATITUDE
        && longitude >= Constants.MIN_LONGITUDE && longitude <= Constants.MAX_LONGITUDE)) {
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
