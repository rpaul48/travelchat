package edu.brown.cs.student.chat.gui;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import spark.*;

import java.io.FileInputStream;
import java.io.IOException;

public class uidHandler implements Route {

  public uidHandler() throws IOException {
    FileInputStream serviceAccount =
          new FileInputStream("travelchat-3120c-firebase-adminsdk-rar2p-3d1e24dc48.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://travelchat-3120c.firebaseio.com")
          .build();

    FirebaseApp.initializeApp(options);
  }

  @Override
  public String handle(Request request, Response response) {
    String ret = "";
    QueryParamsMap qm = request.queryMap();

    System.out.println("Printing request: " + qm.toString());
    String auth = qm.value("auth");
    System.out.println("Printing auth: " + auth);

    try {
      // look up the provided uid. if it exists, we carry on and perform the lookup, since
      // the user making the request is authenticated. else, an error is thrown.
      FirebaseAuth.getInstance().getUser(auth);

      String email = qm.value("email");
      UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
      System.out.println("Printing desired uid: " + userRecord.getUid());
      return userRecord.getUid();
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:\n");
      ex.printStackTrace();
      return ret;
    }
  }
}
