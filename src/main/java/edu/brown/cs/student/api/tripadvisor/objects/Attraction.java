package edu.brown.cs.student.api.tripadvisor.objects;

import java.text.DecimalFormat;

import edu.brown.cs.student.chat.gui.Constants;

/**
 * This is a class for Attraction, as defined by the TripAdvisor API, with
 * fields stored using the results of the API query.
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
  private double lowest_price; // field: "lowest_price"

  /**
   * Default constructor, initializing all instance variables.
   */
  public Attraction() {
    name = "";
    latitude = Constants.INIT_NUM_VALUE;
    longitude = Constants.INIT_NUM_VALUE;
    distance = Constants.INIT_NUM_VALUE;
    numReviews = Constants.INIT_NUM_VALUE;
    lowest_price = Constants.INIT_NUM_VALUE;
    locationString = "";
    photoUrl = "";
    isClosed = false;
  }

  public double getLowest_price() {
    return lowest_price;
  }

  public void setLowest_price(double lowest_price) {
    this.lowest_price = lowest_price;
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

    // Means it has been updated and has an actual value for that variable.
    if (!(numReviews == Constants.INIT_NUM_VALUE)) {
      sb.append("Number of Reviews: ").append(numReviews).append("<br>");
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
