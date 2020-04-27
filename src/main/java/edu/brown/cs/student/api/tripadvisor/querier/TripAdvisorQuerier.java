package api.tripadvisor.querier;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
// For handling responses.
import api.tripadvisor.request.*;
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

import api.tripadvisor.objects.Attraction;
import api.tripadvisor.objects.Flight;
import api.tripadvisor.objects.Hotel;
import api.tripadvisor.objects.Restaurant;
import com.mashape.unirest.http.exceptions.UnirestException;

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


    public TripAdvisorQuerier(String APIKey, String rapidAPIHost) {
        super(APIKey, rapidAPIHost);
        LOGGER.log(Level.INFO, loggerPrefix + String.format("Querier initalized with key %s and host %s", APIKey,
                rapidAPIHost));
    }

    public List<Restaurant> getRestaurants(RestaurantRequest request) {
        LOGGER.log(Level.INFO, loggerPrefix + "Querying restaurants.");
        String hostURL = "https://tripadvisor1.p.rapidapi.com/restaurants/list-by-latlng";
        this.runQuery(hostURL, request);
        LOGGER.log(Level.INFO, loggerPrefix + "Done querying restaurants.");
        // Placeholder to remove errors
        return Collections.emptyList();
    }

    public List<Attraction> getAttractions(AttractionRequest request) {
        LOGGER.log(Level.INFO, loggerPrefix + "Querying attractions.");
        String hostURL = "https://tripadvisor1.p.rapidapi.com/attractions/list-by-latlng";
        this.runQuery(hostURL, request);
        LOGGER.log(Level.INFO, loggerPrefix + "Done querying attractions.");
        return Collections.emptyList();
    }

    // Note for later: "Ok, I found it - to query for a return flight, need to populate o2=(dest airport),
    // d2=(origin airport), dd2=(return date)."
    public List<Flight> getFlights(FlightRequest sessionRequest, FlightRequest pollRequest) {
        LOGGER.log(Level.INFO, loggerPrefix + "Querying flights.");
        // Create Session -> Poll
        String sessionHostURL = "https://tripadvisor1.p.rapidapi.com/flights/create-session";
        this.runQuery(sessionHostURL, sessionRequest);
        String pollHostURL =  "https://tripadvisor1.p.rapidapi.com/flights/poll";
        this.runQuery(pollHostURL, pollRequest);
        LOGGER.log(Level.INFO, loggerPrefix + "Done querying flights.");
        return Collections.emptyList();
    }

    public List<Hotel> getHotels(HotelRequest request) {
        LOGGER.log(Level.INFO, loggerPrefix + "Querying hotels.");
        String hostURL = "https://tripadvisor1.p.rapidapi.com/hotels/list-by-latlng";
        this.runQuery(hostURL, request);
        LOGGER.log(Level.INFO, loggerPrefix + "Done querying hotels.");
        return Collections.emptyList();
    }

    /**
     * Runs query through RapidAPI.
     *
     * Still need to figure out a universal request parsing routine -- probably going to use SPRING/URI packages.
     */
    public void runQuery(String hostURL, Request request) {
        // Request headers
        String x_rapidapi_host = this.getAPIHost();
        String x_rapidapi_key = this.getAPIKey();
        /**
         * 1. Parse query parameters from request object for use below.
         * 2. Send a request and handle response (i.e. convert to JSON).
         * 3. TODO: parse out the fields we need.
         */
        ImmutableMap<String, Object> params = ImmutableMap.copyOf(request.getParams());
        try {
            HttpResponse <JsonNode> response = Unirest.get(hostURL)
                    .queryString(params)
                    .header("x-rapidapi-host", x_rapidapi_host)
                    .header("x-rapidapi-key", x_rapidapi_key)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
