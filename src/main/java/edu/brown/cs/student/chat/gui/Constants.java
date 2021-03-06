package edu.brown.cs.student.chat.gui;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Defines constants to be used for the queries.
 */
public class Constants {
  public static final Map<String, String> ATTRACTION_SUBCATEGORY_TO_CODE = ImmutableMap
      .<String, String>builder().put("Boat Tours & Water Sports", "55").put("Fun & Game", "56")
      .put("Nature & Parks", "57").put("Sights & Landmarks", "47").put("Food & Drink", "36")
      .put("Shopping", "26").put("Transportation", "59").put("Museums", "49")
      .put("Outdoor Activities", "61").put("Spas & Wellness", "40").put("Classes & Workshops", "41")
      .put("Tours", "42").put("Nightlife", "20").put("All", "0").build();

  public static final Map<String, String> CUISINE_TYPE_TO_CODE = ImmutableMap
      .<String, String>builder().put("american", "9908").put("barbeque", "10651")
      .put("chinese", "5379").put("italian", "4617").put("indian", "10346").put("japanese", "5473")
      .put("mexican", "5110").put("seafood", "10643").put("thai", "10660").put("any", "all")
      .build();

  public static final Map<String, String> DIETARY_RESTRICTION_TO_CODE = ImmutableMap
      .<String, String>builder().put("vegetarian friendly", "10665").put("vegan options", "10697")
      .put("halal", "10751").put("gluten-free options", "10992").put("none", "all").build();

  public static final Map<String, String> RESTAURANT_PRICE_TO_CODE = ImmutableMap
      .<String, String>builder().put("$", "10953").put("$$-$$$", "10955").put("$$$$", "10954")
      .put("Any", "all").build();

  public static final int LIMIT = 30;
  public static final String LANG = "en_US";
  public static final String CURRENCY = "USD";
  public static final String LUNIT = "mi";
  public static final String SORT = "recommended";
  /*
   * 1 degree is approximately 69 mi; thus, this lat/lon degree offset will give
   * approximately reasonable boundary for corresponding number of miles.
   */
  public static final double LAT_LON_BOUNDARY_OFFSET_1_MILES = 0.01449275362;
  public static final double LAT_LON_BOUNDARY_OFFSET_2_MILES = 0.02898550724;
  public static final double LAT_LON_BOUNDARY_OFFSET_5_MILES = 0.07246376811;
  public static final double LAT_LON_BOUNDARY_OFFSET_10_MILES = 0.1449275362;

  public static final int MIN_LATITUDE = -90;
  public static final int MAX_LATITUDE = 90;
  public static final int MIN_LONGITUDE = -180;
  public static final int MAX_LONGITUDE = 180;

  /*
   * Each integer/double field in Restaurant/Hotel/Attractions' info is
   * initialized with this value.
   */
  public static final int INIT_NUM_VALUE = -1000000000;

  // Used to calculate Haversine distance.
  public static final double EARTH_RADIUS = 6371.0;
}
