package main.java;

/**
 * Stores information about the player's farmer.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class Farmer {
  /**
   * The name of the farmer.
   */
  public String name;
  /**
   * The age of the farmer.
   */
  public int age;
  /**
   * The farmer's Gender Type.
   */
  public GenderType gender;

  /**
   * An enum to select between a male or female gender type. In place for a future
   * body type implementation.
   */
  public static enum GenderType {
    MALE, FEMALE
  }

  /**
   * Construct a new farmer with a given name, age and gender type.
   * 
   * @param name   The name of the constructed farmer.
   * @param age    The age of the constructed farmer.
   * @param gender The gender type of the constructed farmer.
   */
  public Farmer(String name, int age, GenderType gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
}
