package main.java;

import java.util.ArrayList;
import main.java.animals.Animal.AnimalType;
import main.java.items.Item.ItemCategory;
import main.java.items.Item.ItemType;

/**
 * The in game store. Contains a list of items and animals available for
 * purchase, as well as methods for printing lists of purchasable items, buying
 * items.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class Store {

  /**
   * A list of items available for purchase.
   */
  public ArrayList<ItemType> items = new ArrayList<ItemType>();
  /**
   * A list of animals available for purchase.
   */
  public ArrayList<AnimalType> animals = new ArrayList<AnimalType>();

  /**
   * Construct a new store with all available types of items and animals. Except
   * for Items of the Product category as these are harvested from crops and
   * animals.
   */
  public Store() {

    for (ItemType type : ItemType.values()) {
      if (type.get().category != ItemCategory.PRODUCT) {
        items.add(type);
      }
    }

    for (AnimalType type : AnimalType.values()) {
      animals.add(type);
    }
  }

  /**
   * Print a list of items available for purchase, along with the number to input
   * to purchase them, as well as options to sell products, view animals, and
   * leave the store.
   */
  public void printItems() {

    int count = 1;

    System.out.println();
    System.out.println("Store Items:");
    for (ItemCategory category : ItemCategory.values()) {
      if (category == ItemCategory.PRODUCT) {
        continue;
      }
      System.out.println("\n" + category + "S:");
      for (ItemType item : items) {
        if (item.get().category == category) {
          System.out.println(count + ". " + item.get().name + ": $" + item.get().price);
          count++;
        }
      }
    }
    System.out.println();
    System.out.println("-2. Sell Products");
    System.out.println();
    System.out.println("-1. View Animals");
    System.out.println();
    System.out.println("0. Leave");
  }

  /**
   * Purchase an item, reduce the farm's money by the item's price and add it to
   * the farm's inventory based on a number received from player input.
   * 
   * @param number The number received from player input.
   */
  public void buyItem(final int number) {

    int count = 1;
    ItemType type = null;

    for (ItemCategory category : ItemCategory.values()) {
      if (category == ItemCategory.PRODUCT) {
        continue;
      }
      if (type != null) {
        break;
      }
      for (ItemType item : items) {
        if (item.get().category == category) {
          if (count == number) {
            type = item;
            break;
          }
          count++;
        }
      }
    }
    if (type != null) {
      if (type.get().price <= GameVariables.farm.money) {

        GameVariables.farm.items.addItem(type);
        GameVariables.farm.money -= type.get().price;
        System.out.println("You bought a " + type.get().name + " for $" + type.get().price + ".");
      } else {
        System.out.println("You don't have enough money for that.");
      }
    } else {
      System.out.println("Invalid Input.");
    }
  }

  /**
   * Purchase an item, reduce the farm's money by the item's price and add it to
   * the farm's inventory based on a given ItemType.
   * 
   * @param type The type of item to purchase.
   */
  public void buyItemFromType(final ItemType type) {

    if (type != null) {
      if (type.get().price <= GameVariables.farm.money) {

        GameVariables.farm.items.addItem(type);
        GameVariables.farm.money -= type.get().price;
        GameEnvironment
            .windowPrint("You bought a " + type.get().name + " for $" + type.get().price + ".");
      } else {
        GameEnvironment.windowPrint("You don't have enough money for that.");
      }
    } else {
      GameEnvironment.windowPrint("Invalid Input.");
    }

    GameEnvironment.refreshStorePane();
  }

  /**
   * Print a list of animals available for purchase, along with the number to
   * input to purchase them, as well as the option to return to the items list.
   */
  public void printAnimals() {

    System.out.println();
    System.out.println("Animals:");
    for (AnimalType type : animals) {
      System.out.println((type.ordinal() + 1) + ". " + type.get().name + ": $" + type.get().price);
    }
    System.out.println();
    System.out.println("0. Leave");
  }

  /**
   * Purchase an animal, reduce the farm's money by the animal's price, and add it
   * to the farm's animals based on a number received from player input.
   * 
   * @param number The number received from player input.
   */
  public void buyAnimal(final int number) {

    AnimalType type = null;
    try {
      type = AnimalType.values()[number - 1];
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Invalid Input.");
      return;
    }

    if (type.get().price <= GameVariables.farm.money) {

      GameVariables.farm.animals.add(type.getNew());
      GameVariables.farm.money -= type.get().price;
      System.out.println("You bought a " + type.get().name + " for $" + type.get().price + ".");
    } else {
      System.out.println("You don't have enough money for that.");
    }
  }

  /**
   * Purchase an animal, reduce the farm's money by the animal's price and add it
   * to the farm's animal based on a given AnimalType.
   * 
   * @param type The type of animal to purchase.
   */
  public void buyAnimalFromType(final AnimalType type) {

    if (type.get().price <= GameVariables.farm.money) {

      GameVariables.farm.animals.add(type.getNew());
      GameVariables.farm.money -= type.get().price;
      GameEnvironment
          .windowPrint("You bought a " + type.get().name + " for $" + type.get().price + ".");
    } else {
      GameEnvironment.windowPrint("You don't have enough money for that.");
    }

    GameEnvironment.refreshStorePane();
  }
}
