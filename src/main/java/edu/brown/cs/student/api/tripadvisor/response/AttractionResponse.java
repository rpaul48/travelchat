package api.tripadvisor.response;

import api.tripadvisor.objects.Attraction;
import api.tripadvisor.util.GeoLocation;

import java.util.List;

/**
 * A response for attractions from the TripAdvisor API.
 * @author Joshua Nathan Mugerwa
 * @version 1.0
 */
public class AttractionResponse {
    private List<Attraction> attractions;

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
