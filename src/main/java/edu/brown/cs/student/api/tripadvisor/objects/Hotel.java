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
  private String price; // field "price" (ex. $141 - $533)
  private int ranking; // field "ranking_position"
  private String rankingString; // field "ranking"
  private boolean isClosed; // field "is_closed"
  private String hotelClass; // field "hotel_class"

  /**
   * Default constructor.
   */
  public Hotel() {
  }

  /**
   * Constructor with all fields.
   *
   * @param name           - name of Hotel
   * @param latitude       - latitude of Hotel
   * @param longitude      - longitude of Hotel
   * @param distance       - distance from specified location to Hotel
   * @param numReviews     - number of reviews for Hotel
   * @param locationString - location String of Hotel indicating region
   * @param photoUrl       - url for an image of Hotel
   * @param rating         - rating of Hotel
   * @param priceLevel     - price level (ex. $$$) of Hotel
   * @param price          - number representing price of Hotel
   * @param ranking        - ranking number of Hotel in its category
   * @param rankingString  - ranking string of Hotel in its category
   * @param isClosed       - whether Hotel is closed
   */
  public Hotel(String name, double latitude, double longitude, double distance, int numReviews,
      String locationString, String photoUrl, double rating, String priceLevel, String price,
      int ranking, String rankingString, boolean isClosed) {
    super();
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.distance = distance;
    this.numReviews = numReviews;
    this.locationString = locationString;
    this.photoUrl = photoUrl;
    this.rating = rating;
    this.priceLevel = priceLevel;
    this.price = price;
    this.ranking = ranking;
    this.rankingString = rankingString;
    this.isClosed = isClosed;
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

  public String getPrice() {
    return price;
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

  public void setPrice(String price) {
    this.price = price;
  }

  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  @Override
  public void setClosed(boolean closed) {
    this.isClosed = closed;
  }

  public String getHotelClass() {
    return hotelClass;
  }

  public void setHotelClass(String hotelClass) {
    this.hotelClass = hotelClass;
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df2 = new DecimalFormat("#.####");

    StringBuilder sb = new StringBuilder();
    sb.append("Name: ").append(name).append("\n");
    sb.append("Location: ").append(locationString).append("\n");
    sb.append("Latitude: ").append(df2.format(latitude)).append("\n");
    sb.append("Longitude: ").append(df2.format(longitude)).append("\n");
    sb.append("Distance: ").append(df.format(distance)).append(" ").append(Constants.LUNIT)
        .append("\n");
    sb.append("Number of Reviews: ").append(numReviews).append("\n");
    sb.append("Rating: ").append(rating).append("/5.0\n");
    sb.append("Hotel Class: ").append(hotelClass).append(" stars\n");
    sb.append("Price Level: ").append(priceLevel).append("\n");
    sb.append("Price: ").append(price).append("\n");
    sb.append("Ranking: ").append(rankingString).append("\n");
//    sb.append("Photo Url: " + photoUrl + "\n");

    if (isClosed) {
      sb.append("Closed");
    } else {
      sb.append("Open");
    }

    return sb.toString();
  }
}
