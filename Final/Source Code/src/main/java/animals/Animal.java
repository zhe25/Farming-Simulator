package main.java.animals;

import main.java.GameEnvironment;
import main.java.GameVariables;
import main.java.items.Item.ItemType;

/**
 * Animals are purchased from the store and are held in the farm. They must be
 * fed regularly and can be harvested from daily to receive product items.
 * 
 * @see main.java.Farm#animals
 * @see main.java.Store
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public abstract class Animal {

  /**
   * AnimalType values correspond to Animal subclasses and can be used to retrieve
   * Animal instances from the GameVariables registries.
   * 
   * @see GameVariables#animalRegistry
   */
  public static enum AnimalType {
    CHICKEN, COW, PIG;

    /**
     * Returns the instance of the corresponding Animal from the animal registry.
     * 
     * @see GameVariables#animalRegistry
     * @return The corresponding Animal instance from the animal registry.
     */
    public Animal get() {
      return GameVariables.animalRegistry.get(this);
    }

    /**
     * Returns a new instance of the corresponding Animal from the animal registry.
     * 
     * @see GameVariables#animalRegistry
     * @return A new instance of the corresponding Animal from the animal registry.
     */
    public Animal getNew() {
      try {
        return GameVariables.animalRegistry.get(this).getClass().newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  /**
   * Denotes the health level of the animal. Animals have a random chance to
   * become sick each day.
   * 
   * @see Animal#sleep()
   */
  public static enum HealthLevel {
    HEALTHY, SICK, DEAD
  }

  /**
   * Denotes the hunger level of the animal. Animals become more hungry each day
   * and must be fed. If animals are starving at the end of the day they will
   * starve in the night.
   * 
   * @see Animal#sleep()
   * @see Animal#eat()
   * @see Animal#die()
   */
  public static enum HungerLevel {
    STARVING, HUNGRY, FULL
  }

  /**
   * Denotes the happiness level of the animal. This is determined by the animal's
   * health and hunger level. If all animals are happy at the end of the day the
   * player will earn a bonus.
   * 
   * @see Animal#happiness()
   * @see Animal#health
   * @see Animal#hunger
   * @see GameEnvironment#endDay()
   */
  public static enum HappinessLevel {
    HAPPY, SAD
  }

  /**
   * The type of the animal.
   */
  public AnimalType type;
  /**
   * The price of the animal.
   */
  public int price;
  /**
   * The name of the animal.
   */
  public String name;
  /**
   * A description of the animal.
   */
  public String description;
  /**
   * The item required to feed the animal.
   * 
   */
  public ItemType feedItem;
  /**
   * The item received when harvesting from the animal.
   * 
   * @see GameEnvironment#harvestAnimals()
   */
  public ItemType harvestItem;

  /**
   * Constructs a new animal with the given values.
   * 
   * @param type        The type of the constructed animal.
   * @param price       The price of the constructed animal.
   * @param name        The name of the constructed animal.
   * @param description The description of the constructed animal.
   * @param feedItem    The feedItem of the constructed animal.
   * @param harvestItem the harvestItem of the constructed animal.
   */
  public Animal(AnimalType type, int price, String name, String description, ItemType feedItem,
      ItemType harvestItem) {
    this.type = type;
    this.price = price;
    this.name = name;
    this.description = description;
    this.feedItem = feedItem;
    this.harvestItem = harvestItem;
  }

  /**
   * Whether the animal has been harvested from today.
   */
  public boolean harvested = true;

  /**
   * The current health level of the animal.
   */
  public HealthLevel health = HealthLevel.HEALTHY;
  /**
   * The current hunger level of the animal.
   */
  public HungerLevel hunger = HungerLevel.FULL;

  /**
   * Returns the happiness level of the animal based on their health and hunger
   * levels.
   * 
   * @return The happiness level of the animal.
   */
  public HappinessLevel happiness() {
    if (health == HealthLevel.HEALTHY && hunger != HungerLevel.STARVING) {
      return HappinessLevel.HAPPY;
    } else {
      return HappinessLevel.SAD;
    }
  }

  /**
   * Returns whether the animal is alive.
   * 
   * @return whether the animal is alive.
   */
  public boolean alive() {
    return health != HealthLevel.DEAD;
  }

  /**
   * Increases the hunger level of the animal to FULL.
   * 
   */
  public void eat() {
    hunger = HungerLevel.FULL;
  }

  /**
   * Called at the end of each day for all animals. Decreases hunger and
   * determines whether the animal becomes sick. If the animal is starving it will
   * die. Resets harvested to false.
   * 
   * @see Animal#die()
   * @see Animal#hunger
   * @see Animal#health
   */
  public void sleep() {
    if (this.alive()) {
      if (hunger == HungerLevel.STARVING) {
        die();
        return;
      }
      hunger = HungerLevel.values()[hunger.ordinal() - 1];

      if (GameVariables.farm.tidiness == 0 && GameVariables.rand.nextFloat() < 0.3) {
        health = HealthLevel.SICK;
      }

      harvested = false;
    }
  }

  /**
   * Sets the animal's health level to dead.
   */
  public void die() {
    health = HealthLevel.DEAD;
  }
}
