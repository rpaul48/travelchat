package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetCalendarEventsHandler implements Route {

  @Override
  public JSONArray handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();
    String chatID = qm.value("chatID");

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomRef = database.getReference("chat/room-metadata").child(chatID);
    DatabaseReference eventsRef = roomRef.child("events");

    List<Map<String, String>> jsonArray = new ArrayList<>();
//    if (calendarEvents.containsKey(chatID)) {
//      for (CalendarEvent event : calendarEvents.get(chatID)) {
//        jsonArray.add(event.getMapForJSON());
//      }
//    }
    return new JSONArray(jsonArray);
  }
}
