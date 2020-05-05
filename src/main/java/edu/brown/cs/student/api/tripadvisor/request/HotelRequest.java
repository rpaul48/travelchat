package edu.brown.cs.student.api.tripadvisor.request;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A request for hHtels from the TripAdvisor API. Specifically, this handles all
 * querying logic and returns the unprocessed HTTPResponse.
 */
public class HotelRequest {
  private Map<String, Object> params;

  /**
   * This is the constructor for this class.
   *
   * @param params - a Map from String to Object representing the parameters for
   *               hotel API querying.
   */
  public HotelRequest(Map<String, Object> params) {
    // Query parameters
    this.params = params;
  }

  /**
   * Runs a query with the parameters given in construction. Will return raw
   * HttpResponse.
   *
   * @return String - result of API query.
   * @throws UnirestException - thrown if query fails to run.
   */
  public String run() throws UnirestException {
    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);
    String hostURL = "https://tripadvisor1.p.rapidapi.com/hotels/list-by-latlng";
    // Request headers (with free account's key)
    String xRapidapiHost = "tripadvisor1.p.rapidapi.com";
    String xRapidapiKey = "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d";
    // Send a request and handle response

    HttpResponse<String> response = Unirest.get(hostURL).header("x-rapidapi-host", xRapidapiHost)
        .header("x-rapidapi-key", xRapidapiKey).queryString(immutableParams).asString();
    return response.getBody();
  }

  /**
   * Getter of params.
   *
   * @return a Map from String to Object representing the query parameters.
   */
  public Map<String, Object> getParams() {
    return params;
  }

  /**
   * Setter of params.
   *
   * @param params - a Map from String to Object representing the parameters for
   *               API querying to newly set to.
   */
  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
}
