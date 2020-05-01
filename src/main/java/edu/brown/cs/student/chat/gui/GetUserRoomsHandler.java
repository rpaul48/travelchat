package edu.brown.cs.student.chat.gui;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GetUserRoomsHandler implements Route {

  @Override
  public JSONObject handle(Request request, Response response) {
    final boolean[] done = {false};
    Map<String, String> jsonMap = new HashMap<>();
    QueryParamsMap qm = request.queryMap();

    String uid = qm.value("uid");
    try {
      // get a reference to user's rooms
      final FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference ref = database.getReference("chat/users/" + uid + "/added-rooms");

      // attach a listener to read the data at our reference
      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          if (dataSnapshot.hasChildren()) {
            HashMap<String, HashMap<String, String>> data =
                  (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
            for (String key : data.keySet()) {
              jsonMap.put(key, data.get(key).get("groupName"));
            }
          }
          done[0] = true;
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.err.println("ERROR: The read failed: " + databaseError.getCode());
        }
      });
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:\n");
      ex.printStackTrace();
    }

    try {
      while (!done[0]) {
        Thread.sleep(1);
      }
    } catch(InterruptedException ex) {
      ex.printStackTrace();
      Thread.currentThread().interrupt();
    }
    JSONObject ret = new JSONObject(jsonMap);
    return ret;
  }
}
