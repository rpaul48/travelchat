package edu.brown.cs.student.api.tripadvisor.preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import edu.brown.cs.student.api.tripadvisor.objects.Item;
import edu.brown.cs.student.api.tripadvisor.objects.Restaurant;

public class FoodPreference implements Preference {
  private double latitude;
  private double longitude;
  private Map<String, Object> fields;
  private double minRating;
  private List<String> dietaryRestrictions;
  private boolean openNow;
  private double distance;
  private List<String> restaurantDiningOptions;
  private String prices;
  private List<String> restaurantStyles;
  private List<String> cuisineType; // field "combined_food"
  private List<String> mealType; // field "restaurant_mealtype"

  @Override
  public void buildFields() {
    String dietaryRestrictionsStr = "";
    for (int i = 0; i < this.dietaryRestrictions.size() - 1; i++) {
      dietaryRestrictionsStr += this.dietaryRestrictions.get(i);
      dietaryRestrictionsStr += ",";
    }
    dietaryRestrictionsStr += this.dietaryRestrictions.get(this.dietaryRestrictions.size() - 1);

    String restaurantStylesStr = "";
    for (int i = 0; i < this.restaurantStyles.size() - 1; i++) {
      restaurantStylesStr += this.restaurantStyles.get(i);
      restaurantStylesStr += ",";
    }
    restaurantStylesStr += this.restaurantStyles.get(this.restaurantStyles.size() - 1);

    String cuisineTypeStr = "";
    for (int i = 0; i < this.cuisineType.size() - 1; i++) {
      cuisineTypeStr += this.cuisineType.get(i);
      cuisineTypeStr += ",";
    }
    cuisineTypeStr += this.cuisineType.get(this.cuisineType.size() - 1);

    String mealTypeStr = "";
    for (int i = 0; i < this.mealType.size() - 1; i++) {
      mealTypeStr += this.mealType.get(i);
      mealTypeStr += ",";
    }
    mealTypeStr += this.mealType.get(this.mealType.size() - 1);

    fields = new HashMap<>();
    fields.put("limit", 10); // adjust
    fields.put("lang", "en_US"); // adjust
    fields.put("currency", "USD"); // adjust
    fields.put("lunit", "km"); // adjust
    fields.put("latitude", latitude);
    fields.put("longitude", longitude);
    fields.put("min_rating", minRating);
    fields.put("dietary_restrictions", dietaryRestrictionsStr);
    fields.put("open_now", openNow);
    fields.put("distance", distance);
    fields.put("restaurant_dining_options", restaurantDiningOptions);
    fields.put("prices_restaurants", prices);
    fields.put("restaurant_styles", restaurantStylesStr);
    fields.put("combined_food", cuisineTypeStr);
    fields.put("restaurant_mealtype", mealTypeStr);
  }

  @Override
  public String getQueryString() throws UnirestException {
    if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
      System.err.println("ERROR: Latitude or longitude invalid");
      return null;
    }

    HttpResponse<String> response = Unirest
        .get("https://tripadvisor1.p.rapidapi.com/restaurants/list-by-latlng?")
        .header("x-rapidapi-host", "tripadvisor1.p.rapidapi.com")
        .header("x-rapidapi-key", "9ab9c1d3bdmsha453182e940dd58p105f14jsna2fade8f7b4d")
        .queryString(fields).asString();
    return response.getBody();
  }

  @Override
  public List<Item> parseResult() throws JSONException, UnirestException {
    JSONObject obj = new JSONObject(this.getQueryString());
    if (obj.get("error") != null) {
      System.err.println("ERROR: Error in query");
      return null;
    }

    List<Item> restaurantsList = new ArrayList<Item>();
    JSONArray restaurantsArr = (JSONArray) obj.get("data");
    // goes through all of the restaurants recommended
    for (int i = 0; i < restaurantsArr.length(); i++) {
      Restaurant restaurant = new Restaurant();
      JSONObject restaurantObj = (JSONObject) restaurantsArr.get(i);

      JSONObject photoObj = (JSONObject) restaurantObj.get("photo");
      JSONObject imagesObj = (JSONObject) photoObj.get("images");
      JSONObject smallObj = (JSONObject) imagesObj.get("small");
      restaurant.setPhotoUrl(smallObj.getString("url"));

      restaurant.setName(restaurantObj.getString("name"));
      restaurant.setLatitude(restaurantObj.getDouble("latitude"));
      restaurant.setLongitude(restaurantObj.getDouble("longitude"));
      restaurant.setDistance(restaurantObj.getDouble("distance")); // 1.0886330230315118
      restaurant.setDistanceString(restaurantObj.getString("distance_string")); // "1.1 km"
      restaurant.setNumReviews(restaurantObj.getInt("num_reviews"));
      restaurant.setLocationString(restaurantObj.getString("location_string"));
      restaurant.setRating(restaurantObj.getDouble("rating"));
      restaurant.setPriceLevel(restaurantObj.getString("price_level"));
      restaurant.setPriceRange(restaurantObj.getString("price"));
      restaurant.setRankingString(restaurantObj.getString("ranking"));
      restaurant.setRanking(restaurantObj.getInt("ranking_position"));
      restaurant.setClosed(restaurantObj.getBoolean("is_closed"));
      restaurant.setAddress(restaurantObj.getString("address"));

      List<String> cuisines = new ArrayList<String>();
      JSONArray cuisineArr = (JSONArray) restaurantObj.get("cuisine");
      for (int j = 0; j < cuisineArr.length(); j++) {
        JSONObject cuisineObj = (JSONObject) cuisineArr.get(i);
        cuisines.add(cuisineObj.getString("name"));
      }
      restaurant.setCuisineTypes(cuisines);

      List<String> hours = new ArrayList<String>();
      JSONObject hoursObj = (JSONObject) restaurantObj.get("hours");
      JSONArray hoursArr = (JSONArray) hoursObj.get("week_ranges");
      for (int j = 0; j < hoursArr.length(); j++) {
        JSONArray eachDay = (JSONArray) hoursArr.get(i);
        if (eachDay.length() == 0) {
          hours.add("closed");
        } else {
          hours.add(eachDay.get(0).toString());
        }
      }
      restaurant.setCuisineTypes(cuisines);

      restaurantsList.add(restaurant);
    }

    return restaurantsList;
  }

  @Override
  public double getLatitude() {
    return latitude;
  }

  @Override
  public double getLongitude() {
    return longitude;
  }

  @Override
  public Map<String, Object> getFields() {
    return fields;
  }

  public double getMinRating() {
    return minRating;
  }

  public List<String> getDietaryRestrictions() {
    return dietaryRestrictions;
  }

  public boolean isOpenNow() {
    return openNow;
  }

  public double getDistance() {
    return distance;
  }

  public List<String> getRestaurantDiningOptions() {
    return restaurantDiningOptions;
  }

  public String getPrices() {
    return prices;
  }

  public List<String> getRestaurantStyles() {
    return restaurantStyles;
  }

  public List<String> getCuisineType() {
    return cuisineType;
  }

  public List<String> getMealType() {
    return mealType;
  }

  @Override
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  @Override
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public void setFields(Map<String, Object> fields) {
    this.fields = fields;
  }

  public void setMinRating(double minRating) {
    this.minRating = minRating;
  }

  public void setDietaryRestrictions(List<String> dietaryRestrictions) {
    this.dietaryRestrictions = dietaryRestrictions;
  }

  public void setOpenNow(boolean openNow) {
    this.openNow = openNow;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setRestaurantDiningOptions(List<String> restaurantDiningOptions) {
    this.restaurantDiningOptions = restaurantDiningOptions;
  }

  public void setPrices(String prices) {
    this.prices = prices;
  }

  public void setRestaurantStyles(List<String> restaurantStyles) {
    this.restaurantStyles = restaurantStyles;
  }

  public void setCuisineType(List<String> cuisineType) {
    this.cuisineType = cuisineType;
  }

  public void setMealType(List<String> mealType) {
    this.mealType = mealType;
  }
}
