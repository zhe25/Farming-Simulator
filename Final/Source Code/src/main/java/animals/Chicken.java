package main.java.animals;

import main.java.items.Item.ItemType;

public class Chicken extends Animal {

  public Chicken() {
    super(AnimalType.CHICKEN, 10, "Chicken", "A small chicken. Lays eggs once a day.",
        ItemType.CHICKEN_FEED, ItemType.EGG);
  }

}
