package edu.brown.cs.student.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import edu.brown.cs.student.chat.gui.BrowseActivitiesHandler;
import edu.brown.cs.student.chat.gui.BrowseFlightsHandler;
import edu.brown.cs.student.chat.gui.BrowseLodgingHandler;
import edu.brown.cs.student.chat.gui.BrowseRestaurantsHandler;
import edu.brown.cs.student.chat.gui.ChatFrontHandler;
import edu.brown.cs.student.chat.gui.LoginFrontHandler;
import edu.brown.cs.student.chat.gui.ManageChatsFrontHandler;
import edu.brown.cs.student.chat.gui.PlanMyDayHandler;
import edu.brown.cs.student.chat.gui.calendar.AddRemoveUserFromEventHandler;
import edu.brown.cs.student.chat.gui.calendar.CalendarFrontHandler;
import edu.brown.cs.student.chat.gui.calendar.GetCalendarEventsHandler;
import edu.brown.cs.student.chat.gui.calendar.PostCalendarEventHandler;
import edu.brown.cs.student.chat.gui.firebase.AddUserToRoomHandler;
import edu.brown.cs.student.chat.gui.firebase.CreateRoomHandler;
import edu.brown.cs.student.chat.gui.firebase.GetUserBudgetInRoomHandler;
import edu.brown.cs.student.chat.gui.firebase.GetUserRoomsHandler;
import edu.brown.cs.student.chat.gui.firebase.RemoveUserFromRoomHandler;
import edu.brown.cs.student.chat.gui.firebase.UpdateUserBudgetInRoomHandler;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public final class Main {
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
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
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
      System.out.printf("ERROR: Unable to use %s for template loading.%n", templates);
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
    FileInputStream serviceAccount = new FileInputStream(
        "travelchat-3120c-firebase-adminsdk-rar2p-3d1e24dc48.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://travelchat-3120c.firebaseio.com").build();
    FirebaseApp.initializeApp(options);

    // Setup Spark Routes
    Spark.get("/login", new LoginFrontHandler(), freeMarker);
    Spark.get("/manage-chats", new ManageChatsFrontHandler(), freeMarker);
    Spark.get("/chat/:roomId", new ChatFrontHandler(), freeMarker);

    // Handlers for firebase management
    Spark.post("/createRoom", new CreateRoomHandler());
    Spark.get("/getUserRooms", new GetUserRoomsHandler());
    Spark.post("/addUserToRoom", new AddUserToRoomHandler());
    Spark.post("/removeUserFromRoom", new RemoveUserFromRoomHandler());
    Spark.post("/getUserBudgetInRoom", new GetUserBudgetInRoomHandler());
    Spark.post("/updateUserBudgetInRoom", new UpdateUserBudgetInRoomHandler());

    // Handlers for querying menus within TravelChat chat interface
    Spark.get("/browseRestaurants", new BrowseRestaurantsHandler());
    Spark.get("/browseActivities", new BrowseActivitiesHandler());
    Spark.get("/browseLodging", new BrowseLodgingHandler());
    Spark.get("/browseFlights", new BrowseFlightsHandler());
    Spark.get("/planMyDay", new PlanMyDayHandler());

    // Calendar management
    Spark.get("/calendar/:roomId/:userId", new CalendarFrontHandler(), freeMarker);
    Spark.get("/getCalendarEvents", new GetCalendarEventsHandler());
    Spark.post("/postCalendarEvent", new PostCalendarEventHandler());
    Spark.post("/addRemoveUserFromEventHandler", new AddRemoveUserFromEventHandler());
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
    // return default port if heroku-port isn't set (i.e. on localhost)
    return DEFAULT_PORT;
  }

}
