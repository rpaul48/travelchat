package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostCalendarEventHandler implements Route {

  @Override
  public String handle(Request request, Response response) {


    QueryParamsMap qm = request.queryMap();
    String uniqueEventID = qm.value("id");
    String chatID = qm.value("chatID");
    String title = qm.value("title");
    String startTime = qm.value("start");
    String endTime = qm.value("end");


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomRef = database.getReference("chat/room-metadata").child(chatID);
    DatabaseReference eventsRef = roomRef.child("events");

    eventsRef.child(uniqueEventID).setValueAsync(
          new CalendarEvent(uniqueEventID, title, startTime, endTime));

    return "";
  }



}
