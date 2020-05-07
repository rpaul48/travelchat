package edu.brown.cs.student.api.tripadvisor.objects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.student.chat.gui.Constants;

public class RestaurantTest {

  @Test
  public void testVariableInitialization() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getName(), "");
    // Delta of 0.0001 for double variables (margin of error).
    assertEquals(restaurant.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(restaurant.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(restaurant.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(restaurant.getNumReviews(), Constants.INIT_NUM_VALUE);
    assertEquals(restaurant.getLocationString(), "");
    assertEquals(restaurant.getPhotoUrl(), "");
    assertEquals(restaurant.isClosed(), false);
    assertEquals(restaurant.getRating(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(restaurant.getPriceLevel(), "");
    assertEquals(restaurant.getRanking(), Constants.INIT_NUM_VALUE);
    assertEquals(restaurant.getRankingString(), "");
    assertEquals(restaurant.getAddress(), "");
    assertEquals(restaurant.getCuisineTypes().size(), 0);
  }

  @Test
  public void testGetSetName() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getName(), "");
    restaurant.setName("NY Restaurant");
    assertEquals(restaurant.getName(), "NY Restaurant");
  }

  @Test
  public void testGetSetLatitudeLongitude() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(restaurant.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    restaurant.setLatitude(40.728012);
    restaurant.setLongitude(-73.94395);
    assertEquals(restaurant.getLatitude(), 40.728012, 0.0000001);
    assertEquals(restaurant.getLongitude(), -73.94395, 0.0000001);
  }

  @Test
  public void testGetSetDistance() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    restaurant.setDistance(0.1980156802635985);
    assertEquals(restaurant.getDistance(), 0.1980156802635985, 0.000000000001);
  }

  @Test
  public void testGetSetNumReviews() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getNumReviews(), Constants.INIT_NUM_VALUE);
    restaurant.setNumReviews(10);
    assertEquals(restaurant.getNumReviews(), 10);
  }

  @Test
  public void testGetSetLocationString() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getLocationString(), "");
    restaurant.setLocationString("New York, NY, United States");
    assertEquals(restaurant.getLocationString(), "New York, NY, United States");
  }

  @Test
  public void testGetSetPhotoUrl() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getPhotoUrl(), "");
    restaurant.setPhotoUrl(
        "https://media-cdn.tripadvisor.com/media/photo-l/19/ef/90/7b/restaurant-entrance.jpg");
    assertEquals(restaurant.getPhotoUrl(),
        "https://media-cdn.tripadvisor.com/media/photo-l/19/ef/90/7b/restaurant-entrance.jpg");
  }

  @Test
  public void testIsSetClosed() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.isClosed(), false);
    restaurant.setClosed(true);
    assertEquals(restaurant.isClosed(), true);
  }

  @Test
  public void testGetSetRating() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getRating(), Constants.INIT_NUM_VALUE, 0.0001);
    restaurant.setRating(4.5);
    assertEquals(restaurant.getRating(), 4.5, 0.000000000001);
  }

  @Test
  public void testGetSetPriceLevel() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getPriceLevel(), "");
    restaurant.setPriceLevel("$$");
    assertEquals(restaurant.getPriceLevel(), "$$");
  }

  @Test
  public void testGetSetRanking() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getRanking(), Constants.INIT_NUM_VALUE);
    restaurant.setRanking(1);
    assertEquals(restaurant.getRanking(), 1);
  }

  @Test
  public void testGetSetRankingString() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getRankingString(), "");
    restaurant.setRankingString("#1 Best Value of 509 restaurants in New York City");
    assertEquals(restaurant.getRankingString(),
        "#1 Best Value of 509 restaurants in New York City");
  }

  @Test
  public void testGetSetAddress() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getRankingString(), "");
    restaurant.setRankingString("148 Flushing Ave, Brooklyn, NY 11205-1112");
    assertEquals(restaurant.getRankingString(), "148 Flushing Ave, Brooklyn, NY 11205-1112");
  }

  @Test
  public void testGetSetCuisineTypes() {
    Restaurant restaurant = new Restaurant();
    assertEquals(restaurant.getCuisineTypes().size(), 0);
    List<String> cuisines = new ArrayList<String>();
    cuisines.add("American");
    cuisines.add("Italian");
    restaurant.setCuisineTypes(cuisines);
    assertEquals(restaurant.getCuisineTypes().size(), 2);
    assertEquals(restaurant.getCuisineTypes().get(0), "American");
    assertEquals(restaurant.getCuisineTypes().get(1), "Italian");
  }
}
