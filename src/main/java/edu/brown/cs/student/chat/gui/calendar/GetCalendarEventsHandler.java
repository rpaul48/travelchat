package edu.brown.cs.student.chat.gui.calendar;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCalendarEventsHandler implements Route {

  private Map<String, List<CalendarEvent>> calendarEvents;

  public GetCalendarEventsHandler(Map<String, List<CalendarEvent>> calendarEvents) {
    this.calendarEvents = calendarEvents;
  }

  @Override
  public JSONArray handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();
    String chatID = qm.value("chatID");

    List<Map<String, String>> jsonArray = new ArrayList<>();
    if (calendarEvents.containsKey(chatID)) {
      for (CalendarEvent event : calendarEvents.get(chatID)) {
        jsonArray.add(event.getMapForJSON());
      }
    }
    return new JSONArray(jsonArray);
  }
}
