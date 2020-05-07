package edu.brown.cs.student.api.tripadvisor.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;

/**
 * Tests RestaurantResponse Class.
 */
public class RestaurantResponseTest {
  @Test
  public void testGetSetRestaurantRequest() throws UnirestException {
    RestaurantResponse resp = new RestaurantResponse(null);
    assertEquals(resp.getRestaurantRequest(), null);

    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    RestaurantRequest req = new RestaurantRequest(params);
    resp.setRestaurantRequest(req);
    assertEquals(resp.getRestaurantRequest(), req);
  }

  @Test
  public void testGetData() throws UnirestException {
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    RestaurantResponse rr = new RestaurantResponse(new RestaurantRequest(params));
    List<Restaurant> restaurants = rr.getData();
    assertTrue(!(restaurants == null || restaurants.isEmpty()));
  }
}