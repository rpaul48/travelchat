package edu.brown.cs.student.chat.gui.calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    final boolean[] done = {false};
    List<Map<String, String>> jsonArray = new ArrayList<>();

    eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
          Map<String, Map<String, String>> data =
                  (Map<String, Map<String, String>>) dataSnapshot.getValue();
          for (String key : data.keySet()) {
            jsonArray.add(data.get(key));
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

    return new JSONArray(jsonArray);
  }
}
