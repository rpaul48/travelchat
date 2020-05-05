package edu.brown.cs.student.api.tripadvisor.request;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

/**
 * A request for flights from the TripAdvisor API. Specifically, this handles
 * all querying logic and returns the unprocessed HTTPResponse.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightRequest {
  private Map<String, Object> sessionParams;
  private String sessionID;
  private Map<String, Object> pollParams;

  /**
   * Constructs a new request with a set of session and query (poll) parameters.
   * @param sessionParams
   * @param pollParams
   */
  public FlightRequest(Map<String, Object> sessionParams, Map<String, Object> pollParams) {
    // Query parameters
    this.sessionParams = sessionParams;
    this.pollParams = pollParams;
  }

  /**
   * Runs a query with the parameters given in construction. Will return raw HttpResponse.
   * @return pollResponse A response from the TripAdvisor flights API.
   * @throws UnirestException
   */
  public HttpResponse<JsonNode> run() {
    // Start session and get response
    HttpResponse<JsonNode> response = null;
    try {
      response = this.createSession();
    } catch (UnirestException e) {
      System.out.println("ERROR: An error occurred while starting the flights API session.");
      return null;
    }
    // Get SID then poll
    try {
      sessionID = getSID(response);
      pollParams.put("sid", sessionID);
    } catch (JSONException e) {
      System.out.println("ERROR: An error occurred while parsing the response for search ID.");
      return null;
    }
    //Poll (actually retrieve search results)
    HttpResponse<JsonNode> pollResponse = null;
    try {
      pollResponse = this.pollFlights();
    } catch (UnirestException e) {
      System.out.println("ERROR: An error occurred while polling the flights API.");
      return null;
    }
    return pollResponse;
  }

  /**
   * Accessor of a request's session ID.
   * @return sessionID
   */
  public String getSessionID() {
    return sessionID;
  }

  /**
   * Accessor of parameters used to start the most recent session.
   * @return sessionParams
   */
  public Map<String, Object> getSessionParams() {
    return sessionParams;
  }

  /**
   * Mutator of session parameters.
   * @param sessionParams
   */
  public void setSessionParams(Map<String, Object> sessionParams) {
    this.sessionParams = sessionParams;
  }

  /**
   * Polls the TripAdvisor API for flights.
   * @param sid The ID of the current search
   * @return A response from the TripAdvisor flights API.
   * @throws UnirestException
   */
  public HttpResponse<JsonNode> pollFlights() throws UnirestException {
    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(pollParams);
    String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/poll";
    // Request headers (with free account's key)
    String xRapidapiHost = "tripadvisor1.p.rapidapi.com";
    String xRapidapiKey = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
    // Send a request and handle response
    HttpResponse<JsonNode> response = Unirest.get(hostURL)
            .queryString(immutableParams)
            .header("x-rapidapi-host", xRapidapiHost)
            .header("x-rapidapi-key", xRapidapiKey)
            .asJson();
    return response;
  }

  /**
   * Creates a session with the TripAdvisor flights API.
   * @return A response from the TripAdvisor flights API.
   * @throws UnirestException
   */
  public HttpResponse<JsonNode> createSession() throws UnirestException {
    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(sessionParams);
    String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/create-session";
    // Request headers (with free account's key)
    String xRapidapiHost = "tripadvisor1.p.rapidapi.com";
    String xRapidapiKey = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
    // Send a request and handle response
    HttpResponse<JsonNode> response = Unirest.get(hostURL)
            .queryString(immutableParams)
            .header("x-rapidapi-host", xRapidapiHost)
            .header("x-rapidapi-key", xRapidapiKey)
            .asJson();
    return response;
  }

  /**
   * Parses out the search ID (SID) of a TripAdvisor API response.
   * @param response
   * @return The SID of the response
   */
  public static String getSID(HttpResponse<JsonNode> response) throws JSONException {
    JSONObject obj = new JSONObject(response);
    JSONObject body = obj.getJSONObject("body");
    JSONArray array = body.getJSONArray("array");
    // All fields are in a JSON at index 0, for some reason...
    JSONObject allFields = array.getJSONObject(0);
    // NOW... we can index into the fields
    JSONObject searchParamsJSON = allFields.getJSONObject("search_params");
    return searchParamsJSON.getString("sid");
  }
}
