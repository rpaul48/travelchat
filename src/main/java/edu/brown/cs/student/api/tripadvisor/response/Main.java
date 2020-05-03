package edu.brown.cs.student.api.tripadvisor.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;

public class Main {
  public static void main(String[] args) throws UnirestException {
    Map<String, Object> fields = new HashMap<>();
    fields.put("limit", 10); // adjust
    fields.put("lang", "en_US"); // adjust
    fields.put("currency", "USD"); // adjust
    fields.put("lunit", "km"); // adjust
    fields.put("min_rating", 4);
    fields.put("dietary_restrictions", "");
    fields.put("open_now", true);
    fields.put("distance", 2);
    fields.put("restaurant_dining_options", "");
    fields.put("prices_restaurants", "");
    fields.put("restaurant_styles", "");
    fields.put("combined_food", "");
    fields.put("restaurant_mealtype", "");
    fields.put("latitude", 12.91285);
    fields.put("longitude", 100.87808);
    RestaurantResponse restaurantResponse = new RestaurantResponse(new RestaurantRequest(fields));
    List<Restaurant> restaurantsList = restaurantResponse.getData();
    System.out.println(restaurantsList.size());
    for (Restaurant restaurant : restaurantsList) {
      System.out.println(restaurant.toString());
    }
//
//    FoodPreference fp = new FoodPreference();
//    Map<String, Object> fields = new HashMap<>();
//    fields.put("limit", 10); // adjust
//    fields.put("lang", "en_US"); // adjust
//    fields.put("currency", "USD"); // adjust
//    fields.put("lunit", "km"); // adjust
//    fields.put("min_rating", 4);
//    fields.put("dietary_restrictions", "");
//    fields.put("open_now", true);
//    fields.put("distance", 2);
//    fields.put("restaurant_dining_options", "");
//    fields.put("prices_restaurants", "");
//    fields.put("restaurant_styles", "");
//    fields.put("combined_food", "");
//    fields.put("restaurant_mealtype", "");
//    fields.put("latitude", 12.91285);
//    fields.put("longitude", 100.87808);
//    fp.setFields(fields);
//
//    List<Item> restaurantsList = fp.parseResult();
//    System.out.println(restaurantsList.size());
//    for (Item restaurant : restaurantsList) {
//      System.out.println(restaurant.toString());
//    }

  }
}
