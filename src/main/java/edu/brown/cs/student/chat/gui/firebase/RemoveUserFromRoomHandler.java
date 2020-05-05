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

public class RemoveUserFromRoomHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("chat/users");
    DatabaseReference roomsRef = database.getReference("chat/room-metadata");
    QueryParamsMap qm = request.queryMap();

    String groupId = qm.value("roomId");
    String uid = qm.value("auth");

    // get UIDs from emails, then add the groupId under each UID at user/UID/added-rooms
    try {
      DatabaseReference userRef = usersRef.child(uid);
      updateUserRemovedRooms(userRef, groupId);
      try {
        updateRoomRemovedRooms(roomsRef, groupId, uid);
      } catch (Exception ex) {
        System.err.println("ERROR: An exception occurred. Printing stack trace:");
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:");
      ex.printStackTrace();
    }
    return "";
  }

  private void updateUserRemovedRooms(DatabaseReference userRef, String groupId) {
    userRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild("added-rooms")) {
          DatabaseReference userAddedRoomsRef = dataSnapshot.getRef().child("added-rooms");

          userAddedRoomsRef.child(groupId).removeValueAsync();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("ERROR: The read failed: " + databaseError.getCode());
      }
    });
  }

  private void updateRoomRemovedRooms(DatabaseReference roomsRef, String roomId, String uid) {
    roomsRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        DatabaseReference roomsRef = dataSnapshot.getRef();
        DatabaseReference roomRef = roomsRef.child(roomId);

        roomRef.child("added-users").child(uid).removeValueAsync();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("ERROR: The read failed: " + databaseError.getCode());
      }
    });
  }
}
