package edu.brown.cs.student.chat.gui.firebase;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

public class ChatFrontHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> variables = ImmutableMap.of("title", "TravelChat Login",
            "errors", "");
    return new ModelAndView(variables, "chat.ftl");
  }
}
