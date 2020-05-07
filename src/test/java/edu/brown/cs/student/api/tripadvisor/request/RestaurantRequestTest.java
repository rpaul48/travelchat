package edu.brown.cs.student.api.tripadvisor.request;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Tests RestaurantRequest class.
 */

public class RestaurantRequestTest {

  @Test
  public void test() throws UnirestException {
    RestaurantRequest req = new RestaurantRequest(null);
    assertEquals(req.getParams(), null);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30);
    params.put("lang", "en_US");
    params.put("currency", "USD");
    params.put("lunit", "km");
    params.put("min_rating", 2.0);
    params.put("dietary_restrictions", "");
    params.put("open_now", true);
    params.put("distance", 2);
    params.put("restaurant_dining_options", "");
    params.put("prices_restaurants", "");
    params.put("restaurant_styles", "");
    params.put("combined_food", "");
    params.put("restaurant_mealtype", "");
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);

    req.setParams(params);
    assertEquals(req.getParams(), params);
  }
}
