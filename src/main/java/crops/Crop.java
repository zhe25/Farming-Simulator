package main.java.crops;

import main.java.GameEnvironment;
import main.java.GameVariables;
import main.java.items.Item.ItemType;

/**
 * Crops are planted on the farm using seed items. They must be watered
 * regularly and can be harvested when ready to receive product items.
 * 
 * @see main.java.Farm#crops
 * @see main.java.items.Item.ItemCategory#SEED
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public abstract class Crop {

  /**
   * CropType values correspond to Crop subclasses and can be used to retrieve
   * Crop instances from the GameVariables registries.
   * 
   * @see GameVariables#cropRegistry
   */
  public enum CropType {
    CACTUS, CARROT, CUCUMBER, POTATOE, WATERMELON, WHEAT;

    /**
     * Returns the instance of the corresponding Crop from the crop registry.
     * 
     * @see GameVariables#cropRegistry
     * @return The corresponding Crop instance from the crop registry.
     */
    public Crop get() {
      return GameVariables.cropRegistry.get(this);
    }

    /**
     * Returns a new instance of the corresponding Crop from the crop registry.
     * 
     * @see GameVariables#cropRegistry
     * @return A new instance of the corresponding Crop from the crop registry.
     */
    public Crop getNew() {
      try {
        return GameVariables.cropRegistry.get(this).getClass().newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  /**
   * Denotes the health level of the crop. Decreases each day if the crop is
   * dehydrated.
   * 
   * @see Crop#sleep()
   */
  public static enum HealthLevel {
    DEAD, SICK, HEALTHY
  }

  /**
   * The type of the crop.
   */
  public CropType type;
  /**
   * The number of days a crop takes to grow.
   */
  public int growingTime;
  /**
   * The number of days a crop can survive without water.
   */
  public int waterTime;
  /**
   * The item needed to plant the crop.
   * 
   */
  public ItemType plantingItem;
  /**
   * The item needed to tend to the crop.
   * 
   */
  public ItemType tendingItem;
  /**
   * The item received when harvesting the crop.
   * 
   * @see GameEnvironment#harvestCrops()
   */
  public ItemType harvestItem;
  /**
   * The name of the crop.
   */
  public String name;
  /**
   * The pluralized name of the crop.
   */
  public String pluralName;
  /**
   * A description of the crop.
   */
  public String description;

  /**
   * Constructs a new crop with the given values.
   * 
   * @param type         The type of the crop.
   * @param growingTime  The growingTime of the crop.
   * @param waterTime    The waterTime of the crop.
   * @param plantingItem The plantingTime of the crop.
   * @param tendingItem  The tendingItem of the crop.
   * @param harvestItem  The harvestItem of the crop.
   * @param name         The name of the crop.
   * @param pluralName   The pluralName of the crop.
   * @param description  The description of the crop.
   */
  public Crop(CropType type, int growingTime, int waterTime, ItemType plantingItem,
      ItemType tendingItem, ItemType harvestItem, String name, String pluralName,
      String description) {
    this.type = type;
    this.growingTime = growingTime;
    this.waterTime = waterTime;
    this.plantingItem = plantingItem;
    this.tendingItem = tendingItem;
    this.harvestItem = harvestItem;
    this.name = name;
    this.pluralName = pluralName;
    this.description = description;
  }

  /**
   * The health level of the crop.
   */
  public HealthLevel health = HealthLevel.HEALTHY;

  /**
   * The days since the crop was watered.
   */
  public int daysSinceWatered = 0;

  /**
   * The days since the crop was planted.
   */
  public int daysSincePlanted = 0;

  /**
   * Whether the crop is fertilized.
   */
  public boolean fertilized = false;

  /**
   * Resets the days since watered.
   */
  public void water() {
    daysSinceWatered = 0;
  }

  /**
   * Returns the days until a crop can be harvested. Lower bound of 0.
   * 
   * @return The days until a crop can be harvested. Lower bound of 0.
   */
  public int daysUntilHarvestable() {
    int days = growingTime - daysSincePlanted;
    if (days < 0) {
      days = 0;
    }
    return days;
  }

  /**
   * Returns the days until a crop is dehydrated. Lower bound of 0. If a crop is
   * fertilized, the number of days is increased by 1, allowing the crop to be
   * watered less frequently.
   * 
   * @return The days until a crop is dehydrated. Lower bound of 0.
   */
  public int daysUntilDehydrated() {
    int days = waterTime - daysSinceWatered;
    if (fertilized) {
      days++;
    }
    if (days < 0) {
      days = 0;
    }
    return days;
  }

  /**
   * Returns whether a crop is dehydrated. Short hand for daysUntilDehydrated() ==
   * 0.
   * 
   * @see Crop#daysUntilDehydrated()
   * @return Whether a crop is dehydrated.
   */
  public boolean dehydrated() {
    return daysUntilDehydrated() == 0;
  }

  /**
   * Returns whether the crop is alive.
   * 
   * @return Whether the crop is alive.
   */
  public boolean alive() {
    return health != HealthLevel.DEAD;
  }

  /**
   * Sets the crop's health level to dead.
   */
  public void die() {
    health = HealthLevel.DEAD;
  }

  /**
   * Called at the end of each day for all planted crops. Increases
   * daysSinceWatered and daysSincePlanted and decreases health if the crop is
   * dehydrated.
   * 
   * @see Crop#daysSinceWatered
   * @see Crop#daysSincePlanted
   * @see Crop#health
   */
  public void sleep() {
    if (this.alive()) {
      if (dehydrated()) {
        health = HealthLevel.values()[health.ordinal() - 1];
      }
      daysSinceWatered++;
      daysSincePlanted++;
    }
  }

  /**
   * Helper method to get a CropType value from an item based on a crop's
   * plantingItem.
   * 
   * @see main.java.items.Item
   * @see Crop#plantingItem
   * @param item The item to check.
   * @return The CropType from the item based on the crop's planting item.
   */
  public static CropType getCropTypeFromItem(ItemType item) {
    for (CropType crop : CropType.values()) {
      if (crop.get().plantingItem == item) {
        return crop;
      }
    }
    return null;
  }
}
