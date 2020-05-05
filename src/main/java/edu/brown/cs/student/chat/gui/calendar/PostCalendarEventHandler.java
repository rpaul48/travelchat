package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.*;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

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

    eventsRef.child(uniqueEventID).setValueAsync(new CalendarEvent(uniqueEventID, title, startTime, endTime));



    // Increment the total number of events atomically.
//    DatabaseReference eventCountRef = eventsRef.child("numEvents");
//    eventCountRef.runTransaction(new Transaction.Handler() {
//      @Override
//      public Transaction.Result doTransaction(MutableData mutableData) {
//        Integer currentValue = mutableData.getValue(Integer.class);
//        if (currentValue == null) {
//          mutableData.setValue(1);
//        } else {
//          mutableData.setValue(currentValue + 1);
//        }
//
//        return Transaction.success(mutableData);
//      }
//
//      @Override
//      public void onComplete(
//              DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
//        System.out.println("Transaction completed");
//      }
//    });


    return "";
  }



}
