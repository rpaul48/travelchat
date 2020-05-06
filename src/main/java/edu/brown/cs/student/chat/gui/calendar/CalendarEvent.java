package edu.brown.cs.student.chat.gui.calendar;


public class CalendarEvent {
  private String title;
  private String startTimeISO;
  private String endTimeISO;
  private String id;


  public CalendarEvent(String id, String title, String startTimeISO, String endTimeISO) {
    this.id = id;
    this.title = title;
    this.startTimeISO = startTimeISO;
    this.endTimeISO = endTimeISO;
  }

  @Override
  public String toString() {
    return title + " from " + startTimeISO + " to " + endTimeISO;
  }
}
