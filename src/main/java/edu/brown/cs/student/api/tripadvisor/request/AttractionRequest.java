package edu.brown.cs.student.api.tripadvisor.request;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A request for Attractions from the TripAdvisor API. Specifically, this
 * handles all querying logic and returns the unprocessed HTTPResponse.
 */
public class AttractionRequest {
  private Map<String, Object> params;

  /**
   * This is the constructor for this class.
   *
   * @param params - a Map<String, Object> representing the parameters for
   *               attraction API querying.
   */
  public AttractionRequest(Map<String, Object> params) throws UnirestException {
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
  public String run() throws UnirestException {
    // Latitude and longitude are required parameters. Query cannot be run without
    // them.
    if (!params.containsKey("tr_latitude") || !params.containsKey("tr_longitude")
        || !params.containsKey("bl_latitude") || !params.containsKey("bl_longitude")) {
      System.err.println("ERROR: Top-right/bottom-left latitude or longitude missing.");
      return "";
    }

    // If fields is passed directly into constructor, latitude and longitude might
    // not have been assigned.
    double tr_latitude = (double) params.get("tr_latitude");
    double tr_longitude = (double) params.get("tr_longitude");
    double bl_latitude = (double) params.get("bl_latitude");
    double bl_longitude = (double) params.get("bl_longitude");

    if (!(tr_latitude >= -90 && tr_latitude <= 90 && tr_longitude >= -180 && tr_longitude <= 180
        && bl_latitude >= -90 && bl_latitude <= 90 && bl_longitude >= -180
        && bl_longitude <= 180)) {
      System.err.println("ERROR: Top-right/bottom-left latitude or longitude invalid");
      return "";
    }

    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);
    String hostURL = "https://tripadvisor1.p.rapidapi.com/attractions/list-in-boundary";
    // Request headers (with free account's key)
    String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
    String x_rapidapi_key = "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d";
    // Send a request and handle response

    HttpResponse<String> response = Unirest.get(hostURL).queryString(immutableParams)
        .header("x-rapidapi-host", x_rapidapi_host).header("x-rapidapi-key", x_rapidapi_key)
        .asString();
    return response.getBody();
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
