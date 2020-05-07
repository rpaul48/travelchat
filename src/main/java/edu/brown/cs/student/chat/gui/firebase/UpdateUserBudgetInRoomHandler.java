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
      DatabaseReference userBudgetRef = roomsRef.child(groupId).child(
            "added-users/" + uid + "/budget/");
      return updateUserBudget(userBudgetRef, type, amount);
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:");
      ex.printStackTrace();
    }
    return "0";
  }

  private String updateUserBudget(DatabaseReference userBudgetRef, String type, String amount) {
    final boolean[] done = {false};
    final String[] budget = {null};
    userBudgetRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        double currentBudget = Double.parseDouble(((String) dataSnapshot.getValue()));
        System.out.println("UPDATE THINKS USER BUDGET IS " + currentBudget);

        if ("log".equals(type)) {
          budget[0] = String.valueOf((currentBudget - Double.parseDouble(amount)));
        } else if ("add".equals(type)) {
          budget[0] = String.valueOf((currentBudget + Double.parseDouble(amount)));
        }
        System.out.println("SETTING BUDGET TO " + budget[0]);
        dataSnapshot.getRef().setValue(budget[0], (databaseError, databaseReference) -> {
          System.out.println("done updating children");
          done[0] = true;
        });
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
