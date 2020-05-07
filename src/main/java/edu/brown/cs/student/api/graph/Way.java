package edu.brown.cs.student.api.graph;

/**
 * A class representing a Way object.
 */
public class Way {

  /**
   * These are fields for this class.
   *
   * Id - a String representing the unique ID of the way
   *
   * name - a String representing the name of the way
   *
   * type - a String representing the type of the way
   *
   * start - a String representing the ID of the start node of the way
   *
   * end - a String representing the ID of the end node of the way
   */
  private String id;
  private String name;
  private String type;
  private String start;
  private String end;

  /**
   * A constructor for a Way object.
   *
   * @param id    - a String representing the unique ID of the way
   * @param name  - a String representing the name of the way
   * @param type  - a String representing the type of the way
   * @param start - a String representing the ID of the start node of the way
   * @param end   - a String representing the ID of the end node of the way
   */
  public Way(String id, String name, String type, String start, String end) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.start = start;
    this.end = end;
  }

  /**
   * A getter for the start field of a Way object.
   *
   * @return - a String representing the ID of the start node of the way
   */
  public String getStart() {
    return start;
  }

  /**
   * A getter for the end field of a Way object.
   *
   * @return - a String representing the ID of the end node of the way
   */
  public String getEnd() {
    return end;
  }

  /**
   * A getter for the ID field of a Way object.
   *
   * @return - a String representing the Id of the way
   */
  public String getID() {
    return id;
  }

  /**
   * A getter for the name field of a Way object.
   *
   * @return - a String representing the name of the way
   */
  public String getName() {
    return name;
  }

  /**
   * A getter for the type field of a Way object.
   *
   * @return - a String representing the type of the way
   */
  public String getType() {
    return type;
  }

}
