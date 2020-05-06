package edu.brown.cs.student.chat.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;

import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.FlightRequest;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class that handles requests for flights.
 */
public class BrowseFlightsHandler implements Route {
  /**
   * Returns an array of JSON objects, one for each flight. Each object will contain
   * the booking URL, carrier, and price of the flight.
   * @param request
   * @param response
   * @return An array of flights, as JSONs.
   */
  @Override
  public JSONArray handle(Request request, Response response) {
    try {
      // Querier for flights
      TripAdvisorQuerier querier = new TripAdvisorQuerier();
      // Get parameters from front-end
      QueryParamsMap qm = request.queryMap();
      if (!paramsAreValid(qm)) {
        System.err.println("ERROR: >=1 invalid argument was passed as a flight parameter.");
        return null;
      }

      // Session params map
      Map<String, Object> sessionParams = new HashMap<>();
      sessionParams.put("d1", qm.value("destination"));
      sessionParams.put("o1", qm.value("origin"));
      sessionParams.put("dd1", qm.value("departure_date"));
      sessionParams.put("currency", "USD");
      sessionParams.put("ta", qm.value("adults"));
      sessionParams.put("ts", qm.value("seniors"));
      sessionParams.put("c", qm.value("flightClass"));

      // Remove any null (i.e. absent) parameters
      sessionParams.values().removeIf(Objects::isNull);

      // Poll params map
      Map<String, Object> pollParams = new HashMap<>();
      pollParams.put("currency", "USD");
      pollParams.put("so", "Sorted by Best Value");

      // Create request object
      FlightRequest flightRequest = new FlightRequest(sessionParams, pollParams);

      // Get flights using querier
      return querier.getFlights(flightRequest);
    } catch (Exception ex) {
      System.err.println("ERROR: An error occurred browsing flights. Printing stack trace:");
      ex.printStackTrace();
    }
    return new JSONArray();
  }

  /**
   * Helper for determining if the user's params are valid.
   *
   * @param qm QueryParamsMap to use to test the validity of parameters.
   * @return True iff the params are valid.
   */
  public static boolean paramsAreValid(QueryParamsMap qm) {
    String departureDate = qm.value("departure_date");
    String numAdults = qm.value("adults");
    String numChildren = qm.value("children");
    String numSeniors = qm.value("seniors");
    String maxStops = qm.value("numStops");

    if (Integer.parseInt(numAdults) < 0 || Integer.parseInt(numChildren) < 0
        || Integer.parseInt(numSeniors) < 0) {
      System.out.println(
          "ERROR: An invalid number of adults, children, " + "and/or seniors was passed in.");
      return false;
    }
    // NOT CURRENTLY QUERYING WITH THIS... BUT WILL PROBABLY ADD BACK IN, SO LEAVING
    // THIS HERE.
    if (!maxStops.equals("Any")) {
      if (Integer.parseInt(maxStops) < 0) {
        System.out.println("ERROR: An invalid number of stops was passed in.");
        return false;
      }
    }

    // Format: YYYY-MM-DD
    String dateFormat = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
    if (!departureDate.matches(dateFormat)) {
      System.out.println("ERROR: The departure date is not properly formatted.");
      return false;
    }
    return true;
  }
}
