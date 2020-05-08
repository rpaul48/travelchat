package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

/**
 * Used to remove a calendar event from the database.
 */
public class RemoveCalendarEventHandler implements Route {

  @Override
  public Object handle(Request request, Response response) {


    String uniqueEventID = UUID.randomUUID().toString();
    try {
      QueryParamsMap qm = request.queryMap();
      String chatID = qm.value("chatID");
      String eventID = qm.value("eventID");


      final FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference roomRef = database.getReference("chat/room-metadata").child(chatID);
      DatabaseReference eventRef = roomRef.child("events").child(eventID);

      eventRef.removeValueAsync();


    } catch (Exception ex) {
      System.err.println("ERROR: An error occurred posting calendar event. Printing stack trace:");
      ex.printStackTrace();
    }

    return "";
  }
}
