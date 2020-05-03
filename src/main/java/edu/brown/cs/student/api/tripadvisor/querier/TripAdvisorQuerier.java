package edu.brown.cs.student.api.tripadvisor.querier;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
// For handling responses.
import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
// For assigning JSON type
import com.mashape.unirest.http.JsonNode;
// For making GETs
import com.mashape.unirest.http.Unirest;
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
import edu.brown.cs.student.api.tripadvisor.response.AttractionResponse;
import edu.brown.cs.student.api.tripadvisor.response.FlightResponse;
import edu.brown.cs.student.api.tripadvisor.response.HotelResponse;

import java.net.URLEncoder;
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

    /**
     * Constructor; assigns a key and host.
     * @param APIKey
     * @param rapidAPIHost
     */
    public TripAdvisorQuerier(String APIKey, String rapidAPIHost) {
        super(APIKey, rapidAPIHost);
        LOGGER.log(Level.INFO, loggerPrefix + String.format("Querier initalized with key %s and host %s", APIKey,
                rapidAPIHost));
    }

    /**
     *
     * @param request
     * @return
     */
    public List<Restaurant> getRestaurants(RestaurantRequest request) {
        return Collections.emptyList();
    }

    /**
     *
     * @param request
     * @return
     */
    public List<Attraction> getAttractions(AttractionRequest request) {
        return Collections.emptyList();
    }

    /**
     *
     * @param flightRequest
     * @return
     * @throws UnirestException
     */
    public List<Flight> getFlights(FlightRequest flightRequest) throws UnirestException {
        LOGGER.log(Level.INFO, loggerPrefix + "Querying flights.");
        // Create Session -> Poll -> Returned parsed response
        FlightResponse flightResponse = new FlightResponse(flightRequest);
        HttpResponse<JsonNode> rawResponse = flightRequest.run();
        List<Flight> flights = flightResponse.getData(rawResponse);
        LOGGER.log(Level.INFO, loggerPrefix + String.format("Successfully queried %d flights.", flights.size()));
        return flights;
    }

    /**
     *
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
