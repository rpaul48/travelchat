package api.tripadvisor.request;

import java.util.Map;

/**
 * A request for hotels from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class HotelRequest extends Request {
    public HotelRequest(String hostURL, String currency, Map<String, Object> givenParams) {
        super(hostURL, currency, givenParams);
    }
}
