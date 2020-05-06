package edu.brown.cs.student.api.tripadvisor.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * This is a class for Restaurant, as defined by the TripAdvisor API, with
 * fields stored using the results of the API query.
 */
public class Restaurant implements Item {
  private String name;
  private double latitude;
  private double longitude;
  private int numReviews;
  private String locationString;
  private String photoUrl;
  private double distance;
  private String priceLevel;
  private double rating;
  private boolean isClosed;
  private int ranking;
  private String rankingString;
  private String address;
  private List<String> cuisineTypes;

  /**
   * Default constructor, initializing all instance variables.
   */
  public Restaurant() {
    name = "";
    latitude = Constants.INIT_NUM_VALUE;
    longitude = Constants.INIT_NUM_VALUE;
    numReviews = Constants.INIT_NUM_VALUE;
    locationString = "";
    photoUrl = "";
    distance = Constants.INIT_NUM_VALUE;
    priceLevel = "";
    rating = Constants.INIT_NUM_VALUE;
    isClosed = false;
    ranking = Constants.INIT_NUM_VALUE;
    rankingString = "";
    address = "";
    cuisineTypes = new ArrayList<String>();
    photoUrl = "";
  }

  @Override
  public double getDistance() {
    return distance;
  }

  public String getPriceLevel() {
    return priceLevel;
  }

  public List<String> getCuisineTypes() {
    return cuisineTypes;
  }

  @Override
  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setPriceLevel(String priceLevel) {
    this.priceLevel = priceLevel;
  }

  public void setCuisineTypes(List<String> cuisineTypes) {
    this.cuisineTypes = cuisineTypes;
  }

  @Override
  public String getName() {
    return name;
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
  public int getNumReviews() {
    return numReviews;
  }

  @Override
  public String getLocationString() {
    return locationString;
  }

  @Override
  public String getPhotoUrl() {
    return photoUrl;
  }

  public double getRating() {
    return rating;
  }

  @Override
  public boolean isClosed() {
    return isClosed;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public void setName(String name) {
    this.name = name;
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
  public void setNumReviews(int numReviews) {
    this.numReviews = numReviews;
  }

  @Override
  public void setLocationString(String locationString) {
    this.locationString = locationString;
  }

  @Override
  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  @Override
  public void setClosed(boolean closed) {
    this.isClosed = closed;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRanking() {
    return ranking;
  }

  public String getRankingString() {
    return rankingString;
  }

  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  public void setRankingString(String rankingString) {
    this.rankingString = rankingString;
  }

  /*
   * Checks if each variable has an assigned information and builds an HTML string
   * using meaningful variables with info assigned.
   */
  @Override
  public String toStringHTML() {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df2 = new DecimalFormat("#.####");
    StringBuilder sb = new StringBuilder();

    // If name is not assigned, do not show this Restaurant in HTML string.
    if (name.equals("")) {
      return "";
    }

    sb.append("<br>Name: ").append(name).append("<br>");

    if (!locationString.equals("")) {
      sb.append("Location: ").append(locationString).append("<br>");
    }

    // Means it has been updated and has an actual value for that number variable.
    if (!(latitude == Constants.INIT_NUM_VALUE || longitude == Constants.INIT_NUM_VALUE)) {
      sb.append("Latitude: ").append(df2.format(latitude)).append("<br>");
      sb.append("Longitude: ").append(df2.format(longitude)).append("<br>");
    }

    if (!(distance == Constants.INIT_NUM_VALUE)) {
      sb.append("Distance: ").append(df.format(distance)).append(" ").append(Constants.LUNIT)
          .append("<br>");
    }

    if (cuisineTypes.size() > 0) {
      sb.append("Cuisine Types: ");
      for (String cuisine : cuisineTypes) {
        sb.append(cuisine).append(", ");
      }
      sb.delete(sb.length() - 2, sb.length());
      sb.append("<br>");
    }

    if (!(numReviews == Constants.INIT_NUM_VALUE)) {
      sb.append("Number of Reviews: ").append(numReviews).append("<br>");
    }

    if (!(rating == Constants.INIT_NUM_VALUE)) {
      sb.append("Rating: ").append(rating).append("/5.0<br>");
    }

    if (!priceLevel.equals("")) {
      sb.append("Price Level: ").append(priceLevel).append("<br>");
    }

    if (!rankingString.equals("")) {
      sb.append("Ranking: ").append(rankingString).append("<br>");
    }

    if (!address.equals("")) {
      sb.append("Address: ").append(address).append("<br>");
    }

    if (isClosed) {
      sb.append("Closed");
    } else {
      sb.append("Open");
    }

    if (!photoUrl.equals("")) {
      sb.append("<br><img src=\"" + photoUrl + "\" width = \"300\" height=\"200\">");
    }

    sb.append("<br><br>");

    return sb.toString();
  }
}
