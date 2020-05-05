package edu.brown.cs.student.chat.gui;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

public class ManageChatsFrontHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> variables = ImmutableMap.of("title", "Manage Chats",
            "errors", "");
    return new ModelAndView(variables, "manage-chats.ftl");
  }
}
