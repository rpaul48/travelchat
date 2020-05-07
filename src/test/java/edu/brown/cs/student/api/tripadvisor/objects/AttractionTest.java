package edu.brown.cs.student.api.tripadvisor.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.brown.cs.student.chat.gui.Constants;

public class AttractionTest {

  @Test
  public void testVariableInitialization() {
    Attraction attr = new Attraction();
    assertEquals(attr.getName(), "");
    // Delta of 0.0001 for double variables (margin of error).
    assertEquals(attr.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(attr.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(attr.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(attr.getNumReviews(), Constants.INIT_NUM_VALUE);
    assertEquals(attr.getLocationString(), "");
    assertEquals(attr.getPhotoUrl(), "");
    assertEquals(attr.isClosed(), false);
  }

  @Test
  public void testGetSetName() {
    Attraction attr = new Attraction();
    assertEquals(attr.getName(), "");
    attr.setName("The Bard Graduate Center");
    assertEquals(attr.getName(), "The Bard Graduate Center");
  }

  @Test
  public void testGetSetLatitudeLongitude() {
    Attraction attr = new Attraction();
    assertEquals(attr.getLatitude(), Constants.INIT_NUM_VALUE, 0.0001);
    assertEquals(attr.getLongitude(), Constants.INIT_NUM_VALUE, 0.0001);
    attr.setLatitude(40.78562);
    attr.setLongitude(-73.97053);
    assertEquals(attr.getLatitude(), 40.78562, 0.0000001);
    assertEquals(attr.getLongitude(), -73.97053, 0.0000001);
  }

  @Test
  public void testGetSetDistance() {
    Attraction attr = new Attraction();
    assertEquals(attr.getDistance(), Constants.INIT_NUM_VALUE, 0.0001);
    attr.setDistance(0.1980156802635985);
    assertEquals(attr.getDistance(), 0.1980156802635985, 0.000000000001);
  }

  @Test
  public void testGetSetNumReviews() {
    Attraction attr = new Attraction();
    assertEquals(attr.getNumReviews(), Constants.INIT_NUM_VALUE);
    attr.setNumReviews(10);
    assertEquals(attr.getNumReviews(), 10);
  }

  @Test
  public void testGetSetLocationString() {
    Attraction attr = new Attraction();
    assertEquals(attr.getLocationString(), "");
    attr.setLocationString("New York, NY, United States");
    assertEquals(attr.getLocationString(), "New York, NY, United States");
  }

  @Test
  public void testGetSetPhotoUrl() {
    Attraction attr = new Attraction();
    assertEquals(attr.getPhotoUrl(), "");
    attr.setPhotoUrl(
        "https://www.tripadvisor.com/Attraction_Review-g60763-d309306-Reviews-The_Bard_Graduate_Center-New_York_City_New_York.html");
    assertEquals(attr.getPhotoUrl(),
        "https://www.tripadvisor.com/Attraction_Review-g60763-d309306-Reviews-The_Bard_Graduate_Center-New_York_City_New_York.html");
  }

  @Test
  public void testGetSetIsClosed() {
    Attraction attr = new Attraction();
    assertEquals(attr.isClosed(), false);
    attr.setClosed(true);
    assertEquals(attr.isClosed(), true);
  }
}
