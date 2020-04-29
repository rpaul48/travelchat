package edu.brown.cs.student.api.tripadvisor.objects;

//public class Attraction {
//  private GeoLocation geoLocation;
//}
/**
 * This is a class for Attraction.
 *
 */
public class Attraction implements Item {
  private String name; // field "name"
  private double latitude; // field "latitude"
  private double longitude; // field "longitude"
  private double distance; // field "distance"
  private int numReviews; // field "num_reviews"
  private String locationString; // field "location_string"
  private String photoUrl; // field "photo"-"images"-"small"-"url"
  private String price; // field "lowest_price" (ex. "$20.00")
  private boolean isClosed; // field "is_closed"

  public Attraction() {
  }

  public Attraction(String name, double latitude, double longitude, double distance, int numReviews,
      String locationString, String photoUrl, String price, String priceRange, boolean isClosed) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.distance = distance;
    this.numReviews = numReviews;
    this.locationString = locationString;
    this.photoUrl = photoUrl;
    this.price = price;
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

  public String getPrice() {
    return price;
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

  public void setPrice(String price) {
    this.price = price;
  }

  @Override
  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
  }
}
