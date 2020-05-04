package edu.brown.cs.student.chat.gui.calendar;


public class CalendarEvent {

  public String title;
  public String startTimeISO;
  public String endTimeISO;
  public String id;

  public CalendarEvent(String id, String title, String startTimeISO, String endTimeISO) {

    this.id = id;
    this.title = title;
    this.startTimeISO = startTimeISO;
    this.endTimeISO = endTimeISO;

  }

  public CalendarEvent() {
  }


  @Override
  public String toString() {
    return title + " from " + startTimeISO + " to " + endTimeISO;
  }

}
