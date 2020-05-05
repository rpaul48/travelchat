package edu.brown.cs.student.api.tripadvisor.objects;

import java.text.DecimalFormat;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * This is a class for Attraction, as defined by the TripAdvisor API.
 */
public class Attraction implements Item {
  private String name; // field "name"
  private double latitude; // field "latitude"
  private double longitude; // field "longitude"
  private double distance; // field "distance"
  private int numReviews; // field "num_reviews"
  private String locationString; // field "location_string"
  private String photoUrl; // field "photo"-"images"-"small"-"url"
  private boolean isClosed; // field "is_closed"

  /**
   * Default constructor.
   */
  public Attraction() {
  }

  /**
   * Constructor with all fields.
   *
   * @param name
   * @param latitude
   * @param longitude
   * @param distance
   * @param numReviews
   * @param locationString
   * @param photoUrl
   * @param priceRange
   * @param isClosed
   */
  public Attraction(String name, double latitude, double longitude, double distance, int numReviews,
      String locationString, String photoUrl, String priceRange, boolean isClosed) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.distance = distance;
    this.numReviews = numReviews;
    this.locationString = locationString;
    this.photoUrl = photoUrl;
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

  @Override
  public void setClosed(boolean closed) {
    this.isClosed = closed;
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df2 = new DecimalFormat("#.####");

    StringBuilder sb = new StringBuilder();
    sb.append("Name: " + name + "\n");
    sb.append("Location: " + locationString + "\n");
    sb.append("Latitude: " + df2.format(latitude) + "\n");
    sb.append("Longitude: " + df2.format(longitude) + "\n");
    sb.append("Distance: " + df.format(distance) + " " + Constants.LUNIT + "\n");
    sb.append("Number of Reviews: " + numReviews + "\n");
//    sb.append("Photo Url: " + photoUrl + "\n");

    if (isClosed) {
      sb.append("Closed");
    } else {
      sb.append("Open");
    }

    return sb.toString();
  }
}
