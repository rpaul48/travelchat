package edu.brown.cs.student.api.tripadvisor.objects;

import java.text.DecimalFormat;
import java.util.List;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * This is a class for Restaurant, as defined by the TripAdvisor API.
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
   * Default constructor.
   */
  public Restaurant() {
  }

  /**
   * Constructor with all fields.
   *
   * @param name
   * @param latitude
   * @param longitude
   * @param numReviews
   * @param locationString
   * @param photoUrl
   * @param distance
   * @param priceLevel
   * @param rating
   * @param isClosed
   * @param ranking
   * @param rankingString
   * @param address
   * @param cuisineTypes
   */
  public Restaurant(String name, double latitude, double longitude, int numReviews,
      String locationString, String photoUrl, double distance, String priceLevel, double rating,
      boolean isClosed, int ranking, String rankingString, String address,
      List<String> cuisineTypes) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.numReviews = numReviews;
    this.locationString = locationString;
    this.photoUrl = photoUrl;
    this.distance = distance;
    this.priceLevel = priceLevel;
    this.rating = rating;
    this.isClosed = isClosed;
    this.ranking = ranking;
    this.rankingString = rankingString;
    this.address = address;
    this.cuisineTypes = cuisineTypes;
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

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df2 = new DecimalFormat("#.####");

    StringBuilder sb = new StringBuilder();
    sb.append("Name: ").append(name).append("\n");
    sb.append("Location: ").append(locationString).append("\n");
    sb.append("Latitude: ").append(df2.format(latitude)).append("\n");
    sb.append("Longitude: ").append(df2.format(longitude)).append("\n");
    sb.append("Distance: ").append(df.format(distance)).append(" ").append(Constants.LUNIT);
    if (cuisineTypes != null && cuisineTypes.size() > 0) {
      sb.append("\nCuisine Types: ");
      for (String cuisine : cuisineTypes) {
        sb.append(cuisine).append(", ");
      }
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append("\nNumber of Reviews: ").append(numReviews).append("\n");
    sb.append("Rating: ").append(rating).append("\n");
    sb.append("Price Level: ").append(priceLevel).append("\n");
    sb.append("Ranking: ").append(rankingString).append("\n");
    sb.append("Address: ").append(address).append("\n");
//    sb.append("Photo Url: " + photoUrl + "\n");

    if (isClosed) {
      sb.append("Closed");
    } else {
      sb.append("Open");
    }

    return sb.toString();
  }
}
