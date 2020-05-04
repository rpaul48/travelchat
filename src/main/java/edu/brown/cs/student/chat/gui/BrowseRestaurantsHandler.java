package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;
import edu.brown.cs.student.api.tripadvisor.response.RestaurantResponse;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class BrowseRestaurantsHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response) throws UnirestException {
    QueryParamsMap qm = request.queryMap();

    // max miles from location; either 1, 2, 5, or 10
    int miles = Integer.parseInt(qm.value("miles"));
    // of the form "[lat], [lon]"
    String location = qm.value("location");
    String[] locationStrings = location.split(",");
    double lat = Double.parseDouble(locationStrings[0].replaceAll("[^0-9]", ""));
    double lon = Double.parseDouble(locationStrings[1].replaceAll("[^0-9]", ""));

    /* TODO: integrate new cuisines format
     * a string of cuisine categories of the form "type1,type2,type3"; (there are
     * no spaces after commas) options: Any, american, barbecue, chinese, italian, indian, japanese,
     * mexican, seafood, thai

    String cuisineTypes = qm.value("cuisineTypes");
     */

    // options: any, american, barbecue, chinese, italian, indian, japanese,
    // mexican, seafood, thai
    String cuisine = qm.value("cuisine");
    cuisine.toLowerCase();
    cuisine.replace(" ", "");

    // min rating; options: any, "3 stars", "4 stars", or "5 stars"
    double rating = Double.parseDouble(qm.value("rating"));

    /*
     * dietary restrictions; options: "None", "Vegetarian friendly",
     * "Vegan options", "Halal", "Kosher", "Gluten-free options
     */
    String diet = qm.value("diet");
    diet.toLowerCase();
    diet.replace(" ", "");

    /*
     * (TODO) create a map/list/html string of restaurants with information to be
     * displayed for all search results, (TODO) and set equal to v1 in variables;
     * append all potential errors into a string and set as v2
     */
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 10); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", lat);
    params.put("longitude", lon);
    params.put("min_rating", rating);
    params.put("dietary_restrictions", diet);
    params.put("distance", miles);
    params.put("combined_food", cuisine);

    RestaurantResponse rr = new RestaurantResponse(new RestaurantRequest(params));
    List<Restaurant> restaurants = rr.getData();

    StringBuilder sb = new StringBuilder();

    for (Restaurant restaurant : restaurants) {
      sb.append(restaurant.toString() + "\n");
    }

    String msg = "";
    if (restaurants.isEmpty()) {
      msg = "No matching result.";
    }

    Map<String, String> variables = ImmutableMap.of("restaurants_result", sb.toString(),
        "restaurants_errors", msg);
    return new JSONObject(variables);
  }
}
