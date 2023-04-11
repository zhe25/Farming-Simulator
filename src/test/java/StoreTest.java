/**
 * Tests all methods within the Store class.
 */

package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.Farm;
import main.java.Farmer;
import main.java.GameEnvironment;
import main.java.GameVariables;
import main.java.animals.Animal.AnimalType;
import main.java.items.Item.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
class StoreTest {

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
  void buyItemFromTypeTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    assertEquals(0, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(30, GameVariables.farm.money);

    GameVariables.store.buyItemFromType(ItemType.CACTUS_SEED);

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(30 - ItemType.CACTUS_SEED.get().price, GameVariables.farm.money);

    GameVariables.farm.money = 0;

    GameVariables.store.buyItemFromType(ItemType.CACTUS_SEED);

    assertEquals(1, GameVariables.farm.items.itemAmount(ItemType.CACTUS_SEED));
    assertEquals(0, GameVariables.farm.money);
  }

  @Test
  void buyAnimalFromTypeTest() {

    GameVariables.farm = new Farm("TestFarm", GameVariables.startingMoney, Farm.FarmType.RAINY);
    GameVariables.farmer = new Farmer("TestFarmer", 18, Farmer.GenderType.MALE);

    assertEquals(0, GameVariables.farm.animals.size());
    assertEquals(30, GameVariables.farm.money);

    GameVariables.store.buyAnimalFromType(AnimalType.CHICKEN);

    assertEquals(1, GameVariables.farm.animals.size());
    assertEquals(30 - AnimalType.CHICKEN.get().price, GameVariables.farm.money);

    GameVariables.farm.money = 0;

    GameVariables.store.buyAnimalFromType(AnimalType.CHICKEN);

    assertEquals(1, GameVariables.farm.animals.size());
    assertEquals(0, GameVariables.farm.money);
  }
}
