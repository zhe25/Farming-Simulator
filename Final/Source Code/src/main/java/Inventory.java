package main.java;

import java.util.HashMap;
import main.java.items.Item.ItemType;

/**
 * Stores a Map of ItemTypes to Integer amounts of each item.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class Inventory {

  /**
   * A map of ItemTypes to an integer amount of them in the inventory.
   */
  private HashMap<ItemType, Integer> items = new HashMap<ItemType, Integer>();

  /**
   * Construct a new Inventory of every ItemType mapped to 0.
   */
  public Inventory() {
    for (ItemType type : ItemType.values()) {
      items.put(type, 0);
    }
  }

  /**
   * Returns the amount of the given ItemType present in the inventory.
   * 
   * @param type The ItemType to check.
   * @return The amount of the given ItemType present in the inventory.
   */
  public int itemAmount(ItemType type) {
    return items.get(type);
  }

  /**
   * Shorthand for itemAmount &gt; 0.
   * 
   * @param type The ItemType to check.
   * @return Whether any of the given ItemType is present in the inventory.
   */
  public boolean hasItem(ItemType type) {
    return itemAmount(type) > 0;
  }

  /**
   * Add one of the given ItemType to the inventory by increasing its value in the
   * map by 1.
   * 
   * @param type The ItemType to add.
   */
  public void addItem(ItemType type) {
    items.put(type, items.get(type) + 1);
  }

  /**
   * Attempts to remove one of the item from the inventory.
   * 
   * @param type The ItemType to remove.
   * @return True if successful, False if none of the item is present.
   */
  public boolean removeItem(ItemType type) {
    if (hasItem(type)) {
      items.put(type, items.get(type) - 1);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns whether the inventory is empty.
   * 
   * @return Whether the inventory has any items of any type.
   */
  public boolean isEmpty() {
    boolean empty = true;
    for (ItemType type : ItemType.values()) {
      if (items.get(type) != 0) {
        empty = false;
        break;
      }
    }
    return empty;
  }
}
