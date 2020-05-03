package edu.brown.cs.student.chat.gui.calendar;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostCalendarEventHandler implements Route {


  private Map<String, List<CalendarEvent>> calendarEvents;

  public PostCalendarEventHandler(Map<String, List<CalendarEvent>> calendarEvents) {
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
