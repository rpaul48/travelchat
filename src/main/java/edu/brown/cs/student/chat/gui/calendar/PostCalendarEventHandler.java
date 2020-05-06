package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

/**
 * Used to post a new event to the calendar. When making a post, pass in an object
 * with the string parameter (i.e. data = {"title": Kayaking, "location": Miami,...})
 *
 *      "chatID" = id of the chat
 *      "title" = title of the event
 *      "start" = start time in ISO format (i.e. 2020-05-22T21:38:00)
 *      "end" = end time in ISO format
 *      "location" = location of the event
 *      "price" = price of the event in USD (i.e. 50.00)
 *      "description" = other details of the event. It may be good to include the
 *          link to the website of certain events, such as flights
 *
 */
public class PostCalendarEventHandler implements Route {

  @Override
  public String handle(Request request, Response response) {


    String uniqueEventID = UUID.randomUUID().toString();
    try {
      QueryParamsMap qm = request.queryMap();
      String chatID = qm.value("chatID");
      String title = qm.value("title");
      String startTime = qm.value("start");
      String endTime = qm.value("end");
      String location = qm.value("location");
      String price = qm.value("price");
      String description = qm.value("description");


      final FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference roomRef = database.getReference("chat/room-metadata").child(chatID);
      DatabaseReference eventsRef = roomRef.child("events");

      eventsRef.child(uniqueEventID).setValueAsync(
              new CalendarEvent(uniqueEventID, title, startTime, endTime, location, price, description));
    } catch (Exception ex) {
      System.err.println("ERROR: An error occurred posting calendar event. Printing stack trace:");
      ex.printStackTrace();
    }

    return uniqueEventID;
  }
}
