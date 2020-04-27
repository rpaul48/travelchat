package api.tripadvisor.response;

/**
 * A response from an API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class Response {
    private String rawResponse;

    /**
     * Returns the full String representation of the response (all fields).
     * @return rawResponse The full String representation of the response (all fields).
     */
    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }
}
