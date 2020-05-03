package edu.brown.cs.student.api.tripadvisor.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Attraction;
import edu.brown.cs.student.api.tripadvisor.request.AttractionRequest;

/**
 * A response for attractions from the TripAdvisor API.
 */
public class AttractionResponse {
  private AttractionRequest attractionRequest;

  /**
   * Constructs a Response object built on given request data.
   *
   * @param attractionRequest The data we'll use in our parsers.
   */
  public AttractionResponse(AttractionRequest attractionRequest) {
    this.attractionRequest = attractionRequest;
  }

  /**
   * Getter of attraction request.
   *
   * @return attraction request.
   */
  public AttractionRequest getattractionRequest() {
    return attractionRequest;
  }

  /**
   * Setter of attraction request.
   *
   * @param attractionRequest
   */
  public void setattractionRequest(AttractionRequest attractionRequest) {
    this.attractionRequest = attractionRequest;
  }

  /**
   * Parses all relevant fields from the raw HTTP response for Attraction.
   *
   * @return List of attractions matching query parameters.
   * @throws UnirestException
   */
  public List<Attraction> getData() throws UnirestException {
    List<Attraction> attractionsList = new ArrayList<>();

    try {
      HttpResponse<JsonNode> queryResponse = attractionRequest.run();
      JSONObject obj = new JSONObject(queryResponse);
      JSONArray attractionsArr = (JSONArray) obj.get("data");
      // goes through all of the attractions recommended
      for (int i = 0; i < attractionsArr.length(); i++) {
        Attraction attraction = new Attraction();
        JSONObject attractionObj = (JSONObject) attractionsArr.get(i);

        JSONObject photoObj = (JSONObject) attractionObj.get("photo");
        JSONObject imagesObj = (JSONObject) photoObj.get("images");
        JSONObject smallObj = (JSONObject) imagesObj.get("small");
        attraction.setPhotoUrl(smallObj.getString("url")); // "https://media-cdn.tripadvisor.com/media/photo-l/15/19/d6/c1/the-jouney-begins-kaan.jpg"

        attraction.setName(attractionObj.getString("name")); // "Performances"
        attraction.setLatitude(attractionObj.getDouble("latitude")); // 12.906674
        attraction.setLongitude(attractionObj.getDouble("longitude")); // 100.87785
        attraction.setDistance(attractionObj.getDouble("distance")); // 1.0886330230315118
        attraction.setNumReviews(attractionObj.getInt("num_reviews")); // 120
        attraction.setLocationString(attractionObj.getString("location_string")); // "Pattaya,
                                                                                  // Chonburi
        // Province"
//
//      JSONObject offerGroupObj = (JSONObject) attractionObj.get("offer_group");
//      attraction.setPrice(offerGroupObj.getString("lowest_price")); // "$16.23"

        attraction.setClosed(attractionObj.getBoolean("is_closed")); // false

        attractionsList.add(attraction);
      }
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Missing element in API result causing error in parsing.");
    }

    return attractionsList;
  }
}
