package edu.brown.cs.student.api.tripadvisor.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
   * @param request to newly set to.
   */
  public void setattractionRequest(AttractionRequest request) {
    this.attractionRequest = request;
  }

  /**
   * Parses all relevant fields from the raw HTTP response for Attraction.
   *
   * @return List of attractions matching query parameters.
   * @throws UnirestException - thrown if JSONArray mapped by "data" cannot be
   *                          obtained.
   */
  public List<Attraction> getData() throws UnirestException {
    List<Attraction> attractionsList = new ArrayList<>();

    try {
      String queryResult = attractionRequest.run();

      if (queryResult.equals("")) {
        return attractionsList;
      }

      JSONObject obj = new JSONObject(queryResult);
      JSONArray attractionsArr = (JSONArray) obj.get("data");
      // goes through all of the attractions recommended
      for (int i = 0; i < attractionsArr.length(); i++) {
        Attraction attraction = new Attraction();
        JSONObject attractionObj = (JSONObject) attractionsArr.get(i);

        try {
          if (attractionObj.isNull("name")) {
            continue;
          }
          attraction.setName(attractionObj.getString("name"));

          if (!attractionObj.isNull("latitude") && !attractionObj.isNull("longitude")) {
            attraction.setLatitude(attractionObj.getDouble("latitude"));
            attraction.setLongitude(attractionObj.getDouble("longitude"));
          }

          if (!attractionObj.isNull("distance")) {
            attraction.setDistance(attractionObj.getDouble("distance"));
          }

          if (!attractionObj.isNull("num_reviews")) {
            attraction.setNumReviews(attractionObj.getInt("num_reviews"));
          }

          if (!attractionObj.isNull("location_string")) {
            attraction.setLocationString(attractionObj.getString("location_string"));
          }

          if (!attractionObj.isNull("is_closed")) {
            attraction.setClosed(attractionObj.getBoolean("is_closed"));
          }

          if (!attractionObj.isNull("photo")) {
            JSONObject photoObj = (JSONObject) attractionObj.get("photo");
            if (!photoObj.isNull("images")) {
              JSONObject imagesObj = (JSONObject) photoObj.get("images");
              if (!imagesObj.isNull("small")) {
                JSONObject smallObj = (JSONObject) imagesObj.get("small");
                if (!smallObj.isNull("url")) {
                  attraction.setPhotoUrl(smallObj.getString("url"));
                }
              }
            }
          }
        } catch (org.json.JSONException exception) {
          continue;
        }

        attractionsList.add(attraction);
      }
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Data is unobtainable.");
    }

    return attractionsList;
  }
}
