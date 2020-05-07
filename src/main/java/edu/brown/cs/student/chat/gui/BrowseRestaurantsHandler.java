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
  public JSONObject handle(Request request, Response response) {
    try {
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
      StringBuilder cuisines = new StringBuilder();
      if (cuisinesArr.length != 0) {
        for (String s : cuisinesArr) {
          /*
           * Can only run query using unique code corresponding to each cuisine type.
           * Conversion from cuisine type name to code is necessary.
           */
          if (Constants.CUISINE_TYPE_TO_CODE.containsKey(s)) {
            cuisines.append(Constants.CUISINE_TYPE_TO_CODE.get(s)).append(",");
          }
        }
        if (cuisines.length() > 0) {
          cuisines = new StringBuilder(cuisines.substring(0, cuisines.length() - 1));
        }
      }

      // options: any, $, $$-$$$, $$$$
      String price = Constants.RESTAURANT_PRICE_TO_CODE.get(qm.value("price"));

      /*
       * dietary restrictions; options: "None", "Vegetarian friendly",
       * "Vegan options", "Halal", "Gluten-free options
       */
      String[] dietArr = qm.value("diet").toLowerCase().split(",");
      StringBuilder dietStr = new StringBuilder();
      if (dietArr.length != 0) {
        for (String s : dietArr) {
          if (Constants.DIETARY_RESTRICTION_TO_CODE.containsKey(s)) {
            dietStr.append(Constants.DIETARY_RESTRICTION_TO_CODE.get(s)).append(",");
          }
        }
        dietStr = new StringBuilder(dietStr.substring(0, dietStr.length() - 1));
      }

      Map<String, Object> params = new HashMap<>();
      params.put("limit", Constants.LIMIT);
      params.put("lang", Constants.LANG);
      params.put("currency", Constants.CURRENCY);
      params.put("lunit", Constants.LUNIT);
      params.put("latitude", lat);
      params.put("longitude", lon);
      params.put("min_rating", rating);
      params.put("dietary_restrictions", dietStr.toString());
      params.put("distance", miles);
      params.put("combined_food", cuisines.toString());
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
        for (int i = 0; i < restaurants.size() - 1; i++) {
          sb.append(restaurants.get(i).toStringHTML()).append("<hr>");
        }
        sb.append(restaurants.get(restaurants.size() - 1).toStringHTML());
      }

      Map<String, String> variables = ImmutableMap.of("restaurants_result", sb.toString(),
            "restaurants_errors", "");
      return new JSONObject(variables);
    } catch (Exception ex) {
      System.err.println("ERROR: An error occurred while browsing activities. Printing stack:");
      ex.printStackTrace();
    }
    return new JSONObject();
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
