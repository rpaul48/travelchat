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

public class UpdateUserBudgetInRoomHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomsRef = database.getReference("chat/room-metadata");
    QueryParamsMap qm = request.queryMap();

    String groupId = qm.value("roomId");
    String uid = qm.value("auth");
    String type = qm.value("type");
    String amount = qm.value("amount");

    try {
      DatabaseReference userRef = roomsRef.child(groupId).child("added-users/" + uid);
      return updateUserBudget(userRef, type, amount);
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:");
      ex.printStackTrace();
    }
    return "0";
  }

  private String updateUserBudget(DatabaseReference userRef, String type, String amount) {
    final boolean[] done = {false};
    final String[] budget = {"0"};
    userRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Map<String, Object> budgetUpdate = new HashMap<>();
        double currentBudget = Double.parseDouble(
              ((String) dataSnapshot.child("budget").getValue()));

        if ("log".equals(type)) {
          budget[0] = String.valueOf((currentBudget - Double.parseDouble(amount)));
        } else if ("add".equals(type)) {
          budget[0] = String.valueOf((currentBudget + Double.parseDouble(amount)));
        }
        budgetUpdate.put("budget", budget[0]);
        dataSnapshot.getRef().updateChildrenAsync(budgetUpdate);
        done[0] = true;
        // need to remove the event listener or else the update will trigger an infinite loop
        userRef.removeEventListener(this);
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
