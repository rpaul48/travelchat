package edu.brown.cs.student.chat.gui.calendar;

import jdk.nashorn.internal.runtime.JSONFunctions;

import java.util.HashMap;
import java.util.Map;

public class CalendarEvent {

  private String title;
  private String startTimeISO;
  private String endTimeISO;
  private Map<String, String> JSONMap = new HashMap<>();

  public CalendarEvent(String title, String startTimeISO, String endTimeISO) {

    this.title = title;
    this.startTimeISO = startTimeISO;
    this.endTimeISO = endTimeISO;

    JSONMap.put("title", this.title);
    JSONMap.put("startTimeISO", this.startTimeISO);
    JSONMap.put("endTimeISO", this.endTimeISO);

  }

  public Map<String, String> getMapForJSON() {

    return JSONMap;
  }


  public String getTitle() { return title; }

  public String getStartTime() { return startTimeISO; }

  public String getEndTime() { return endTimeISO; }

  @Override
  public String toString() {
    return title + " from " + startTimeISO + " to " + endTimeISO;
  }

}
