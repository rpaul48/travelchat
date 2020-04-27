package edu.brown.cs.student.api.tripadvisor.response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

/**
 * A response from an API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public abstract class Response {
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

    public abstract void parseResponse(HttpResponse<JsonNode> response);
}
