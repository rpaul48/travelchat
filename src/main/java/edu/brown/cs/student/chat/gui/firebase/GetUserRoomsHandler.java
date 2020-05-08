package edu.brown.cs.student.chat.gui.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONObject;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles getting a user's rooms.
 */
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
      ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
