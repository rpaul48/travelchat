package edu.brown.cs.student.api.tripadvisor.response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.request.LocationIDRequest;

public class LocationIDResponse {
  private LocationIDRequest locationIDRequest;

  /**
   * Constructs a Response object built on given request data.
   *
   * @param locationIDRequest The data we'll use in our parsers.
   */
  public LocationIDResponse(LocationIDRequest locationIDRequest) {
    this.locationIDRequest = locationIDRequest;
  }

  /**
   * Parses all relevant fields from the raw HTTP response for location ID.
   *
   * @return String representing location ID.
   * @throws UnirestException - thrown if JSONArray mapped by "data" cannot be
   *                          obtained.
   */
  public String getData() throws UnirestException {
    String locationID = "";
    try {
      String queryResult = locationIDRequest.run();
      JSONObject obj = new JSONObject(queryResult);
      JSONArray arr = (JSONArray) obj.get("data");
      JSONObject obj1 = (JSONObject) arr.get(0);
      JSONObject obj2 = (JSONObject) obj1.get("result_object");
      locationID = (String) obj2.get("location_id");
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Data is unobtainable.");
    }

    return locationID;
  }

  /**
   * Getter of locationIDRequest.
   *
   * @return LocationIDRequest - location ID Request.
   */
  public LocationIDRequest getLocationIDRequest() {
    return locationIDRequest;
  }

  /**
   * Setter of locationIDRequest.
   *
   * @param locationIDRequest - new LocationIDRequest to change to.
   */
  public void setLocationIDRequest(LocationIDRequest locationIDRequest) {
    this.locationIDRequest = locationIDRequest;
  }

}
