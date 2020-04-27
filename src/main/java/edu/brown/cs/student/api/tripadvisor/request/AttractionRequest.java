package edu.brown.cs.student.api.tripadvisor.request;

import java.util.Map;

/**
 * A request for attractions from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class AttractionRequest extends Request {
    public AttractionRequest(String hostURL, String currency, Map<String, Object> givenParams) {
        super(hostURL, currency, givenParams);
    }
}
