package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.*;
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

public class GetUsersOfEventHandler implements Route {

  @Override
  public Object handle(Request request, Response response) {


    QueryParamsMap qm = request.queryMap();
    String chatID = qm.value("chatID");
    String eventID = qm.value("eventID");

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomRef = database.getReference("chat/room-metadata").child(chatID);
    DatabaseReference participantsRef = roomRef.child("events").child(eventID).child("participants");

    final boolean[] done = {false};
    Map<String, String> jsonMap= new HashMap<>();

    participantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
          Map<String, String> participants = (Map<String, String>) dataSnapshot.getValue();
          for (String key : participants.keySet()) {
            jsonMap.put(participants.get(key), "true");
          }
        }
        done[0] = true;
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("ERROR: The read failed: " + databaseError.getCode());
      }
    });

    try {
      // necessary because ValueEventListener is asynchronous, but we don't want that
      while (!done[0]) {
        Thread.sleep(1);
      }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
      Thread.currentThread().interrupt();
    }


    return new JSONObject(jsonMap);
  }

}
