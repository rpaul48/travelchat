package edu.brown.cs.student.api.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to read data from a CSV file.
 */
public class CSVReader {
  private String valSeparator;
  private ArrayList<String[]> csv;

  /**
   * Constructs a CSV reader that splits lines at commas.
   */
  public CSVReader() {
    this.valSeparator = ",";
  }

  /**
   * Loads a CSV file by storing it in an ArrayList of arrays of Strings.
   *
   * @param filePath The path to the CSV to be loaded.
   * @return ArrayList of arrays of Strings containing the CSV data.
   */
  public ArrayList<String[]> loadCSV(String filePath) {
    ArrayList<String[]> currentCSV = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      String[] parsedLine;

      while ((line = br.readLine()) != null) {
        parsedLine = line.split(this.valSeparator);
        currentCSV.add(parsedLine);
      }

    } catch (FileNotFoundException e) {
      System.err.println("ERROR: CSV file not found at " + filePath);
    } catch (IOException e) {
      System.err.println("ERROR: Failure while reading the file at " + filePath);
    }
    this.csv = currentCSV;
    return currentCSV;
  }

  /**
   * Verifies that the header of the CSV matches an expected format.
   *
   * @param expected Array of Strings representing expected header.
   * @return If the header matches what was expected.
   */
  public boolean verifyHeader(String[] expected) {
    return Arrays.equals(expected, this.csv.get(0));
  }
}
