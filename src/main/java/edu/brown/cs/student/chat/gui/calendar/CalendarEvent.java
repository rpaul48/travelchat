package edu.brown.cs.student.chat.gui.calendar;

/**
 * Class representing an event in the calendar.
 */
public class CalendarEvent {

  private String id;
  private String title;
  private String startTimeISO;
  private String endTimeISO;
  private String location;
  private String price;
  private String description;

  /**
   * Constructs a calendar event.
   * @param id unique id of the event
   * @param title given title of the event
   * @param startTimeISO start time of the event in ISO
   * @param endTimeISO end time of the event in ISO
   * @param location location of the event
   * @param price price to participate in the event
   * @param description description of the event
   */

  public CalendarEvent(String id, String title, String startTimeISO, String endTimeISO,
                       String location,  String price, String description) {

    this.id = id;
    this.title = title;
    this.startTimeISO = startTimeISO;
    this.endTimeISO = endTimeISO;
    this.location = location;
    this.price = price;
    this.description = description;

  }

  @Override
  public String toString() {
    return title + " from " + startTimeISO + " to " + endTimeISO;
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
    return startTimeISO;
  }

  /**
   * Gets the end time.
   * @return end time
   */
  public String getEndTimeISO() {
    return endTimeISO;
  }

  /**
   * Gets the id.
   * @return id
   */
  public String getId() {
    return id;
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
