package edu.brown.cs.student.chat.gui.calendar;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an event in the calendar.
 */
public class CalendarEvent {

  private String id;
  private String ownerID;
  private String title;
  private String start;
  private String end;
  private String location;
  private String price;
  private String description;
  private Map<String, String> JSONMap = new HashMap<>();

  /**
   * Constructs a calendar event.
   * @param id unique id of the event
   * @param title given title of the event
   * @param start start time of the event in ISO format
   * @param end end time of the event in ISO format
   * @param location location of the event
   * @param price price to participate in the event
   * @param description description of the event
   */

  public CalendarEvent(String id, String ownerID, String title, String start, String end,
                       String location,  String price, String description) {

    this.id = id;
    this.ownerID = ownerID;
    this.title = title;
    this.start = start;
    this.end = end;
    this.location = location;
    this.price = price;
    this.description = description;

    JSONMap.put("id", id);
    JSONMap.put("ownerID", ownerID);
    JSONMap.put("title", title);
    JSONMap.put("start", start);
    JSONMap.put("end", end);
    JSONMap.put("location", location);
    JSONMap.put("price", price);
    JSONMap.put("description", description);

  }

  public Map<String, String> getMapForJSON() {
    return JSONMap;
  }

  @Override
  public String toString() {
    return title + " from " + start + " to " + end;
  }

  /**
   * Gets the title.
   * @return title
   */
  public String  getTitle() {
    return title;
  }

  /**
   * Gets the start time.
   * @return start time
   */
  public String getStartTimeISO() {
    return start;
  }

  /**
   * Gets the end time.
   * @return end time
   */
  public String getEndTimeISO() {
    return end;
  }

  /**
   * Gets the id.
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the owner's id.
   * @return ownerID
   */
  public String getOwnerID() {
    return ownerID;
  }


  /**
   * Gets location.
   * @return location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Gets price.
   * @return price
   */
  public String getPrice() {
    return price;
  }

  /**
   * Gets description.
   * @return description
   */
  public String getDescription() {
    return description;
  }
}
