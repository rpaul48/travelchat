package edu.brown.cs.student.chat.gui.firebase;

import com.google.firebase.auth.FirebaseAuth;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles creating a room.
 */
public class CreateRoomHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("chat/users");
    DatabaseReference roomsRef = database.getReference("chat/room-metadata");
    QueryParamsMap qm = request.queryMap();

    String auth = qm.value("auth");
    String emailsOneString = qm.value("emails");
    String[] emails = emailsOneString.split(",");
    String groupId = qm.value("groupId");
    String groupName = qm.value("groupName");

    List<String> uids = new ArrayList<>();
    uids.add(auth);

    // get UIDs from emails, then add the groupId under each UID at user/UID/added-rooms
    for (String email : emails) {
      try {
        if (email != null && !email.equals("")) {
          UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
          String uid = userRecord.getUid();
          uids.add(uid);
          DatabaseReference userRef = usersRef.child(uid);
          updateUserAddedRooms(userRef, groupId, groupName);
        }
      } catch (Exception ex) {
        System.err.println("ERROR: An exception occurred. Printing stack trace:\n");
        ex.printStackTrace();
      }
    }
    updateUserAddedRooms(usersRef.child(auth), groupId, groupName);

    try {
      createRoomAddedRooms(roomsRef, groupId, uids);
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:\n");
      ex.printStackTrace();
    }
    return "";
  }

  private void updateUserAddedRooms(DatabaseReference userRef, String groupId, String groupName) {
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild("added-rooms")) {
          DatabaseReference userAddedRoomsRef = dataSnapshot.getRef().child("added-rooms");
          Map<String, Object> userUpdates = new HashMap<>();
          Map<String, Object> roomDetails = new HashMap<>();
          roomDetails.put("groupId", groupId);
          roomDetails.put("groupName", groupName);
          userUpdates.put(groupId, roomDetails);

          userAddedRoomsRef.updateChildrenAsync(userUpdates);
        } else {
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

  private void createRoomAddedRooms(DatabaseReference roomsRef, String roomId, List<String> uids) {
    roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        DatabaseReference roomsRef = dataSnapshot.getRef();
        DatabaseReference roomRef = roomsRef.child(roomId);

        for (String uid : uids) {
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
