package edu.brown.cs.student.chat.gui;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class uidHandler implements Route {

  @Override
  public String handle(Request request, Response response) {
    String ret = "";
    QueryParamsMap qm = request.queryMap();

    String auth = qm.value("auth");

    try {
      // look up the provided uid. if it exists, we carry on and perform the lookup, since
      // the user making the request is authenticated. else, an error is thrown.
      FirebaseAuth.getInstance().getUser(auth);

      String email = qm.value("email");
      if (email != null && !email.equals("")) {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        ret = userRecord.getUid();
      }
    } catch (Exception ex) {
      System.err.println("ERROR: An exception occurred. Printing stack trace:\n");
      ex.printStackTrace();
    }
    return ret;
  }
}
