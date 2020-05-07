package edu.brown.cs.student.api.tripadvisor.querier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import edu.brown.cs.student.api.tripadvisor.request.FlightRequest;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;
import edu.brown.cs.student.api.tripadvisor.request.LocationIDRequest;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;

public class TripAdvisorQuerierTest {

  @Test
  public void testGetRestaurants() throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "km"); // adjust
    params.put("min_rating", 4);
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
    List<Restaurant> restaurantsList = querier.getRestaurants(new RestaurantRequest(params));
    assertFalse(restaurantsList.isEmpty());
  }

  @Test
  public void testGetAttractions() throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 30); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "km"); // length unit; adjust
    double lat = 12.91285;
    double lon = 100.87808;
    params.put("tr_latitude", lat + 0.02); // 1 degree is approximately 69 mi;
    params.put("tr_longitude", lon + 0.02);
    params.put("bl_latitude", lat - 0.02);
    params.put("bl_longitude", lon - 0.02);
    params.put("distance", 5);
    List<Attraction> attractionsList = querier.getAttractions(new AttractionRequest(params));
    assertFalse(attractionsList.isEmpty());
  }

  @Test
  public void testGetHotels() throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    Map<String, Object> params = new HashMap<>();
    params.put("limit", 50); // adjust
    params.put("lang", "en_US"); // adjust
    params.put("currency", "USD"); // adjust
    params.put("lunit", "mi"); // adjust
    params.put("latitude", 12.91285);
    params.put("longitude", 100.87808);
    params.put("checkin", "2020-05-08");
    params.put("subcategory", "bb");
    params.put("nights", 2);
    params.put("rooms", 2);
    params.put("hotel_class", 3);
    params.put("longitude", 100.87808);
    List<Hotel> hotelsList = querier.getHotels(new HotelRequest(params));
    assertFalse(hotelsList.isEmpty());
  }

  @Test
  public void testGetFlights() {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    // Session params map
    Map<String, Object> sessionParams = new HashMap<>();
    sessionParams.put("d1", "JFK");
    sessionParams.put("o1", "LAX");
    sessionParams.put("dd1", "2020-06-08");
    sessionParams.put("currency", "USD");
    sessionParams.put("ta", "1");
    sessionParams.put("c", "0");
    sessionParams.put("null", null);

    // Remove any null (i.e. absent) parameters
    sessionParams.values().removeIf(Objects::isNull);

    // Poll params map
    Map<String, Object> pollParams = new HashMap<>();
    pollParams.put("currency", "USD");
    pollParams.put("so", "Sorted by Best Value");

    // Create request object
    FlightRequest flightRequest = new FlightRequest(sessionParams, pollParams);

    // Get flights using querier
    JSONArray flights = querier.getFlights(flightRequest);
    assertFalse(flights.toList().isEmpty());
  }

  @Test
  public void testGetLocationID() throws UnirestException {
    TripAdvisorQuerier querier = new TripAdvisorQuerier();
    String locationID = querier.getLocationID(new LocationIDRequest("Providence"));
    assertEquals(locationID, "60946");
  }
}
