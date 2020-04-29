package edu.brown.cs.student.api.tripadvisor.objects;

public interface Item {

  public String getName();

  public double getLatitude();

  public double getLongitude();

  public double getDistance();

  public int getNumReviews();

  public String getLocationString();

  public String getPhotoUrl();

  public boolean isClosed();

  public void setName(String name);

  public void setLatitude(double latitude);

  public void setLongitude(double longitude);

  public void setDistance(double distance);

  public void setNumReviews(int numReviews);

  public void setLocationString(String locationString);

  public void setPhotoUrl(String photoUrl);

  public void setClosed(boolean isClosed);
}
