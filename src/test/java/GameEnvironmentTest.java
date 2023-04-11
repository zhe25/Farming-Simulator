/**
 * Tests all methods of GameEnvironment.
 */

package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import main.java.Farm;
import main.java.Farm.FarmType;
import main.java.Farmer;
import main.java.GameEnvironment;
import main.java.GameVariables;
import main.java.animals.Animal;
import main.java.animals.Animal.AnimalType;
import main.java.animals.Animal.HungerLevel;
import main.java.crops.Crop;
import main.java.crops.Crop.CropType;
import main.java.crops.WheatCrop;
import main.java.items.Item.ItemCategory;
import main.java.items.Item.ItemType;
import main.swing.GameWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


/**
 * This is the game's main environment class. It contains all the required
 * methods for the gameplay to progress.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
@TestMethodOrder(OrderAnnotation.class)
class GameEnvironmentTest {

  @BeforeEach
  void init() {
    GameEnvironment.window = null;
    GameVariables.farm = null;
    GameVariables.farmer = null;
    GameVariables.currentDay = 1;
    GameVariables.actions = 2;
    GameVariables.totalDays = 5;
  }

  @Test
  @Order(1)
  void printTest() {
    GameEnvironment.print();
    GameEnvironment.print(true);
    GameEnvironment.print(null);
    GameEnvironment.print(100);
    GameEnvironment.print(-1);
    GameEnvironment.print("Test");
    GameEnvironment.print(Integer.MAX_VALUE);
  }

  @Test
  @Order(2)
  void windowPrintTest() {
    GameEnvironment.windowPrint("Test");
    new GameWindow();
    GameEnvironment.window.getOutputArea().setText("");
    GameEnvironment.windowPrint("Test");
    assertEquals("\nTest", GameEnvironment.window.getOutputArea().getText());
    GameEnvironment.windowPrint("Test2");
    assertEquals("\nTest\nTest2", GameEnvironment.window.getOutputArea().getText());
  }

  @Test
  @Order(3)
  void testResetTest() {
    assertEquals(null, GameEnvironment.window);
    assertEquals(null, GameVariables.farmer);
    assertEquals(null, GameVariables.farm);
    assertEquals(1, GameVariables.currentDay);
    assertEquals(2, GameVariables.actions);
    assertEquals(5, GameVariables.totalDays);
  }

  @Test
  void initializeTest() {
    new GameWindow();
    assertEquals(2, JLayeredPane.getLayer(GameEnvironment.window.getWelcomePane()));
    for (Component c : GameEnvironment.window.getLayeredPane().getComponents()) {
      if (c != GameEnvironment.window.getWelcomePane() && c.getName() != "Intercept") {
        assertEquals(0, GameEnvironment.window.getLayeredPane().getLayer(c));
      }
    }
  }

  @Test
  void refreshInfoPanelTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.refreshInfoPanel();

    assertEquals(String.valueOf(GameVariables.farm.name),
        GameEnvironment.window.getStatusLabel().getText());
    assertEquals(String.valueOf(GameVariables.farm.money),
        GameEnvironment.window.getMoneyOutputLabel().getText());
    assertEquals(String.valueOf(GameVariables.farmer.name),
        GameEnvironment.window.getNameOutputLabel().getText());
    assertEquals(String.valueOf(GameVariables.farmer.age),
        GameEnvironment.window.getAgeOutputLabel().getText());
    assertEquals(String.valueOf(GameVariables.farm.farmType),
        GameEnvironment.window.getFarmTypeOutputLabel().getText());
    assertEquals(String.valueOf(GameVariables.farm.tidinessDescription()),
        GameEnvironment.window.getTidinessOutputText().getText());
    assertEquals(String.valueOf(GameVariables.currentDay),
        GameEnvironment.window.getDayOutputLabel().getText());
    assertEquals(String.valueOf(GameVariables.actions + " Actions Remaining:"),
        GameEnvironment.window.getActionsRemainingLabel().getText());

    assertEquals(true, GameEnvironment.window.getFeedAnimalsButton().isEnabled());

    GameVariables.actions = 0;
    GameEnvironment.refreshInfoPanel();

    assertEquals(false, GameEnvironment.window.getFeedAnimalsButton().isEnabled());
  }

  @Test
  void refreshStorePaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.refreshStorePane();

    assertEquals("Animals:",
        ((JLabel) GameEnvironment.window.getStorePaneInfoPane().getComponent(0)).getText());
    assertEquals("Price:",
        ((JLabel) GameEnvironment.window.getStorePaneInfoPane().getComponent(1)).getText());

    if (GameVariables.store.animals.size() > 0) {
      assertEquals(GameVariables.store.animals.get(0).get().name,
          ((JLabel) GameEnvironment.window.getStorePaneInfoPane().getComponent(2)).getText());
    }
  }

  @Test
  void refreshFeedAnimalsPaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;
    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    GameEnvironment.refreshFeedAnimalsPane();

    assertEquals("Animal:",
        ((JLabel) GameEnvironment.window.getFeedAnimalsPaneInfoPane().getComponent(0)).getText());
    assertEquals("Amount:",
        ((JLabel) GameEnvironment.window.getFeedAnimalsPaneInfoPane().getComponent(1)).getText());
    assertEquals("Worst Hunger:",
        ((JLabel) GameEnvironment.window.getFeedAnimalsPaneInfoPane().getComponent(2)).getText());

    for (AnimalType type : AnimalType.values()) {
      if (GameVariables.farm.hasAnimal(type)) {
        assertEquals(type.get().name + "s",
            ((JLabel) GameEnvironment.window.getFeedAnimalsPaneInfoPane().getComponent(3))
                .getText());
        break;
      }
    }
  }

  @Test
  void feedAnimalFromButtonTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;

    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.CHICKEN_FEED));

    GameEnvironment.feedAnimalFromButton(AnimalType.CHICKEN);

    assertEquals(HungerLevel.FULL, GameVariables.farm.animals.get(0).hunger);
    assertEquals(false, GameVariables.farm.items.hasItem(ItemType.CHICKEN_FEED));
  }

  @Test
  void refreshTendCropsPaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.items.addItem(ItemType.FERTILIZER);

    GameEnvironment.refreshTendCropsPane();

    assertEquals("Crops:",
        ((JLabel) GameEnvironment.window.getTendCropsPaneInfoPane().getComponent(0)).getText());
    assertEquals("Amount:",
        ((JLabel) GameEnvironment.window.getTendCropsPaneInfoPane().getComponent(1)).getText());
    assertEquals("Need Fertilizing:",
        ((JLabel) GameEnvironment.window.getTendCropsPaneInfoPane().getComponent(2)).getText());

    for (CropType type : CropType.values()) {
      if (GameVariables.farm.hasCrop(type)) {
        assertEquals(type.get().pluralName,
            ((JLabel) GameEnvironment.window.getTendCropsPaneInfoPane().getComponent(3)).getText());
        break;
      }
    }
  }

  @Test
  void tendCropFromButtonTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.tendCropFromButton(CropType.CACTUS, ItemType.FERTILIZER);

    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.items.addItem(ItemType.FERTILIZER);

    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);
    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.FERTILIZER));

    GameEnvironment.tendCropFromButton(CropType.CACTUS, ItemType.BACON);

    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);
    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.FERTILIZER));

    GameEnvironment.tendCropFromButton(CropType.CACTUS, ItemType.FERTILIZER);

    assertEquals(true, GameVariables.farm.crops.get(0).fertilized);
    assertEquals(false, GameVariables.farm.items.hasItem(ItemType.FERTILIZER));
  }

  @Test
  void tendCropFromButtonTestWater() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.items.addItem(ItemType.FERTILIZER);
    GameVariables.farm.crops.get(0).daysSinceWatered = 1;

    assertEquals(1, GameVariables.farm.crops.get(0).daysSinceWatered);
    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);
    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.FERTILIZER));

    GameEnvironment.tendCropFromButton(CropType.CACTUS, null);

    assertEquals(0, GameVariables.farm.crops.get(0).daysSinceWatered);
    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);
    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.FERTILIZER));
  }

  @Test
  void refreshAnimalPaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());

    GameEnvironment.refreshAnimalPane();

    assertEquals("Animals:",
        ((JLabel) GameEnvironment.window.getAnimalPaneInfoPane().getComponent(0)).getText());

    for (AnimalType type : AnimalType.values()) {
      if (GameVariables.farm.hasAnimal(type)) {
        assertEquals(type.get().name + "s:",
            ((JLabel) GameEnvironment.window.getAnimalPaneInfoPane().getComponent(1)).getText());
        break;
      }
    }
    Animal animal = GameVariables.farm.animals.get(0);
    assertEquals(
        animal.name + " - Hunger level: " + animal.hunger + ", Happiness level: "
            + animal.happiness() + ", Health: " + animal.health,
        ((JLabel) GameEnvironment.window.getAnimalPaneInfoPane().getComponent(2)).getText());
  }

  @Test
  void refreshCropPaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());

    GameEnvironment.refreshCropPane();

    assertEquals("Crops:",
        ((JLabel) GameEnvironment.window.getCropPaneInfoPane().getComponent(0)).getText());

    for (CropType type : CropType.values()) {
      if (GameVariables.farm.hasCrop(type)) {
        assertEquals(type.get().pluralName + ":",
            ((JLabel) GameEnvironment.window.getCropPaneInfoPane().getComponent(1)).getText());
        break;
      }
    }
    Crop crop = GameVariables.farm.crops.get(0);
    assertEquals(
        crop.name + " - Days until dehydrated: " + crop.daysUntilDehydrated()
            + ", Days until harvestable: " + crop.daysUntilHarvestable() + ", Health: "
            + crop.health + ", Fertilized: " + crop.fertilized,
        ((JLabel) GameEnvironment.window.getCropPaneInfoPane().getComponent(2)).getText());
  }

  @Test
  void refreshInventoryPaneTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.items.addItem(ItemType.WHEAT_SEED);

    GameEnvironment.refreshInventoryPane();

    assertEquals("Inventory",
        ((JLabel) GameEnvironment.window.getInventoryPaneInfoPane().getComponent(0)).getText());
    assertEquals("Amount:",
        ((JLabel) GameEnvironment.window.getInventoryPaneInfoPane().getComponent(1)).getText());

    for (ItemType type : ItemType.values()) {
      if (GameVariables.farm.items.hasItem(type)) {
        assertEquals(type.get().name,
            ((JLabel) GameEnvironment.window.getInventoryPaneInfoPane().getComponent(2)).getText());
        assertEquals(String.valueOf(GameVariables.farm.items.itemAmount(type)),
            ((JLabel) GameEnvironment.window.getInventoryPaneInfoPane().getComponent(3)).getText());
        break;
      }
    }
  }

  @Test
  void refreshPlantCropsPane() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.items.addItem(ItemType.WHEAT_SEED);

    GameEnvironment.refreshPlantCropsPane();

    assertEquals("Crops:",
        ((JLabel) GameEnvironment.window.getPlantCropsPaneInfoPane().getComponent(0)).getText());
    assertEquals("Amount:",
        ((JLabel) GameEnvironment.window.getPlantCropsPaneInfoPane().getComponent(1)).getText());

    for (ItemType type : ItemType.values()) {
      if (type.get().category == ItemCategory.SEED) {
        if (GameVariables.farm.items.hasItem(type)) {
          assertEquals(type.get().pluralName,
              ((JLabel) GameEnvironment.window.getPlantCropsPaneInfoPane().getComponent(2))
                  .getText());
          assertEquals(String.valueOf(GameVariables.farm.items.itemAmount(type)),
              ((JLabel) GameEnvironment.window.getPlantCropsPaneInfoPane().getComponent(3))
                  .getText());
          break;
        }
      }
    }
  }

  @Test
  void plantCropFromButtonTest() {
    new GameWindow();
    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.items.addItem(ItemType.WHEAT_SEED);

    assertEquals(true, GameVariables.farm.items.hasItem(ItemType.WHEAT_SEED));
    assertEquals(0, GameVariables.farm.crops.size());

    GameEnvironment.plantCropFromButton(ItemType.WHEAT_SEED);

    assertEquals(WheatCrop.class, GameVariables.farm.crops.get(0).getClass());
    assertEquals(false, GameVariables.farm.items.hasItem(ItemType.WHEAT_SEED));
  }

  void setupValues() {
    GameEnvironment.window.getDaysSpinner().setValue(5);
    GameEnvironment.window.getFarmerNameText().setText("TestFarmer");
    GameEnvironment.window.getAgeSpinner().setValue(18);
    GameEnvironment.window.getFarmTypeSlider().setValue(0);
    GameEnvironment.window.getFarmNameText().setText("TestFarm");
  }

  @Test
  void setupFromWindowTestValid() {
    new GameWindow();
    setupValues();

    GameEnvironment.setupFromWindow();

    assertEquals("TestFarm", GameVariables.farm.name);
    assertEquals(FarmType.RAINY, GameVariables.farm.farmType);
    assertEquals("TestFarmer", GameVariables.farmer.name);
    assertEquals(18, GameVariables.farmer.age);
    assertEquals(5, GameVariables.totalDays);
  }

  @Test
  void setupFromWindowTestValidMin() {
    new GameWindow();
    setupValues();

    GameEnvironment.window.getDaysSpinner().setValue(5);
    GameEnvironment.window.getFarmerNameText().setText("Min");
    GameEnvironment.window.getAgeSpinner().setValue(18);
    GameEnvironment.window.getFarmTypeSlider().setValue(0);
    GameEnvironment.window.getFarmNameText().setText("Min");

    GameEnvironment.setupFromWindow();

    assertEquals("Min", GameVariables.farm.name);
    assertEquals(FarmType.RAINY, GameVariables.farm.farmType);
    assertEquals("Min", GameVariables.farmer.name);
    assertEquals(18, GameVariables.farmer.age);
    assertEquals(5, GameVariables.totalDays);
  }

  @Test
  void setupFromWindowTestValidMax() {
    new GameWindow();
    setupValues();

    GameEnvironment.window.getDaysSpinner().setValue(10);
    GameEnvironment.window.getFarmerNameText().setText("TestFarmerTestF");
    GameEnvironment.window.getAgeSpinner().setValue(80);
    GameEnvironment.window.getFarmTypeSlider().setValue(3);
    GameEnvironment.window.getFarmNameText().setText("TestFarmTestFar");

    GameEnvironment.setupFromWindow();

    assertEquals("TestFarmTestFar", GameVariables.farm.name);
    assertEquals(FarmType.DESERT, GameVariables.farm.farmType);
    assertEquals("TestFarmerTestF", GameVariables.farmer.name);
    assertEquals(80, GameVariables.farmer.age);
    assertEquals(10, GameVariables.totalDays);
  }

  @Test
  void setupFromWindowTestInvalidOver() {
    new GameWindow();

    setupValues();
    GameEnvironment.window.getDaysSpinner().setValue(11);
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getFarmerNameText().setText("TestFarmerTestFa");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getAgeSpinner().setValue(81);
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getFarmNameText().setText("TestFarmTestFarm");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);
  }

  @Test
  void setupFromWindowTestInvalidUnder() {
    new GameWindow();

    setupValues();
    GameEnvironment.window.getDaysSpinner().setValue(4);
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getFarmerNameText().setText("Mi");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getAgeSpinner().setValue(17);
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getFarmNameText().setText("Mi");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);
  }

  @Test
  void setupFromWindowTestInvalidFarmerName() {
    new GameWindow();

    setupValues();
    GameEnvironment.window.getFarmerNameText().setText("Farmer1");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);

    setupValues();
    GameEnvironment.window.getFarmerNameText().setText("Farmer_");
    GameEnvironment.setupFromWindow();

    assertEquals(null, GameVariables.farm);
    assertEquals(null, GameVariables.farmer);
  }

  @Test
  void setupGameTestValid() {
    new GameWindow();

    String input = "5\n" + "TestFarmer\n" + "18\n" + "1\n" + "TestFarm";
    GameEnvironment.scanner = new Scanner(input);
    GameEnvironment.setupGame();

    assertEquals("TestFarm", GameVariables.farm.name);
    assertEquals(FarmType.RAINY, GameVariables.farm.farmType);
    assertEquals("TestFarmer", GameVariables.farmer.name);
    assertEquals(18, GameVariables.farmer.age);
    assertEquals(5, GameVariables.totalDays);
  }

  @Test
  void setupGameTestValidMin() {
    new GameWindow();

    String input = "5\n" + "Min\n" + "18\n" + "1\n" + "Min";
    GameEnvironment.scanner = new Scanner(input);
    GameEnvironment.setupGame();

    assertEquals("Min", GameVariables.farm.name);
    assertEquals(FarmType.RAINY, GameVariables.farm.farmType);
    assertEquals("Min", GameVariables.farmer.name);
    assertEquals(18, GameVariables.farmer.age);
    assertEquals(5, GameVariables.totalDays);
  }

  @Test
  void setupGameTestValidMax() {
    new GameWindow();

    String input = "10\n" + "TestFarmerTestF\n" + "80\n" + "4\n" + "TestFarmTestFar";
    GameEnvironment.scanner = new Scanner(input);
    GameEnvironment.setupGame();

    assertEquals("TestFarmTestFar", GameVariables.farm.name);
    assertEquals(FarmType.DESERT, GameVariables.farm.farmType);
    assertEquals("TestFarmerTestF", GameVariables.farmer.name);
    assertEquals(80, GameVariables.farmer.age);
    assertEquals(10, GameVariables.totalDays);
  }

  @Test
  void setupGameTestValidMultipleInput() {
    new GameWindow();

    String input = "11\n" + "4\n" + "10\n" + "\n" + "T\n" + "TestFarmerTestFarmer\n"
        + "TestFarmer1\n" + "TestFarmer\n" + "17\n" + "81\n" + "\n" + "  \n" + " 1 8 \n" + " 18\n"
        + "18\n" + "0\n" + "5\n" + "10\n" + "1\n" + "\n" + "T\n" + "TestFarmTestFarmTestFarm\n"
        + "TestFarm";
    GameEnvironment.scanner = new Scanner(input);
    GameEnvironment.setupGame();

    assertEquals("TestFarm", GameVariables.farm.name);
    assertEquals(FarmType.RAINY, GameVariables.farm.farmType);
    assertEquals("TestFarmer", GameVariables.farmer.name);
    assertEquals(18, GameVariables.farmer.age);
    assertEquals(10, GameVariables.totalDays);
  }

  @Test
  void feedAnimalsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;
    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    String input = "1\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.feedAnimals();

    assertEquals(Animal.HungerLevel.FULL, GameVariables.farm.animals.get(0).hunger);
    assertEquals(1, GameVariables.actions);
  }

  @Test
  void feedAnimalsTestNoFeed() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;

    GameEnvironment.feedAnimals();

    assertEquals(Animal.HungerLevel.HUNGRY, GameVariables.farm.animals.get(0).hunger);
    assertEquals(2, GameVariables.actions);
  }

  @Test
  void feedAnimalsTestMultipleInput() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;
    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    String input = "2\n" + "g\n" + "1";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.feedAnimals();

    assertEquals(Animal.HungerLevel.FULL, GameVariables.farm.animals.get(0).hunger);
    assertEquals(1, GameVariables.actions);
  }

  @Test
  void feedAnimalsTestExit() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.HUNGRY;
    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    String input = "0\n" + "1";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.feedAnimals();

    assertEquals(Animal.HungerLevel.HUNGRY, GameVariables.farm.animals.get(0).hunger);
    assertEquals(2, GameVariables.actions);
  }

  @Test
  void feedAnimalsTestNoAnimals() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.feedAnimals();
    assertEquals(2, GameVariables.actions);
  }

  @Test
  void feedAnimalsTestMultipleAnimals() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.add(AnimalType.PIG.getNew());
    GameVariables.farm.animals.get(1).hunger = Animal.HungerLevel.HUNGRY;
    GameVariables.farm.items.addItem(ItemType.PIG_FEED);

    String input = "1\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.feedAnimals();

    assertEquals(Animal.HungerLevel.FULL, GameVariables.farm.animals.get(1).hunger);
    assertEquals(1, GameVariables.actions);
  }

  @Test
  void tendFarmTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.reduceTidyness();

    assertEquals(3, GameVariables.farm.tidiness);
    assertEquals(2, GameVariables.actions);

    GameEnvironment.tendFarm();

    assertEquals(4, GameVariables.farm.tidiness);
    assertEquals(1, GameVariables.actions);

    GameEnvironment.tendFarm();

    assertEquals(4, GameVariables.farm.tidiness);
    assertEquals(1, GameVariables.actions);

    GameVariables.farm.reduceTidyness();

    GameEnvironment.tendFarm();

    assertEquals(4, GameVariables.farm.tidiness);
    assertEquals(0, GameVariables.actions);

    GameVariables.farm.reduceTidyness();

    GameEnvironment.tendFarm();

    assertEquals(3, GameVariables.farm.tidiness);
    assertEquals(0, GameVariables.actions);
  }

  @Test
  void harvestCropsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.harvestCrops();

    GameVariables.farm.crops.add(CropType.WHEAT.getNew());
    GameVariables.farm.crops.add(CropType.WHEAT.getNew());
    GameVariables.farm.crops.add(CropType.WHEAT.getNew());
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.crops.add(CropType.CARROT.getNew());

    for (Crop crop : GameVariables.farm.crops) {
      crop.daysSincePlanted = crop.growingTime;
    }

    GameVariables.farm.crops.add(CropType.WATERMELON.getNew());
    GameVariables.farm.crops.add(CropType.POTATOE.getNew());

    GameEnvironment.harvestCrops();

    assertEquals(2, GameVariables.farm.crops.size());
    assertEquals(3, GameVariables.farm.items.itemAmount(ItemType.WHEAT));
    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.PRICKLY_PEAR));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CARROT));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.WATERMELON));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.POTATOE));

    GameEnvironment.harvestCrops();

    assertEquals(2, GameVariables.farm.crops.size());
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.WATERMELON));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.POTATOE));

    for (Crop crop : GameVariables.farm.crops) {
      crop.daysSincePlanted = crop.growingTime;
    }

    GameEnvironment.harvestCrops();

    assertEquals(0, GameVariables.farm.crops.size());
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.WATERMELON));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.POTATOE));

    GameVariables.farm.crops.add(CropType.WATERMELON.getNew());
    GameVariables.farm.crops.add(CropType.POTATOE.getNew());

    for (Crop crop : GameVariables.farm.crops) {
      crop.daysSincePlanted = crop.growingTime;
    }

    GameEnvironment.harvestCrops();

    assertEquals(2, GameVariables.farm.crops.size());
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.WATERMELON));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.POTATOE));
  }

  @Test
  void harvestAnimalsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.harvestAnimals();

    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.add(AnimalType.PIG.getNew());

    for (Animal animal : GameVariables.farm.animals) {
      animal.harvested = false;
    }

    GameVariables.farm.animals.add(AnimalType.PIG.getNew());
    GameVariables.farm.animals.add(AnimalType.COW.getNew());

    GameEnvironment.harvestAnimals();

    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.BACON));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.MILK));

    GameEnvironment.harvestAnimals();

    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.BACON));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.MILK));

    for (Animal animal : GameVariables.farm.animals) {
      animal.harvested = false;
    }

    GameEnvironment.harvestAnimals();

    assertEquals(4, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(3, GameVariables.farm.items.itemAmount(ItemType.BACON));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.MILK));

    for (Animal animal : GameVariables.farm.animals) {
      animal.harvested = false;
    }

    GameEnvironment.harvestAnimals();

    assertEquals(4, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(3, GameVariables.farm.items.itemAmount(ItemType.BACON));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.MILK));
  }

  @Test
  void playWithAnimalsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.playWithAnimals();

    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());
    GameVariables.farm.animals.add(AnimalType.PIG.getNew());

    GameVariables.farm.animals.get(0).health = Animal.HealthLevel.SICK;

    assertEquals(Animal.HealthLevel.SICK, GameVariables.farm.animals.get(0).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(1).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(2).health);

    GameEnvironment.playWithAnimals();

    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(0).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(1).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(2).health);

    GameVariables.farm.animals.get(0).health = Animal.HealthLevel.SICK;
    GameVariables.farm.animals.get(1).health = Animal.HealthLevel.SICK;
    GameVariables.farm.animals.get(2).health = Animal.HealthLevel.SICK;

    GameEnvironment.playWithAnimals();

    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(0).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(1).health);
    assertEquals(Animal.HealthLevel.HEALTHY, GameVariables.farm.animals.get(2).health);

    GameVariables.farm.animals.get(0).health = Animal.HealthLevel.SICK;
    GameVariables.farm.animals.get(1).health = Animal.HealthLevel.SICK;
    GameVariables.farm.animals.get(2).health = Animal.HealthLevel.SICK;

    GameEnvironment.playWithAnimals();

    assertEquals(Animal.HealthLevel.SICK, GameVariables.farm.animals.get(0).health);
    assertEquals(Animal.HealthLevel.SICK, GameVariables.farm.animals.get(1).health);
    assertEquals(Animal.HealthLevel.SICK, GameVariables.farm.animals.get(2).health);
  }

  @Test
  void tendCropsTestValid() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.tendCrops();

    GameVariables.farm.crops.add(CropType.WHEAT.getNew());
    GameVariables.farm.crops.add(CropType.WHEAT.getNew());
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.crops.add(CropType.WATERMELON.getNew());

    for (Crop crop : GameVariables.farm.crops) {
      crop.daysSinceWatered = 2;
    }

    assertEquals(2, GameVariables.farm.crops.get(2).daysSinceWatered);
    assertEquals(2, GameVariables.actions);

    String input = "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.tendCrops();

    assertEquals(2, GameVariables.farm.crops.get(2).daysSinceWatered);
    assertEquals(2, GameVariables.actions);

    input = "1\n" + "g\n" + "0";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.tendCrops();

    assertEquals(2, GameVariables.farm.crops.get(2).daysSinceWatered);
    assertEquals(2, GameVariables.actions);

    input = "g\n" + "1\n" + "1\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.tendCrops();

    assertEquals(0, GameVariables.farm.crops.get(2).daysSinceWatered);
    assertEquals(1, GameVariables.actions);

    input = "2\n" + "1\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.tendCrops();

    assertEquals(0, GameVariables.farm.crops.get(4).daysSinceWatered);
    assertEquals(0, GameVariables.actions);

    GameEnvironment.tendCrops();

    assertEquals(2, GameVariables.farm.crops.get(0).daysSinceWatered);
    assertEquals(0, GameVariables.actions);

    assertEquals(false, GameVariables.farm.crops.get(2).fertilized);
    GameVariables.farm.items.addItem(ItemType.FERTILIZER);
    GameVariables.actions = 2;

    input = "1\n" + "2";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.tendCrops();

    assertEquals(true, GameVariables.farm.crops.get(2).fertilized);
    assertEquals(1, GameVariables.actions);
  }

  @Test
  void plantCropsTestValid() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.plantCrops();

    GameVariables.farm.items.addItem(ItemType.CARROT_SEED);
    GameVariables.farm.items.addItem(ItemType.CARROT_SEED);
    GameVariables.farm.items.addItem(ItemType.CACTUS_SEED);

    assertEquals(0, GameVariables.farm.crops.size());
    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.CARROT_SEED));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));

    String input = "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.plantCrops();

    assertEquals(0, GameVariables.farm.crops.size());
    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.CARROT_SEED));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));

    input = "2\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.plantCrops();

    assertEquals(1, GameVariables.farm.crops.size());
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CARROT_SEED));
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));

    input = "g\n" + "-3\n" + "\n" + "1\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.plantCrops();

    assertEquals(2, GameVariables.farm.crops.size());
    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CARROT_SEED));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));

    input = "1\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.plantCrops();

    assertEquals(3, GameVariables.farm.crops.size());
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CARROT_SEED));
    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
  }

  @Test
  void visitStoreTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(30, GameVariables.farm.money);

    String input = "g\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(30, GameVariables.farm.money);

    input = "1\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(0, GameVariables.farm.animals.size());
    assertEquals(29, GameVariables.farm.money);

    input = "-1\n" + "1\n" + "0\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(1, GameVariables.farm.animals.size());
    assertEquals(19, GameVariables.farm.money);

    GameVariables.farm.items.addItem(ItemType.EGG);
    GameVariables.farm.items.addItem(ItemType.EGG);

    assertEquals(2, GameVariables.farm.items.itemAmount(ItemType.EGG));

    input = "-2\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(29, GameVariables.farm.money);

    input = "-2\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(29, GameVariables.farm.money);

    GameVariables.farm.items.addItem(ItemType.EGG);

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.EGG));

    input = "-2\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.EGG));
    assertEquals(34, GameVariables.farm.money);

    GameVariables.farm.money = 0;

    input = "1\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(0, GameVariables.farm.money);

    input = "-1\n" + "1\n" + "0\n" + "0\n";
    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.visitStore();

    assertEquals(1, GameVariables.farm.animals.size());
    assertEquals(0, GameVariables.farm.money);
  }

  @Test
  void checkInventoryTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream out = System.out;
    System.setOut(new PrintStream(tempOut));

    GameEnvironment.checkInventory();

    assertEquals("Inventory:\r\n" + GameVariables.farm.items.itemAmount(ItemType.CHICKEN_FEED)
        + " X " + ItemType.CHICKEN_FEED.get().name + "\r\n", tempOut.toString());

    System.setOut(out);
  }

  @Test
  void checkInventoryTestPlural() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);
    GameVariables.farm.items.addItem(ItemType.CHICKEN_FEED);

    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream out = System.out;
    System.setOut(new PrintStream(tempOut));

    GameEnvironment.checkInventory();

    assertEquals("Inventory:\r\n" + GameVariables.farm.items.itemAmount(ItemType.CHICKEN_FEED)
        + " X " + ItemType.CHICKEN_FEED.get().pluralName + "\r\n", tempOut.toString());

    System.setOut(out);
  }

  @Test
  void checkFarmTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream out = System.out;
    System.setOut(new PrintStream(tempOut));

    GameEnvironment.checkFarm();

    assertEquals(GameVariables.farm.name + ":\r\n" + "Money: $" + GameVariables.farm.money + "\r\n"
        + "Farmer Name: " + GameVariables.farmer.name + "\r\n" + "Farmer Age: "
        + GameVariables.farmer.age + "\r\n" + "Current Day: " + GameVariables.currentDay + "\r\n"
        + "Farm Type: " + GameVariables.farm.farmType.toString() + "\r\n" + ""
        + GameVariables.farm.tidinessDescription() + "\r\n" + "", tempOut.toString());

    System.setOut(out);
  }

  @Test
  void checkAnimalsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());

    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream out = System.out;
    System.setOut(new PrintStream(tempOut));

    GameEnvironment.checkAnimals();

    assertEquals(
        "Animals:\r\n" + "\n" + "Chickens:\r\n"
            + "Chicken - Hunger level: FULL, Happiness level: HAPPY, Health: HEALTHY\r\n" + "",
        tempOut.toString());

    System.setOut(out);
  }

  @Test
  void checkCropsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.crops.add(CropType.CACTUS.getNew());

    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream out = System.out;
    System.setOut(new PrintStream(tempOut));

    GameEnvironment.checkCrops();

    assertEquals("Crops:\r\n" + "\n" + "Cacti:\r\n"
        + "Cactus - Days until dehydrated: 1, Days until harvestable: 1, "
        + "Health: HEALTHY, Fertilized: false\r\n"
        + "", tempOut.toString());

    System.setOut(out);
  }

  @Test
  void endDayTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.farm.crops.add(CropType.CACTUS.getNew());
    GameVariables.farm.animals.add(AnimalType.CHICKEN.getNew());

    assertEquals(Animal.HungerLevel.FULL, GameVariables.farm.animals.get(0).hunger);
    assertEquals(0, GameVariables.farm.crops.get(0).daysSincePlanted);

    GameEnvironment.endDay();

    assertEquals(Animal.HungerLevel.HUNGRY, GameVariables.farm.animals.get(0).hunger);
    assertEquals(1, GameVariables.farm.crops.get(0).daysSincePlanted);

    GameVariables.farm.animals.get(0).hunger = Animal.HungerLevel.STARVING;

    GameEnvironment.endDay();

    assertEquals(0, GameVariables.farm.animals.size());
  }

  @Test
  void fertilizeCropsTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameEnvironment.fertilizeCrops(CropType.CACTUS, ItemType.FERTILIZER);

    GameVariables.farm.crops.add(CropType.CACTUS.getNew());

    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);

    GameEnvironment.fertilizeCrops(CropType.CACTUS, ItemType.BACON);

    assertEquals(false, GameVariables.farm.crops.get(0).fertilized);

    GameEnvironment.fertilizeCrops(CropType.CACTUS, ItemType.FERTILIZER);

    assertEquals(true, GameVariables.farm.crops.get(0).fertilized);
  }

  @Test
  void endDayTestWindow() {

    new GameWindow();

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    GameVariables.totalDays = 1;

    GameEnvironment.endDay();

    assertEquals("Score: " + GameVariables.score(),
        GameEnvironment.window.getScoreLabel().getText());
  }

  @Test
  void startGameTest() {
    String input = "5\n" + "TestFarmer\n" + "18\n" + "1\n" + "TestFarm\n" + "5\n" + "-1\n" + "1\n"
        + "0\n" + "0\n" + "10\n" + "10\n" + "10\n" + "7\n" + "7\n" + "7\n" + "7\n" + "7\n" + "7\n";

    GameEnvironment.scanner = new Scanner(input);

    GameEnvironment.startGame();
  }
}
