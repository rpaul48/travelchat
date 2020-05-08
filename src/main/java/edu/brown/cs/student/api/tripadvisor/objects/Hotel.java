package edu.brown.cs.student.api.tripadvisor.objects;

import java.text.DecimalFormat;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * This is a class for Hotel, as defined by the TripAdvisor API, with fields
 * stored using the results of the API query.
 */
public class Hotel implements Item {
  private String name; // field "name"
  private double latitude; // field "latitude"
  private double longitude; // field "longitude"
  private double distance; // field "distance"
  private int numReviews; // field "num_reviews"
  private String locationString; // field "location_string"
  private String photoUrl; // field "photo"-"images"-"small"-"url"
  private double rating; // field "rating" (out of 5.0)
  private String priceLevel; // field "price_level" (ex. $$)
  private String priceRange; // field "price" (ex. $141 - $533)
  private int ranking; // field "ranking_position"
  private String rankingString; // field "ranking"
  private boolean isClosed; // field "is_closed"
  private double hotelClass; // field "hotel_class"

  /**
   * Default constructor, initializing all instance variables.
   */
  public Hotel() {
    name = "";
    latitude = Constants.INIT_NUM_VALUE;
    longitude = Constants.INIT_NUM_VALUE;
    numReviews = Constants.INIT_NUM_VALUE;
    locationString = "";
    photoUrl = "";
    distance = Constants.INIT_NUM_VALUE;
    priceLevel = "";
    priceRange = "";
    rating = Constants.INIT_NUM_VALUE;
    isClosed = false;
    ranking = Constants.INIT_NUM_VALUE;
    rankingString = "";
    photoUrl = "";
    hotelClass = Constants.INIT_NUM_VALUE;
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
  public double getDistance() {
    return distance;
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

  public String getPriceLevel() {
    return priceLevel;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public int getRanking() {
    return ranking;
  }

  @Override
  public boolean isClosed() {
    return isClosed;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public String getRankingString() {
    return rankingString;
  }

  public void setRankingString(String rankingString) {
    this.rankingString = rankingString;
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
  public void setDistance(double distance) {
    this.distance = distance;
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

  public void setPriceLevel(String priceLevel) {
    this.priceLevel = priceLevel;
  }

  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
  }

  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  @Override
  public void setClosed(boolean closed) {
    this.isClosed = closed;
  }

  public double getHotelClass() {
    return hotelClass;
  }

  public void setHotelClass(double hotelClass) {
    this.hotelClass = hotelClass;
  }

  @Override
  public String toStringHTML() {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df2 = new DecimalFormat("#.####");
    StringBuilder sb = new StringBuilder();

    // If name is not assigned, do not show this Restaurant in HTML string.
    if (name.equals("")) {
      return "";
    }

    sb.append("<p>Name: ").append(name).append("<br>");

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

    // Means it has been updated and has an actual value for that variable.
    if (!(numReviews == Constants.INIT_NUM_VALUE)) {
      sb.append("Number of Reviews: ").append(numReviews).append("<br>");
    }

    if (!(rating == Constants.INIT_NUM_VALUE)) {
      sb.append("Rating: ").append(rating).append("/5.0<br>");
    }

    if (!(hotelClass == Constants.INIT_NUM_VALUE)) {
      sb.append("Hotel Class: ").append(hotelClass).append(" stars<br>");
    }

    if (!priceLevel.equals("")) {
      sb.append("Price Level: ").append(priceLevel).append("<br>");
    }

    if (!priceRange.equals("")) {
      sb.append("Price: ").append(priceRange).append("<br>");
    }

    if (!rankingString.equals("")) {
      sb.append("Ranking: ").append(rankingString).append("<br>");
    }

    if (isClosed) {
      sb.append("Closed");
    } else {
      sb.append("Open");
    }

    if (!photoUrl.equals("")) {
      sb.append("<br><img src=\"" + photoUrl + "\" width = \"300\" height=\"200\">");
    }

    sb.append("<br><p>");

    return sb.toString();
  }
}
