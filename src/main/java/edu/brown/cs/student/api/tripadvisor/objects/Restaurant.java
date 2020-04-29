package edu.brown.cs.student.api.tripadvisor.objects;

import java.util.List;

//public class Restaurant {
//  private String name;
//  // location ID is defined by TripAdvisor API
//  private String location_id;
//  private GeoLocation geoLocation;
//  // e.g. hours[0][1] = opening time on Monday
//  private String[][] hours;
//  private String websiteURL;
//  private String priceLevel;
//  private String rating;
//  // e.g. #1 of 1,201 restaurants in New York City
//  private String ranking;
//}

public class Restaurant implements Item {
  private String name;
  private double latitude;
  private double longitude;
  private int numReviews;
  private String locationString;
  private String photoUrl; // field "photo"-"images"-"small"-"url"
  private double distance;
  private String priceLevel; // field "price_level" (ex. $$)
  private double rating;
  private boolean isClosed;
  private int ranking; // field "ranking_position"
  private String rankingString; // field "ranking"
  private String address;
//  private List<String> hours; ///
  private List<String> cuisineTypes;

  public Restaurant() {
  }

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
  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
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
}
