package edu.brown.cs.student.api.tripadvisor.request;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A request for restaurants from the TripAdvisor API. Specifically, this
 * handles all querying logic and returns the unprocessed HTTPResponse.
 */
public class RestaurantRequest {
  private Map<String, Object> params;

  /**
   * This is the constructor for this class.
   *
   * @param params - a Map<String, Object> representing the parameters for
   *               restaurant API querying.
   */
  public RestaurantRequest(Map<String, Object> params) throws UnirestException {
    // Query parameters
    this.params = params;
  }

  /**
   * Runs a query with the parameters given in construction. Will return raw
   * HttpResponse.
   *
   * @return HttpResponse<JsonNode> - response of API query
   * @throws UnirestException
   */
  public HttpResponse<JsonNode> run() throws UnirestException {
    // Latitude and longitude are required parameters. Query cannot be run without
    // them.
    if (!params.containsKey("latitude") || !params.containsKey("longitude")) {
      System.err.println("ERROR: Latitude or longitude missing.");
      return null;
    }

    // If fields is passed directly into constructor, latitude and longitude might
    // not have been assigned.
    double latitude = (double) params.get("latitude");
    double longitude = (double) params.get("longitude");

    if (!(latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180)) {
      System.err.println("ERROR: Latitude or longitude invalid");
      return null;
    }

    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);
    String hostURL = "https://tripadvisor1.p.rapidapi.com/restaurants/list-by-latlng";
    // Request headers (with free account's key)
    String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
    String x_rapidapi_key = "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d";
    // Send a request and handle response

    HttpResponse<JsonNode> response = Unirest.get(hostURL).queryString(immutableParams)
        .header("x-rapidapi-host", x_rapidapi_host).header("x-rapidapi-key", x_rapidapi_key)
        .asJson();

    return response;
  }

  /**
   * Getter of params.
   *
   * @return a Map<String, Object> representing the query parameters.
   */
  public Map<String, Object> getParams() {
    return params;
  }

  /**
   * Setter of params.
   *
   * @param params - a Map<String, Object> representing the parameters for API
   *               querying to newly set to.
   */
  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
}
