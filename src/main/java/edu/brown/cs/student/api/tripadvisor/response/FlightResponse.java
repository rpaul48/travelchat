package edu.brown.cs.student.api.tripadvisor.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Flight;
import edu.brown.cs.student.api.tripadvisor.request.FlightRequest;

/**
 * A response for flights from the TripAdvisor API; specifically, the
 * information we need for our app.
 *
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightResponse {
  private FlightRequest flightRequest;

  /**
   * Constructs a Response object built on given request data.
   *
   * @param flightRequest The data we'll use in our parsers.
   */
  public FlightResponse(FlightRequest flightRequest) {
    this.flightRequest = flightRequest;
  }

  /**
   * Getter of flight request.
   *
   * @return flight request.
   */
  public FlightRequest getFlightRequest() {
    return flightRequest;
  }

  /**
   * Setter of flight request.
   *
   * @param flightRequest
   */
  public void setFlightRequest(FlightRequest flightRequest) {
    this.flightRequest = flightRequest;
  }

  /**
   * Parses all relevant fields from the raw HTTP response, creating a SINGLE
   * flight.
   *
   * It's pretty easy to return more than one flight, but we'll start with one
   * until the method is proven.
   *
   * @return List of flights matching query parameters.
   * @throws UnirestException
   */
  public List<Flight> getData() throws UnirestException {
    HttpResponse<JsonNode> pollResponse = flightRequest.run();
    JSONObject obj = new JSONObject(pollResponse);
    JSONObject body = obj.getJSONObject("body");
    JSONArray array = body.getJSONArray("array");
    // All fields are in a JSON at index 0, for some reason...
    JSONObject allFields = array.getJSONObject(0);
    String sh = getSearchHash(allFields);
    String origin = getOrigin(allFields);
    String dest = getDest(allFields);
    String flightID = getFlightID(allFields);
    String price = getPrice(allFields);
    // CABIN CLASS -- is buggy, may need to omit.
//    String cabinClass = getCabinClass(allFields);
    String layover = getLayover(allFields);
    if (layover.trim().equals("")) {
      layover = "no layover";
    }
    String duration = getDuration(allFields);
    String bookingURL = getBookingURL(sh, dest, origin, flightID, flightRequest.getSessionID());
    String carrier = getCarrier(allFields);
    List<Flight> flights = new ArrayList<>();
    Flight bestFlight = new Flight(bookingURL, price, carrier, layover, dest, origin, flightID,
        duration);
    flights.add(bestFlight);
    return flights;
  }

  /**
   * Parses out search hash from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getSearchHash(JSONObject allFields) {
    JSONObject summary = allFields.getJSONObject("summary");
    return summary.getString("sh");
  }

  /**
   * Parses out flight ID from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getFlightID(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray idArr = flight.getJSONArray("l");
    return idArr.getJSONObject(0).getString("id");
  }

  /**
   * Parses out flight origin location from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getOrigin(JSONObject allFields) {
    JSONObject search_params = allFields.getJSONObject("search_params");
    String origin = search_params.getJSONArray("s").getJSONObject(0).getString("o");
    return origin;
  }

  /**
   * Parses out flight destination location from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getDest(JSONObject allFields) {
    JSONObject search_params = allFields.getJSONObject("search_params");
    String dest = search_params.getJSONArray("s").getJSONObject(0).getString("d");
    return dest;
  }

  /**
   * Parses out flight price from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getPrice(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray idArr = flight.getJSONArray("l");
    return idArr.getJSONObject(0).getJSONObject("pr").getString("dp");
  }

  /**
   * Parses out cabin class from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getCabinClass(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray idArr = flight.getJSONArray("l");
    return idArr.getJSONObject(0).getJSONObject("fb").getString("nm");
  }

  /**
   * Parses out flight carrier from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getCarrier(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray idArr = flight.getJSONArray("l");
    return idArr.getJSONObject(0).getString("s");
  }

  /**
   * Parses out flight duration from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getDuration(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray arr = flight.getJSONArray("f");
    String departDate = arr.getJSONObject(0).getJSONArray("l").getJSONObject(0).getString("dd");
    String arriveDate = arr.getJSONObject(0).getJSONArray("l").getJSONObject(0).getString("ad");
    return departDate + " -> " + arriveDate;
  }

  /**
   * Parses out flight's layover from HTTP response.
   *
   * @param allFields
   * @return
   */
  public static String getLayover(JSONObject allFields) {
    JSONArray itineraries = allFields.getJSONArray("itineraries");
    JSONObject flight = itineraries.getJSONObject(0);
    JSONArray arr = flight.getJSONArray("f");
    String layover = "";
    try {
      layover = arr.getJSONObject(0).getJSONArray("lo").getJSONObject(0).getString("t");
      layover = layover.substring(layover.indexOf('>') + 1, layover.lastIndexOf('<'));
      String locationOfLayover = arr.getJSONObject(0).getJSONArray("lo").getJSONObject(0)
          .getString("s");
      layover += " in " + locationOfLayover;
    } catch (Exception e) {
      System.out.println("No layover.");
    }
    return layover;
  }

  /**
   * Queries the TripAdvisorAPI for a flight's booking URL.
   *
   * @param sh  The search hash.
   * @param d   The destination
   * @param o   The origin
   * @param id  The flight ID
   * @param sid The original querie's search ID.
   * @return
   */
  public static String getBookingURL(String sh, String d, String o, String id, String sid) {
    Map<String, String> params = new HashMap<>();
    params.put("searchHash", sh);
    params.put("Dest", d);
    params.put("id", id);
    params.put("Orig", o);
    params.put("searchId", sid);

    ImmutableMap<String, Object> immutableParams = ImmutableMap.copyOf(params);

    String hostURL = "https://tripadvisor1.p.rapidapi.com/flights/get-booking-url";
    // Request headers (with free account's key)
    String x_rapidapi_host = "tripadvisor1.p.rapidapi.com";
    String x_rapidapi_key = "aaf4f074c6msh0940f8b6e880750p1f240bjsne42d7f349197";
    // Send a request and handle response
    HttpResponse<JsonNode> response = null;
    try {
      response = Unirest.get(hostURL).queryString(immutableParams)
          .header("x-rapidapi-host", x_rapidapi_host).header("x-rapidapi-key", x_rapidapi_key)
          .asJson();
    } catch (Exception e) {
      return "Could not get booking URL.";
    }
    JSONObject obj = new JSONObject(response);
    JSONObject body = obj.getJSONObject("body");
    String s = "";
    try {
      s = body.getJSONArray("array").getJSONObject(0).getString("partner_url");
    } catch (Exception e) {
      s = "Could not get booking URL.";
    }
    return s;
  }
}
