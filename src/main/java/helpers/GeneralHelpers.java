package main.java.helpers;

/**
 * Contains general helper functions.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class GeneralHelpers {

  /**
   * Attempts to parse a string and returns whether it was successful.
   * 
   * @param value The String to attempt to parse.
   * @return Whether the string was successfully parsed.
   */
  public static boolean tryParseInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Attempts to cast an object to an integer and returns whether it was
   * successful.
   * 
   * @param value The object to attempt to cast.
   * @return Whether the object was successfully casted.
   */
  public static boolean tryCastInt(Object value) {
    try {
      @SuppressWarnings("unused")
      int a = (int) value;
      return true;
    } catch (ClassCastException | NullPointerException e) {
      return false;
    }
  }
}
