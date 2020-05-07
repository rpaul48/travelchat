package edu.brown.cs.student.api.tripadvisor.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;
import edu.brown.cs.student.api.tripadvisor.request.RestaurantRequest;

/**
 * A response for restaurants from the TripAdvisor API.
 */
public class RestaurantResponse {
  private RestaurantRequest restaurantRequest;

  /**
   * Constructs a Response object built on given request data.
   *
   * @param restaurantRequest The data we'll use in our parsers.
   */
  public RestaurantResponse(RestaurantRequest restaurantRequest) {
    this.restaurantRequest = restaurantRequest;
  }

  /**
   * Getter of restaurant request.
   *
   * @return restaurant request.
   */
  public RestaurantRequest getRestaurantRequest() {
    return restaurantRequest;
  }

  /**
   * Setter of restaurant request.
   *
   * @param restaurantRequest to newly set to.
   */
  public void setRestaurantRequest(RestaurantRequest restaurantRequest) {
    this.restaurantRequest = restaurantRequest;
  }

  /**
   * Parses all relevant fields from the raw HTTP response for restaurant.
   *
   * @return List of restaurants matching query parameters.
   * @throws UnirestException - thrown if JSONArray mapped by "data" cannot be
   *                          obtained.
   */
  public List<Restaurant> getData() throws UnirestException {
    List<Restaurant> restaurantsList = new ArrayList<>();

    try {
      String queryResult = restaurantRequest.run();

      if (queryResult.equals("")) {
        return restaurantsList;
      }

      JSONObject obj = new JSONObject(queryResult);
      JSONArray restaurantsArr = (JSONArray) obj.get("data");
      // goes through all of the restaurants recommended
      for (int i = 0; i < restaurantsArr.length(); i++) {
        Restaurant restaurant = new Restaurant();
        JSONObject restaurantObj = (JSONObject) restaurantsArr.get(i);

        try {
          if (restaurantObj.isNull("name")) {
            continue;
          }
          restaurant.setName(restaurantObj.getString("name"));

          if (!restaurantObj.isNull("latitude") && !restaurantObj.isNull("longitude")) {
            restaurant.setLatitude(restaurantObj.getDouble("latitude"));
            restaurant.setLongitude(restaurantObj.getDouble("longitude"));
          }

          if (!restaurantObj.isNull("distance")) {
            restaurant.setDistance(restaurantObj.getDouble("distance"));
          }

          if (!restaurantObj.isNull("num_reviews")) {
            restaurant.setNumReviews(restaurantObj.getInt("num_reviews"));
          }

          if (!restaurantObj.isNull("location_string")) {
            restaurant.setLocationString(restaurantObj.getString("location_string"));
          }

          if (!restaurantObj.isNull("rating")) {
            restaurant.setRating(restaurantObj.getDouble("rating"));
          }

          if (!restaurantObj.isNull("price_level")) {
            restaurant.setPriceLevel(restaurantObj.getString("price_level"));
          }

          if (!restaurantObj.isNull("price")) {
            restaurant.setPriceRange(restaurantObj.getString("price"));
          }

          if (!restaurantObj.isNull("ranking")) {
            restaurant.setRankingString(restaurantObj.getString("ranking"));
          }

          if (!restaurantObj.isNull("ranking_position")) {
            restaurant.setRanking(restaurantObj.getInt("ranking_position"));
          }

          if (!restaurantObj.isNull("is_closed")) {
            restaurant.setClosed(restaurantObj.getBoolean("is_closed"));
          }

          if (!restaurantObj.isNull("address")) {
            restaurant.setAddress(restaurantObj.getString("address"));
          }

          if (!restaurantObj.isNull("cuisine")) {
            List<String> cuisines = new ArrayList<String>();
            JSONArray cuisineArr = (JSONArray) restaurantObj.get("cuisine");
            for (int j = 0; j < cuisineArr.length(); j++) {
              JSONObject cuisineObj = (JSONObject) cuisineArr.get(j);
              cuisines.add(cuisineObj.getString("name"));
            }
            restaurant.setCuisineTypes(cuisines);
          }

          if (!restaurantObj.isNull("photo")) {
            JSONObject photoObj = (JSONObject) restaurantObj.get("photo");
            if (!photoObj.isNull("images")) {
              JSONObject imagesObj = (JSONObject) photoObj.get("images");
              if (!imagesObj.isNull("small")) {
                JSONObject smallObj = (JSONObject) imagesObj.get("small");
                if (!smallObj.isNull("url")) {
                  restaurant.setPhotoUrl(smallObj.getString("url"));
                }
              }
            }
          }
        } catch (org.json.JSONException exception) {
          continue;
        }

        restaurantsList.add(restaurant);
      }
    } catch (org.json.JSONException exception) {
      System.err.println("ERROR: Data is unobtainable.");
    }

    return restaurantsList;
  }
}
