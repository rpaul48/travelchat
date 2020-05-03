package edu.brown.cs.student.api.tripadvisor.request;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.tripadvisor.objects.Flight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A request for flights from the TripAdvisor API.
 * Specifically, this handles all querying logic and returns the unprocessed HTTPResponse.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightRequest{
    private Map<String, Object> sessionParams;
    private String sessionID;
    private Map<String, Object> pollParams;

    /**
     *
     * @param sessionParams
     * @param pollParams
     * @throws UnirestException
     */
    public FlightRequest(Map<String, Object> sessionParams, Map<String, Object> pollParams)
            throws UnirestException {
        // Query parameters
        this.sessionParams = sessionParams;
        this.pollParams = pollParams;
    }

    /**
     * Runs a query with the parameters given in construction. Will return raw HttpResponse.
     * @return
     * @throws UnirestException
     */
    public HttpResponse<JsonNode> run() throws UnirestException {
        // Start session and get response
        HttpResponse<JsonNode> response = this.createSession();
        // Get SID then poll
        sessionID = getSID(response);
        //Poll (actually retrieve search results
        HttpResponse<JsonNode> pollResponse = this.pollFlights(sessionID);
        return pollResponse;
    }

    /**
     *
     * @return
     */
    public String getSessionID(){
        return sessionID;
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getSessionParams() {
        return sessionParams;
    }

    /**
     *
     * @param sessionParams
     */
    public void setSessionParams(Map<String, Object> sessionParams) {
        this.sessionParams = sessionParams;
    }

    /**
     *
     * @param sid
     * @return
     * @throws UnirestException
     */
    public HttpResponse<JsonNode> pollFlights(String sid) throws UnirestException {
        ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(pollParams);
        String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/poll";
        // Request headers (with free account's key)
        String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
        String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
        // Send a request and handle response
        HttpResponse <JsonNode> response = Unirest.get(hostURL)
                .queryString(immutableParams)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        return response;
    }

    /**
     *
     * @return
     * @throws UnirestException
     */
    public HttpResponse<JsonNode> createSession() throws UnirestException {
        ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(sessionParams);
        String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/create-session";
        // Request headers (with free account's key)
        String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
        String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
        // Send a request and handle response
        HttpResponse <JsonNode> response = Unirest.get(hostURL)
                .queryString(immutableParams)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        return response;
    }

    /**
     *
     * @param response
     * @return
     */
    public static String getSID(HttpResponse<JsonNode> response) {
        JSONObject obj = new JSONObject(response);
        JSONObject body = obj.getJSONObject("body");
        JSONArray array = body.getJSONArray("array");
        // All fields are in a JSON at index 0, for some reason...
        JSONObject allFields = array.getJSONObject(0);
        // NOW... we can index into the fields
        JSONObject searchParamsJSON = allFields.getJSONObject("search_params");
        String sid = searchParamsJSON.getString("sid");
        return sid;
    }

}
