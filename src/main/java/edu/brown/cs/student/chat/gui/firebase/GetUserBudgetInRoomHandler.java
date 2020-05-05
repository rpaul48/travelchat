package edu.brown.cs.student.chat.gui.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GetUserBudgetInRoomHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomsRef = database.getReference("chat/room-metadata");
    QueryParamsMap qm = request.queryMap();

    String groupId = qm.value("roomId");
    String uid = qm.value("auth");

    try {
      DatabaseReference userRef = roomsRef.child(groupId).child("added-users/" + uid);
      return getUserBudget(userRef);
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:");
      ex.printStackTrace();
    }
    return "0";
  }

  private String getUserBudget(DatabaseReference userRef) {
    final boolean[] done = {false};
    final String[] budget = {"0"};
    userRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild("budget")) {
          // if there is a budget already set, get it
          budget[0] = (String) dataSnapshot.child("budget").getValue();
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

    return budget[0];
  }
}
