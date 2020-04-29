package edu.brown.cs.student.api.tripadvisor.objects;

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

  void setClosed(boolean isClosed);
}
