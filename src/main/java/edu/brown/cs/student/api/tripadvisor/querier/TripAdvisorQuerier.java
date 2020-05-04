package edu.brown.cs.student.api.tripadvisor.querier;

import com.mashape.unirest.http.HttpResponse;
// For assigning JSON type
import com.mashape.unirest.http.JsonNode;
// For making GETs
//
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.objects.Flight;
import edu.brown.cs.student.api.tripadvisor.objects.Hotel;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.*;
import edu.brown.cs.student.api.tripadvisor.response.FlightResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A TripAdvisor API querier.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class TripAdvisorQuerier extends Querier {
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final static String loggerPrefix = "TripAdvisor API: ";
  private final static String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
  private final static String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";

  /**
   * Constructor; assigns a key and host and logs initialization.
   */
  public TripAdvisorQuerier() {
    super(x_rapidapi_key, x_rapidapi_host);
    LOGGER.log(Level.INFO, loggerPrefix + String.format("Querier initalized with key %s and host %s",
            x_rapidapi_key, x_rapidapi_host));
  }

  /**
   * Queries and returns a list of restaurants using constraints detailed in a RestaurantRequest.
   * @param request
   * @return
   */
  public List<Restaurant> getRestaurants(RestaurantRequest request) {
    return Collections.emptyList();
  }

  /**
   * Queries and returns a list of attractions using constraints detailed in a AttractionRequest.
   * @param request
   * @return
   */
  public List<Attraction> getAttractions(AttractionRequest request) {
    return Collections.emptyList();
  }

  /**
   * Queries and returns a list of flights using constraints detailed in a FlightRequest.
   * @param flightRequest The object containing all parameters/constraints needed for the querty
   * @return A list of flights matching the given parameters.
   * @throws UnirestException
   */
  public JSONArray getFlights(FlightRequest flightRequest) throws UnirestException {
    LOGGER.log(Level.INFO, loggerPrefix + "Querying flights.");
    // Create Session -> Poll -> Returned parsed response
    FlightResponse flightResponse = new FlightResponse(flightRequest);
    // Serialize and return results
    List<Flight> data = flightResponse.getData();
    LOGGER.log(Level.INFO, loggerPrefix + String.format("Successfully queried %d flights.", data.size()));

    JSONArray flightArray = new JSONArray();
    for (Flight flight : data) {
      JSONObject json = new JSONObject();
      json.put("booking_url", flight.getBookingURL());
      json.put("price", flight.getPrice());
      json.put("carrier", flight.getCarrier());
      flightArray.put(json);
      // We can submit more fields but may not be worth the trouble... this should be informative enough.
    }

    return flightArray;
  }

  /**
   * Queries and returns a list of hotels using constraints detailed in a HotelRequest.
   * @param request
   * @return
   */
  public List<Hotel> getHotels(HotelRequest request) {
    return Collections.emptyList();
  }

  /**
   * DEBUGGING
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
