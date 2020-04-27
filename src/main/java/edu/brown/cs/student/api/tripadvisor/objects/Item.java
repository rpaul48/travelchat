package edu.brown.cs.student.api.tripadvisor.objects;

public interface Item {
  public String getDistanceString();

  public void setDistanceString(String distanceString);

  public String getName();

  public double getLatitude();

  public double getLongitude();

  public double getDistance();

  public int getNumReviews();

  public String getLocationString();

  public String getPhotoUrl();

  public double getRating();

  public String getPriceLevel();

  public String getPriceRange();

  public int getRanking();

  public boolean isClosed();

  public void setName(String name);

  public String getRankingString();

  public void setRankingString(String rankingString);

  public void setLatitude(double latitude);

  public void setLongitude(double longitude);

  public void setDistance(double distance);

  public void setNumReviews(int numReviews);

  public void setLocationString(String locationString);

  public void setPhotoUrl(String photoUrl);

  public void setRating(double rating);

  public void setPriceLevel(String priceLevel);

  public void setPriceRange(String priceRange);

  public void setRanking(int ranking);

  public void setClosed(boolean isClosed);
}
