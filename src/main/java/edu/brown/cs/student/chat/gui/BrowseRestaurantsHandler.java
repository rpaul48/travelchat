package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import spark.*;

import java.util.Map;

public class BrowseRestaurantsHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();

    //max miles from location; either 1, 2, 5, or 10
    int miles = Integer.parseInt(qm.value("miles"));
    //of the form "[lat], [lon]"
    String location = qm.value("location");
    //options: any, american, barbecue, chinese, italian, indian, japanese, mexican, seafood, thai
    String cuisine = qm.value("cuisine");
    //min rating; options: any, "3 stars", "4 stars", or "5 stars"
    String rating = qm.value("rating");
    //options: $, $$, or $$$
    String price = qm.value("price");
    /*dietary restrictions; options: "None", "Vegetarian friendly", "Vegan options", "Halal",
    "Kosher", "Gluten-free options */
    String diet = qm.value("diet");

    /*(TODO) create a map/list/html string of restaurants with information to be displayed for all search results,
    (TODO) and set equal to v1 in variables; append all potential errors into a string and set as v2*/

    Map<String, String> variables = ImmutableMap.of("restaurants_result", "", "restaurants_errors", "");
    return new JSONObject(variables);
  }
}
