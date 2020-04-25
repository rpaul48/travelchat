package edu.brown.cs.student.chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.CloseException;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import static j2html.TagCreator.*;
import static spark.Spark.*;

/**
 * Class for a singular group chat.
 */
public class Chat {
  // map is shared between sessions and threads --> needs to be thread-safe
  // (http://stackoverflow.com/a/2688817)
  static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
  static int nextUserNumber = 1;

  public static void main(String[] args) {
    staticFileLocation("/public");
    webSocket("/chat", ChatWebSocketHandler.class);
    init();
  }

  /**
   * Sends a message from one user to all users (also updates userlist)
   * @param sender name of user who is sending the message
   * @param message message being broadcast to the chat
   */
  public static void broadcastMessage(String sender, String message) {
    userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
      try {
        session.getRemote().sendString(String.valueOf(new JSONObject()
              .put("userMessage", createHtmlMessageFromSender(sender, message))
              .put("userlist", userUsernameMap.values())
        ));
      } catch (CloseException ex) {
        System.out.println("Web socket connection closed - was idle.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Creates an HTML message with a username, message, and timestamp
   * @param sender name of user who sent the message
   * @param message message being broadcast to the chat
   * @return returns formatted HTML for the message
   */
  private static String createHtmlMessageFromSender(String sender, String message) {
    return article().with(
          b(sender + " says:"),
          p(message),
          span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
    ).render();
  }
}
