package edu.brown.cs.student.api.tripadvisor.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Requests query resulting from city name.
 */
public class LocationIDRequest {
  private String cityName;

  public LocationIDRequest(String cityName) {
    this.cityName = cityName;
  }

  /**
   * Runs a query with the parameters given in construction. Will return raw
   * HttpResponse.
   *
   * @return
   * @throws UnirestException
   */
  public String run() throws UnirestException {
    String hostURL = "https://tripadvisor1.p.rapidapi.com/locations/search";
    // Request headers (with free account's key)
    String xRapidapiHost = "tripadvisor1.p.rapidapi.com";
    String xRapidapiKey = "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d";
    // Send a request and handle response

    HttpResponse<String> response = Unirest.get(hostURL).header("x-rapidapi-host", xRapidapiHost)
        .header("x-rapidapi-key", xRapidapiKey).queryString("query", cityName).asString();
    return response.getBody();
  }
}
