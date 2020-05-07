package edu.brown.cs.student.chat.gui.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
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

/**
 * Handles adding a user to a room.
 */
public class AddUserToRoomHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("chat/users");
    DatabaseReference roomsRef = database.getReference("chat/room-metadata");
    QueryParamsMap qm = request.queryMap();

    String email = qm.value("email");
    String groupId = qm.value("roomId");
    String groupName = qm.value("groupName");
    String uid = "";

    // get UIDs from emails, then add the groupId under each UID at user/UID/added-rooms
    try {
      if (email != null && !email.equals("")) {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        uid = userRecord.getUid();

        DatabaseReference userRef = usersRef.child(uid);
        updateUserAddedRooms(userRef, groupId, groupName);

        try {
          updateRoomAddedRooms(roomsRef, groupId, uid);
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

  private void updateUserAddedRooms(DatabaseReference userRef, String groupId, String groupName) {
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        // if the user has an 'added-rooms' dir & is not already in this room, add it
        if (dataSnapshot.hasChild("added-rooms")
              && !dataSnapshot.child("added-rooms").hasChild(groupId)) {
          DatabaseReference userAddedRoomsRef = dataSnapshot.getRef().child("added-rooms");
          Map<String, Object> userUpdates = new HashMap<>();
          Map<String, Object> roomDetails = new HashMap<>();
          roomDetails.put("groupId", groupId);
          roomDetails.put("groupName", groupName);
          userUpdates.put(groupId, roomDetails);

          userAddedRoomsRef.updateChildrenAsync(userUpdates);
        } else if (!dataSnapshot.hasChild("added-rooms")) {
          // if the user does not have an 'added-rooms' dir
          DatabaseReference userRef = dataSnapshot.getRef();
          Map<String, Object> userUpdates = new HashMap<>();
          Map<String, Object> roomDetails = new HashMap<>();
          roomDetails.put("groupId", groupId);
          roomDetails.put("groupName", groupName);
          userUpdates.put("added-rooms/" + groupId, roomDetails);

          userRef.updateChildrenAsync(userUpdates);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("ERROR: The read failed: " + databaseError.getCode());
      }
    });
  }

  private void updateRoomAddedRooms(DatabaseReference roomsRef, String roomId, String uid) {
    roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        DatabaseReference roomsRef = dataSnapshot.getRef();
        DatabaseReference roomRef = roomsRef.child(roomId);

        // if the room does not already have this user, add them
        if (!dataSnapshot.child(roomId).child("added-users").hasChild(uid)) {
          Map<String, Object> roomUpdates = new HashMap<>();
          Map<String, Object> roomDetails = new HashMap<>();
          roomDetails.put("uid", uid);
          roomDetails.put("budget", "0");
          roomUpdates.put("added-users/" + uid, roomDetails);

          roomRef.updateChildrenAsync(roomUpdates);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.err.println("ERROR: The read failed: " + databaseError.getCode());
      }
    });
  }
}
