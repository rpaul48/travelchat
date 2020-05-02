package edu.brown.cs.student.chat;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import edu.brown.cs.student.chat.gui.*;
import edu.brown.cs.student.chat.gui.firebase.*;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.*;

public class Main {

  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {

    // Test above this line --------------------------
    try {
      new Main(args).run();
    } catch (Exception ex) {
      System.err.println("ERROR: An exception has occurred. Printing stack trace:\n");
      ex.printStackTrace();
    }
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    try {
      runSparkServer((int) options.valueOf("port"));
    } catch (Exception ex) {
      System.out.println("ERROR: An exception occurred. Printing stack trace:\n");
      ex.printStackTrace();
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable to use %s for template loading.%n",
              templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) throws IOException {
    Spark.port(getHerokuAssignedPort());
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // setup connection to Firebase
    FileInputStream serviceAccount =
          new FileInputStream("travelchat-3120c-firebase-adminsdk-rar2p-3d1e24dc48.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://travelchat-3120c.firebaseio.com")
          .build();
    FirebaseApp.initializeApp(options);

    // Setup Spark Routes
    Spark.get("/login", new LoginFrontHandler(), freeMarker);
    Spark.get("/manage-chats", new ManageChatsFrontHandler(), freeMarker);
    Spark.get("/chat/:roomId", new ChatFrontHandler(), freeMarker);
    Spark.get("/calendar", new CalendarHandler(), freeMarker);

    // Handlers for firebase management
    Spark.post("/createRoom", new CreateRoomHandler());
    Spark.get("/getUserRooms", new GetUserRoomsHandler());
    Spark.post("/addUserToRoom", new AddUserToRoomHandler());
    Spark.post("/removeUserFromRoom", new RemoveUserFromRoomHandler());

    Spark.post("/browseRestaurants", new RestaurantsSubmitHandler());
    Spark.post("/browseActivities", new ActivitiesSubmitHandler(), freeMarker);
    Spark.post("/browseLodging", new LodgingSubmitHandler(), freeMarker);
    Spark.post("/browseFlights", new FlightsSubmitHandler());
    Spark.post("/planMyDay", new PlanMyDaySubmitHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}
