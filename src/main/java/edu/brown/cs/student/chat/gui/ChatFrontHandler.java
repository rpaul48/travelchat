package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

/**
 * Handles serving Freemarker template to front-end for the chat.
 */
public class ChatFrontHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> variables = ImmutableMap.of("title", "TravelChat Login",
            "errors", "");
    return new ModelAndView(variables, "chat.ftl");
  }
}
