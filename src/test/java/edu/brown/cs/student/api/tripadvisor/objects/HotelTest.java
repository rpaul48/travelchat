package edu.brown.cs.student.api.tripadvisor.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.brown.cs.student.chat.gui.Constants;

public class HotelTest {

  @Test
  public void testVariableInitialization() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getName(), "");
    // Delta of 0.0001 for double variables (margin of error).
    assertEquals(hotel.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(hotel.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(hotel.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(hotel.getNumReviews(), Constants.INIT_NUM_VALUE);
    assertEquals(hotel.getLocationString(), "");
    assertEquals(hotel.getPhotoUrl(), "");
    assertEquals(hotel.isClosed(), false);
    assertEquals(hotel.getRating(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(hotel.getPriceLevel(), "");
    assertEquals(hotel.getPriceRange(), "");
    assertEquals(hotel.getRanking(), Constants.INIT_NUM_VALUE);
    assertEquals(hotel.getRankingString(), "");
    assertEquals(hotel.getHotelClass(), Constants.INIT_NUM_VALUE, 0.0001);
  }

  @Test
  public void testGetSetName() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getName(), "");
    hotel.setName("Henry Norman Hotel");
    assertEquals(hotel.getName(), "Henry Norman Hotel");
  }

  @Test
  public void testGetSetLatitudeLongitude() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(hotel.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    hotel.setLatitude(40.728012);
    hotel.setLongitude(-73.94395);
    assertEquals(hotel.getLatitude(), 40.728012, 0.0000001);
    assertEquals(hotel.getLongitude(), -73.94395, 0.0000001);
  }

  @Test
  public void testGetSetDistance() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    hotel.setDistance(0.1980156802635985);
    assertEquals(hotel.getDistance(), 0.1980156802635985, 0.000000000001);
  }

  @Test
  public void testGetSetNumReviews() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getNumReviews(), Constants.INIT_NUM_VALUE);
    hotel.setNumReviews(10);
    assertEquals(hotel.getNumReviews(), 10);
  }

  @Test
  public void testGetSetLocationString() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getLocationString(), "");
    hotel.setLocationString("New York, NY, United States");
    assertEquals(hotel.getLocationString(), "New York, NY, United States");
  }

  @Test
  public void testGetSetPhotoUrl() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getPhotoUrl(), "");
    hotel.setPhotoUrl(
        "https://media-cdn.tripadvisor.com/media/photo-l/19/ef/90/7b/hotel-entrance.jpg");
    assertEquals(hotel.getPhotoUrl(),
        "https://media-cdn.tripadvisor.com/media/photo-l/19/ef/90/7b/hotel-entrance.jpg");
  }

  @Test
  public void testIsSetClosed() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.isClosed(), false);
    hotel.setClosed(true);
    assertEquals(hotel.isClosed(), true);
  }

  @Test
  public void testGetSetRating() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getRating(), Constants.INIT_NUM_VALUE, 0.0001);
    hotel.setRating(4.5);
    assertEquals(hotel.getRating(), 4.5, 0.000000000001);
  }

  @Test
  public void testGetSetPriceLevel() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getPriceLevel(), "");
    hotel.setPriceLevel("$$");
    assertEquals(hotel.getPriceLevel(), "$$");
  }

  @Test
  public void testGetSetPrice() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getPriceRange(), "");
    hotel.setPriceRange("$129 - $475");
    assertEquals(hotel.getPriceRange(), "$129 - $475");
  }

  @Test
  public void testGetSetRanking() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getRanking(), Constants.INIT_NUM_VALUE);
    hotel.setRanking(1);
    assertEquals(hotel.getRanking(), 1);
  }

  @Test
  public void testGetSetRankingString() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getRankingString(), "");
    hotel.setRankingString("#1 Best Value of 509 hotels in New York City");
    assertEquals(hotel.getRankingString(), "#1 Best Value of 509 hotels in New York City");
  }

  @Test
  public void testGetSetHotelClass() {
    Hotel hotel = new Hotel();
    assertEquals(hotel.getHotelClass(), Constants.INIT_NUM_VALUE, 0.0001);
    hotel.setHotelClass(4.0);
    assertEquals(hotel.getHotelClass(), 4.0, 0.000000000001);
  }
}
