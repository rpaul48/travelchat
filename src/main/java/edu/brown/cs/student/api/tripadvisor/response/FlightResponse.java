package edu.brown.cs.student.api.tripadvisor.response;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import edu.brown.cs.student.api.tripadvisor.objects.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * A response for flights from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class FlightResponse extends Response {
    private List<Flight> flights;

    public FlightResponse(HttpResponse<JsonNode> response) {
        this.flights = new ArrayList<>();
        this.parseResponse(response);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public void parseResponse(HttpResponse<JsonNode> response) {
        //TODO: Specific parsing logic.
    }
}
