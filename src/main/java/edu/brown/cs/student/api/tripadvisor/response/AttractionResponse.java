package edu.brown.cs.student.api.tripadvisor.response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import edu.brown.cs.student.api.tripadvisor.objects.Attraction;

import java.util.ArrayList;
import java.util.List;

/**
 * A response for attractions from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class AttractionResponse {
    private List<Attraction> attractions;

    public AttractionResponse(HttpResponse<JsonNode> response) {
        this.attractions = new ArrayList<>();
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

}
