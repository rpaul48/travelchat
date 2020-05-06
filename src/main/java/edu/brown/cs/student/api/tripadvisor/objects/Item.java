package edu.brown.cs.student.api.tripadvisor.objects;

/**
 * Interface Item that recommendation objects such as Attraction, Hotel, and
 * Restaurant implement.
 *
 */
public interface Item {

  String getName();

  double getLatitude();

  double getLongitude();

  double getDistance();

  int getNumReviews();

  String getLocationString();

  String getPhotoUrl();

  boolean isClosed();

  void setName(String name);

  void setLatitude(double latitude);

  void setLongitude(double longitude);

  void setDistance(double distance);

  void setNumReviews(int numReviews);

  void setLocationString(String locationString);

  void setPhotoUrl(String photoUrl);

  void setClosed(boolean closed);

  String toStringHTML();
}
