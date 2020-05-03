package edu.brown.cs.student.chat.gui.calendar;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class CalendarHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    return new ModelAndView(null, "calendar.ftl");
  }

}
