package main.java;

import java.util.HashMap;
import java.util.Random;
import main.java.animals.Animal;
import main.java.animals.Animal.AnimalType;
import main.java.animals.Chicken;
import main.java.animals.Cow;
import main.java.animals.Pig;
import main.java.crops.CactusCrop;
import main.java.crops.CarrotCrop;
import main.java.crops.Crop;
import main.java.crops.Crop.CropType;
import main.java.crops.CucumberCrop;
import main.java.crops.PotatoeCrop;
import main.java.crops.WatermelonCrop;
import main.java.crops.WheatCrop;
import main.java.items.Bacon;
import main.java.items.CactusSeed;
import main.java.items.Carrot;
import main.java.items.CarrotSeed;
import main.java.items.ChickenFeed;
import main.java.items.CowFeed;
import main.java.items.Cucumber;
import main.java.items.CucumberSeed;
import main.java.items.Egg;
import main.java.items.Fertilizer;
import main.java.items.Item;
import main.java.items.Item.ItemType;
import main.java.items.Milk;
import main.java.items.PigFeed;
import main.java.items.Potatoe;
import main.java.items.PotatoeSeed;
import main.java.items.PricklyPear;
import main.java.items.Watermelon;
import main.java.items.WatermelonSeed;
import main.java.items.Wheat;
import main.java.items.WheatSeed;

/**
 * Contains references to objects needed for the game. Makes use of a static
 * initializer to call the registerObjects method before any others.
 * 
 * @see GameVariables#registerObjects()
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class GameVariables {

  /**
   * A static Random function for all classes to use.
   */
  public static Random rand = new Random();

  /**
   * The amount of money the player should start with.
   */
  public static int startingMoney = 30;
  /**
   * The total days the game will run for.
   */
  public static int totalDays = 5;
  /**
   * The day the game is currently on.
   */
  public static int currentDay = 1;
  /**
   * The number of actions remaining for the player to use.
   */
  public static int actions = 2;

  /**
   * Reference to the game's farmer.
   */
  public static Farmer farmer;
  /**
   * Reference to the game's farm.
   */
  public static Farm farm;
  /**
   * Hash Map that maps from a CropType to a Crop object. Used to retrieve new
   * instances of Crops.
   */
  public static HashMap<CropType, Crop> cropRegistry = new HashMap<CropType, Crop>();
  /**
   * Hash Map that maps from an AnimalType to an Animal object. Used to retrieve
   * new instances of Animals.
   */
  public static HashMap<AnimalType, Animal> animalRegistry = new HashMap<AnimalType, Animal>();
  /**
   * Hash Map that maps from an ItemType to an Item object. Used to retrieve new
   * instances of Items.
   */
  public static HashMap<ItemType, Item> itemRegistry = new HashMap<ItemType, Item>();
  /**
   * Reference to the game's store.
   */
  public static Store store;

  static {
    registerObjects();
  }

  /**
   * Registers all Crops, Animals, and Items by adding them to the HashMap
   * registries and creates a new Store instance.
   */
  public static void registerObjects() {
    // Register crops.
    GameVariables.cropRegistry.put(CropType.CACTUS, new CactusCrop());
    GameVariables.cropRegistry.put(CropType.CARROT, new CarrotCrop());
    GameVariables.cropRegistry.put(CropType.CUCUMBER, new CucumberCrop());
    GameVariables.cropRegistry.put(CropType.POTATOE, new PotatoeCrop());
    GameVariables.cropRegistry.put(CropType.WATERMELON, new WatermelonCrop());
    GameVariables.cropRegistry.put(CropType.WHEAT, new WheatCrop());

    // Register animals.
    GameVariables.animalRegistry.put(AnimalType.CHICKEN, new Chicken());
    GameVariables.animalRegistry.put(AnimalType.COW, new Cow());
    GameVariables.animalRegistry.put(AnimalType.PIG, new Pig());

    // Register items.
    GameVariables.itemRegistry.put(ItemType.CACTUS_SEED, new CactusSeed());
    GameVariables.itemRegistry.put(ItemType.CARROT_SEED, new CarrotSeed());
    GameVariables.itemRegistry.put(ItemType.COW_FEED, new CowFeed());
    GameVariables.itemRegistry.put(ItemType.CUCUMBER_SEED, new CucumberSeed());
    GameVariables.itemRegistry.put(ItemType.CHICKEN_FEED, new ChickenFeed());
    GameVariables.itemRegistry.put(ItemType.FERTILIZER, new Fertilizer());
    GameVariables.itemRegistry.put(ItemType.PIG_FEED, new PigFeed());
    GameVariables.itemRegistry.put(ItemType.POTATOE_SEED, new PotatoeSeed());
    GameVariables.itemRegistry.put(ItemType.WATERMELON_SEED, new WatermelonSeed());
    GameVariables.itemRegistry.put(ItemType.WHEAT_SEED, new WheatSeed());
    GameVariables.itemRegistry.put(ItemType.PRICKLY_PEAR, new PricklyPear());
    GameVariables.itemRegistry.put(ItemType.CARROT, new Carrot());
    GameVariables.itemRegistry.put(ItemType.CUCUMBER, new Cucumber());
    GameVariables.itemRegistry.put(ItemType.POTATOE, new Potatoe());
    GameVariables.itemRegistry.put(ItemType.WATERMELON, new Watermelon());
    GameVariables.itemRegistry.put(ItemType.WHEAT, new Wheat());
    GameVariables.itemRegistry.put(ItemType.EGG, new Egg());
    GameVariables.itemRegistry.put(ItemType.MILK, new Milk());
    GameVariables.itemRegistry.put(ItemType.BACON, new Bacon());

    store = new Store();
  }

  /**
   * Calculates the final score based on the farm's money and animals.
   * 
   * @return The final score.
   */
  public static int score() {
    int finalScore = 0;
    finalScore += (farm.money / 10);
    finalScore += farm.animalAmount(AnimalType.CHICKEN) * 5;
    finalScore += farm.animalAmount(AnimalType.PIG) * 10;
    finalScore += farm.animalAmount(AnimalType.COW) * 20;
    return finalScore;
  }
}
