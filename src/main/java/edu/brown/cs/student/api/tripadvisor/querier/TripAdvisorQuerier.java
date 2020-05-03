package edu.brown.cs.student.api.tripadvisor.querier;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
// For assigning JSON type
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Flight;
import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;
import edu.brown.cs.student.api.tripadvisor.request.FlightRequest;
import edu.brown.cs.student.api.tripadvisor.request.HotelRequest;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;
import edu.brown.cs.student.api.tripadvisor.response.AttractionResponse;
import edu.brown.cs.student.api.tripadvisor.response.FlightResponse;
import edu.brown.cs.student.api.tripadvisor.response.HotelResponse;
import edu.brown.cs.student.api.tripadvisor.response.RestaurantResponse;

/**
 * A TripAdvisor API querier.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class TripAdvisorQuerier extends Querier {
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final static String loggerPrefix = "TripAdvisor API: ";

  /**
   * Constructor; assigns a key and host.
   *
   * @param APIKey
   * @param rapidAPIHost
   */
  public TripAdvisorQuerier(String APIKey, String rapidAPIHost) {
    super(APIKey, rapidAPIHost);
    LOGGER.log(Level.INFO, loggerPrefix
        + String.format("Querier initalized with key %s and host %s", APIKey, rapidAPIHost));
  }

  /**
   * Getter of Restaurants.
   *
   * @param restaurantRequest
   * @return
   * @throws UnirestException
   */
  public List<Restaurant> getRestaurants(RestaurantRequest restaurantRequest)
      throws UnirestException {
    LOGGER.log(Level.INFO, loggerPrefix + "Querying restaurants.");
    // Create Session -> Poll -> Returned parsed response
    RestaurantResponse restaurantResponse = new RestaurantResponse(restaurantRequest);
    List<Restaurant> restaurants = restaurantResponse.getData();
    LOGGER.log(Level.INFO,
        loggerPrefix + String.format("Successfully queried %d restaurants.", restaurants.size()));
    return restaurants;
  }

  /**
   * Getter of Attractions.
   *
   * @param attractionRequest
   * @return
   * @throws UnirestException
   */
  public List<Attraction> getAttractions(AttractionRequest attractionRequest)
      throws UnirestException {
    LOGGER.log(Level.INFO, loggerPrefix + "Querying attractions.");
    AttractionResponse attractionResponse = new AttractionResponse(attractionRequest);
    List<Attraction> attractions = attractionResponse.getData();
    LOGGER.log(Level.INFO,
        loggerPrefix + String.format("Successfully queried %d attractions.", attractions.size()));
    return attractions;
  }

  /**
   * Getter of Flights.
   *
   * @param flightRequest
   * @return
   * @throws UnirestException
   */
  public List<Flight> getFlights(FlightRequest flightRequest) throws UnirestException {
    LOGGER.log(Level.INFO, loggerPrefix + "Querying flights.");
    // Create Session -> Poll -> Returned parsed response
    FlightResponse flightResponse = new FlightResponse(flightRequest);
    List<Flight> flights = flightResponse.getData();
    LOGGER.log(Level.INFO,
        loggerPrefix + String.format("Successfully queried %d flights.", flights.size()));
    return flights;
  }

  /**
   * Getter of Hotels.
   *
   * @param hotelRequest
   * @return
   * @throws UnirestException
   */
  public List<Hotel> getHotels(HotelRequest hotelRequest) throws UnirestException {
    LOGGER.log(Level.INFO, loggerPrefix + "Querying hotels.");
    // Create Session -> Poll -> Returned parsed response
    HotelResponse hotelResponse = new HotelResponse(hotelRequest);
    List<Hotel> hotels = hotelResponse.getData();
    LOGGER.log(Level.INFO,
        loggerPrefix + String.format("Successfully queried %d hotels.", hotels.size()));
    return hotels;
  }

  /**
   * DEBUGGING
   *
   * @param response
   */
  public static void printHTTPResponse(HttpResponse<JsonNode> response) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();
    JsonElement je = jp.parse(response.getBody().toString());
    String prettyJsonString = gson.toJson(je);
    System.out.println(prettyJsonString);
  }
}
