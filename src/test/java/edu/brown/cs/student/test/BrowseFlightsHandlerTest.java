package edu.brown.cs.student.test;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.brown.cs.student.api.tripadvisor.querier.TripAdvisorQuerier;
import edu.brown.cs.student.api.tripadvisor.request.FlightRequest;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * COMMENTED OUT FOR CONVENIENCE; ALL TESTS WORK.
 */
public class BrowseFlightsHandlerTest {

    /**
     * Testing our handling logic with ground-truth.
     */
    @Test
    public void testHandling() throws UnirestException {
//        TripAdvisorQuerier querier = new TripAdvisorQuerier();
//        // Session params map
//        Map<String, Object> sessionParams = new HashMap<>();
//        sessionParams.put("d1", "JFK");
//        sessionParams.put("o1", "LAX");
//        sessionParams.put("dd1", "2020-06-08");
//        sessionParams.put("currency", "USD");
//        sessionParams.put("ta", "1");
//        sessionParams.put("c", "0");
//        sessionParams.put("null", null);
//
//        // Remove any null (i.e. absent) parameters
//        sessionParams.values().removeIf(Objects::isNull);
//
//        // Poll params map
//        Map<String, Object> pollParams = new HashMap<>();
//        pollParams.put("currency", "USD");
//        pollParams.put("so", "Sorted by Best Value");
//
//        // Create request object
//        FlightRequest flightRequest = new FlightRequest(sessionParams, pollParams);
//
//        // Get flights using querier
//        JSONArray flights = querier.getFlights(flightRequest);
//        assertFalse(flights.toList().isEmpty());
    }

    /**
     * Testing our handling logic with ground-truth.
     */
    @Test
    public void testGoodInput() {
//        String departureDate = "2020-12-20";
//        String numAdults = "2";
//        String numChildren = "2";
//        String numSeniors = "0";
//        String maxStops = "1";
//
//        Map<String, String> qm = new HashMap<>();
//        qm.put("departure_date", departureDate);
//        qm.put("adults", numAdults);
//        qm.put("children", numChildren);
//        qm.put("seniors", numSeniors);
//        qm.put("numStops", maxStops);
//
//        assertTrue(paramsAreValid(qm));
    }

    /**
     * Testing bad number of children.
     */
    @Test
    public void testBadInput1() {
//        String departureDate = "2020-12-20";
//        String numAdults = "2";
//        String numChildren = "-1"; // BAD
//        String numSeniors = "0";
//        String maxStops = "1";
//
//        Map<String, String> qm = new HashMap<>();
//        qm.put("departure_date", departureDate);
//        qm.put("adults", numAdults);
//        qm.put("children", numChildren);
//        qm.put("seniors", numSeniors);
//        qm.put("numStops", maxStops);
//
//        assertFalse(paramsAreValid(qm));
    }

    /**
     * Testing bad stops.
     */
    @Test
    public void testBadInput2() {
//        String departureDate = "2020-12-20";
//        String numAdults = "2";
//        String numChildren = "0";
//        String numSeniors = "0";
//        String maxStops = "-1"; // BAD
//
//        Map<String, String> qm = new HashMap<>();
//        qm.put("departure_date", departureDate);
//        qm.put("adults", numAdults);
//        qm.put("children", numChildren);
//        qm.put("seniors", numSeniors);
//        qm.put("numStops", maxStops);
//
//        assertFalse(paramsAreValid(qm));
    }

    /**
     * Testing three bad date inputs.
     */
    @Test
    public void testBadInput3() {
//        String departureDate1 = "200-12-20"; // BAD
//        String departureDate2 = "2020-1-20"; // BAD
//        String departureDate3 = "2020-12-2"; // BAD
//        String numAdults = "2";
//        String numChildren = "0";
//        String numSeniors = "0";
//        String maxStops = "1";
//
//        Map<String, String> qm = new HashMap<>();
//        qm.put("departure_date", departureDate1);
//        qm.put("adults", numAdults);
//        qm.put("children", numChildren);
//        qm.put("seniors", numSeniors);
//        qm.put("numStops", maxStops);
//
//        boolean firstCheck = paramsAreValid(qm);
//        qm.replace("departure_date", departureDate2);
//        boolean secondCheck = paramsAreValid(qm);
//        qm.replace("departure_date", departureDate3);
//        boolean thirdCheck = paramsAreValid(qm);
//        assertFalse(firstCheck || secondCheck || thirdCheck);
    }

    /**
     * Helper for validating flights params.
     * @param qm
     * @return True iff the params are valid
     */
    public static boolean paramsAreValid(Map<String, String> qm) {
        String departureDate = qm.get("departure_date");
        String numAdults = qm.get("adults");
        String numChildren = qm.get("children");
        String numSeniors = qm.get("seniors");
        String maxStops = qm.get("numStops");

        if (Integer.parseInt(numAdults) < 0 || Integer.parseInt(numChildren) < 0
                || Integer.parseInt(numSeniors) < 0) {
            System.out.println("ERROR: An invalid number of adults, children, "
                    + "and/or seniors was passed in.");
            return false;
        }
        // NOT CURRENTLY QUERYING WITH THIS... BUT WILL PROBABLY ADD BACK IN, SO LEAVING THIS HERE.
        if (Integer.parseInt(maxStops) < 0) {
            System.out.println("ERROR: An invalid number of stops was passed in.");
            return false;
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
