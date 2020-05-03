package edu.brown.cs.student.chat.gui.calendar;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Expected input: an JSON array of event objects
 *    i.e. [ {title: title1, start: 2020-05-02T12:30:00, end: 2020-05-02T16:30:00, chatID: 01},
 *           {title: title2, start: 2020-05-03T12:30:00, end: 2020-05-04T13:30:00}, chatID: 02]
 *
 * The start and end times must be in ISO format.
 */
public class postCalendarEvent implements Route {

  private Map<String, List<CalendarEvent>> calendarEvents;

  public postCalendarEvent(Map<String, List<CalendarEvent>> calendarEvents) {
    this.calendarEvents = calendarEvents;
  }

  //TODO: Add price, description parameters.
  //TODO: Throw error if inputs are not correct
  @Override
  public String handle(Request request, Response response) {

    QueryParamsMap qm = request.queryMap();
    String chatID = qm.value("chatID");
    String title = qm.value("title");
    String startTime = qm.value("start");
    String endTime = qm.value("end");

    CalendarEvent event = new CalendarEvent(title, startTime, endTime);

    if (!calendarEvents.containsKey(chatID)) {
      calendarEvents.put(chatID, new ArrayList<>());
    }
    calendarEvents.get(chatID).add(event);

    return "";
  }



}
