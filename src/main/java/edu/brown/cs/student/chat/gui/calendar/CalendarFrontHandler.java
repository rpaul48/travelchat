package edu.brown.cs.student.chat.gui.calendar;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Collections;
import java.util.Map;

public class CalendarFrontHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response)  {
    Map<String, String> variables = Collections.emptyMap();
    return new ModelAndView(variables, "calendar.ftl");
  }

}
