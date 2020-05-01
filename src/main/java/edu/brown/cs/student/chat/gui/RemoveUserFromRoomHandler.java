package edu.brown.cs.student.chat.gui;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
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

    String email = qm.value("email");
    String groupId = qm.value("roomId");
    String uid = "";

    // get UIDs from emails, then add the groupId under each UID at user/UID/added-rooms
    try {
      if (email != null && !email.equals("")) {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        uid = userRecord.getUid();

        DatabaseReference userRef = usersRef.child(uid);
        updateUserRemovedRooms(userRef, groupId);

        try {
          updateRoomRemovedRooms(roomsRef, groupId, uid);
        } catch (Exception ex) {
          System.err.println("ERROR: An exception occurred. Printing stack trace:");
          ex.printStackTrace();
        }
      }
    } catch (FirebaseAuthException ex) {
      System.err.println("ERROR: No user record found for the provided email: " + email);
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
