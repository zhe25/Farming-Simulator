package main.java.items;

import main.java.GameVariables;

/**
 * Items are used to plant crops, feed animals, and can be harvested from crops
 * and animals and sold at the store.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public abstract class Item {

  /**
   * ItemType values correspond to Item subclasses and can be used to retrieve
   * Item instances from the GameVariables registries.
   * 
   * @see GameVariables#itemRegistry
   */
  public static enum ItemType {
    CACTUS_SEED, CARROT_SEED, CUCUMBER_SEED, POTATOE_SEED, WATERMELON_SEED, WHEAT_SEED, FERTILIZER,
    CHICKEN_FEED, PIG_FEED, COW_FEED, PRICKLY_PEAR, CARROT, CUCUMBER, POTATOE, WATERMELON, WHEAT,
    EGG, MILK, BACON;

    /**
     * Returns the instance of the corresponding Item from the item registry.
     * 
     * @see GameVariables#itemRegistry
     * @return The corresponding Item instance from the item registry.
     */
    public Item get() {
      return GameVariables.itemRegistry.get(this);
    }

    /**
     * Returns a new instance of the corresponding Item from the item registry.
     * 
     * @see GameVariables#itemRegistry
     * @return A new instance of the corresponding Item from the item registry.
     */
    public Item getNew() {
      try {
        return GameVariables.itemRegistry.get(this).getClass().newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  /**
   * Categories for sorting items. Each item has one category value.
   * 
   * @see Item#category
   */
  public static enum ItemCategory {
    SEED, FEED, FERTILIZER, PRODUCT
  }

  /**
   * The price of the item.
   */
  public int price;
  /**
   * The name of the item.
   */
  public String name;
  /**
   * The pluralized name of the item.
   */
  public String pluralName;
  /**
   * A description of the item.
   */
  public String description;
  /**
   * The type of the item.
   */
  public ItemType type;
  /**
   * The category of the item.
   */
  public ItemCategory category;

  /**
   * Constructs a new item with the given values.
   * 
   * @param price       The price of the constructed item.
   * @param name        The name of the constructed item.
   * @param pluralName  The pluralized name of the constructed item.
   * @param description A description of the constructed item.
   * @param type        The type of the constructed item.
   * @param category    The category of the constructed item.
   */
  public Item(int price, String name, String pluralName, String description, ItemType type,
      ItemCategory category) {
    this.price = price;
    this.name = name;
    this.pluralName = pluralName;
    this.description = description;
    this.type = type;
    this.category = category;
  }
}
