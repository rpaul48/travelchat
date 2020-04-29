package edu.brown.cs.student.api.tripadvisor.objects;

//public class Hotel {
//  private GeoLocation geoLocation;
//}

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

  public Hotel() {
  }

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

  @Override
  public double getRating() {
    return rating;
  }

  @Override
  public String getPriceLevel() {
    return priceLevel;
  }

  public String getPrice() {
    return price;
  }

  @Override
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

  @Override
  public String getRankingString() {
    return rankingString;
  }

  @Override
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

  @Override
  public void setRating(double rating) {
    this.rating = rating;
  }

  @Override
  public void setPriceLevel(String priceLevel) {
    this.priceLevel = priceLevel;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  @Override
  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  @Override
  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
  }
}
