package edu.brown.cs.student.chat.gui.calendar;


import java.util.HashMap;
import java.util.Map;

public class CalendarEvent {
  public String id;
  public String title;
  public String startTimeISO;
  public String endTimeISO;
  public String location;
  public String price;
  public String description;


  public CalendarEvent(String id, String title, String startTimeISO, String endTimeISO, String location,  String price, String description) {
    this.id = id;
    this.title = title;
    this.startTimeISO = startTimeISO;
    this.endTimeISO = endTimeISO;
    this.location = location;
    this.price = price;
    this.description = description;

  }

  public CalendarEvent() {
  }

  @Override
  public String toString() {
    return title + " from " + startTimeISO + " to " + endTimeISO;
  }
}
