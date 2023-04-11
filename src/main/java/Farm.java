package main.java;

import java.util.ArrayList;
import main.java.animals.Animal;
import main.java.animals.Animal.AnimalType;
import main.java.crops.Crop;
import main.java.crops.Crop.CropType;

/**
 * Stores information about the player's farm as well as references to all
 * Crops, Animals, and Items stored in the farm.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class Farm {

  /**
   * The possible farm types to select. Drier farms will receive less rain.
   */
  public static enum FarmType {
    RAINY, TEMPERATE, DRY, DESERT
  }

  /**
   * The name of the farm.
   */
  public String name;
  /**
   * The farm's current money.
   */
  public int money;
  /**
   * The farm's type.
   */
  public FarmType farmType;

  /**
   * A list of crops growing on the farm.
   * 
   * @see Crop
   */
  public ArrayList<Crop> crops = new ArrayList<Crop>();
  /**
   * A list of animals living on the farm.
   * 
   * @see Animal
   */
  public ArrayList<Animal> animals = new ArrayList<Animal>();
  /**
   * An inventory of items held on the farm.
   * 
   * @see main.java.items.Item
   */
  public Inventory items = new Inventory();

  /**
   * The current tidiness level of the farm.
   */
  public int tidiness = 4;
  /**
   * The maximum tidiness level of the farm.
   */
  private int maxTidiness = 4;

  /**
   * Reduces the tidiness of the farm by one and destroys crops if the farm
   * becomes too messy.
   */
  public void reduceTidyness() {
    if (tidiness > 0) {
      tidiness--;
    }

    if (tidiness <= 1) {
      if (crops.size() > 12) {
        int difference = crops.size() - 12;
        while (crops.size() > 12) {
          crops.remove(GameVariables.rand.nextInt(crops.size()));
        }
        GameEnvironment.windowPrint("Your farm has gotten messy and " + difference
            + " crops have been overcrowded and destroyed.");
      }
    }
  }

  /**
   * Reset the farm's tidiness level to max.
   */
  public void resetTidiness() {
    tidiness = maxTidiness;
  }

  /**
   * Returns a description that details the current tidiness of the farm.
   * 
   * @return A description of the farm's tidiness.
   */
  public String tidinessDescription() {

    switch (tidiness) {
      case 4:
        return "Your farm is incredibly tidy.";
      case 3:
        return "Your farm is very tidy.";
      case 2:
        return "Your farm is relatively tidy.";
      case 1:
        return "Your farm is messy. Crop capacity is reduced to 12.";
      case 0:
        return "Your farm is incredibly messy. The farm's crop capacity "
            + "is reduced to 12 and animals will get sick.";
      default:
        return "Error. Tidyness value invalid.";
    }
  }

  /**
   * Construct a new farm with the given name, money and type.
   * 
   * @param name     The name of the constructed farm.
   * @param money    The amount of money of the constructed farm.
   * @param farmType The type of the constructed farm.
   */
  public Farm(String name, int money, FarmType farmType) {
    this.name = name;
    this.money = money;
    this.farmType = farmType;
  }

  /**
   * Adds a new crop to the farm's crops, and removes one of the required seed
   * from the farm's inventory.
   * 
   * @param type The type of crop to plant.
   */
  public void plantCrop(CropType type) {
    if (items.removeItem(type.get().plantingItem)) {
      crops.add(type.getNew());
      GameEnvironment.windowPrint(type.get().name + " planted.");
    } else {
      GameEnvironment.windowPrint(
          type.get().plantingItem.get().name + " needed to plant " + type.get().name + ".");
    }
  }

  /**
   * Returns the number of crops of the given type in the farm's crops list.
   * 
   * @param type The type of crop to check.
   * @return The number of crops of the given type currently growing on the farm.
   */
  public int cropAmount(CropType type) {
    int total = 0;
    for (Crop crop : crops) {
      if (crop.type == type) {
        total++;
      }
    }
    return total;
  }

  /**
   * Shorthand for cropAmount &gt; 0.
   * 
   * @param type The type of crop to check.
   * @return Whether any of the given type of crops are currently growing on the
   *         farm.
   */
  public boolean hasCrop(CropType type) {
    return cropAmount(type) > 0;
  }

  /**
   * Returns whether the farm has any crops of any type currently growing.
   * 
   * @return Whether there are any crops currently growing on the farm.
   */
  public boolean hasAnyCrops() {
    return crops.size() > 0;
  }

  /**
   * Returns the number of animals of the given type in the farm's animals list.
   * 
   * @param type The type of animal to check.
   * @return The number of animals of the given type currently living on the farm.
   */
  public int animalAmount(AnimalType type) {
    int total = 0;
    for (Animal animal : animals) {
      if (animal.type == type) {
        total++;
      }
    }
    return total;
  }

  /**
   * Shorthand for animalAmount &gt; 0.
   * 
   * @param type The type of animal to check.
   * @return Whether any of the given type of animals are currently living on the
   *         farm.
   */
  public boolean hasAnimal(AnimalType type) {
    return animalAmount(type) > 0;
  }

  /**
   * Returns whether the farm has any animals of any type currently on the farm.
   * 
   * @return Whether there are any animals currently living on the farm.
   */
  public boolean hasAnyAnimals() {
    return animals.size() > 0;
  }

  /**
   * Returns a float value based on the farm's type to be used in a random object
   * to determine whether the farm receives any rain.
   * 
   * @return Float chance from 0 - 1 of rain happening each night.
   */
  public float rainChance() {
    switch (farmType) {
      case RAINY:
        return 1;
      case TEMPERATE:
        return 0.3f;
      case DRY:
        return 0.15f;
      case DESERT:
      default:
        return 0.0f;
    }
  }
}
