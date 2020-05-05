package edu.brown.cs.student.chat.gui;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Constants {
  public static final Map<String, Integer> ATTRACTION_NAME_TO_CODE = ImmutableMap
      .<String, Integer>builder().put("Boat Tours & Water Sports", 55).put("Fun & Game", 56)
      .put("Nature & Parks", 57).put("Sights & Landmarks", 47).put("Food & Drink", 36)
      .put("Shopping", 26).put("Transportation", 59).put("Museums", 49)
      .put("Outdoor Activities", 61).put("Spas & Wellness", 40).put("Classes & Workshops", 41)
      .put("Tours", 42).put("Nightlife", 20).put("All", 0).build();

  public static final String LIMIT = "30";
  public static final String LANG = "en_US";
  public static final String CURRENCY = "USD";
  public static final String LUNIT = "mi";
}
