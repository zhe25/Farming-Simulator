package main.java.animals;

import main.java.items.Item.ItemType;

public class Cow extends Animal {

  public Cow() {
    super(AnimalType.COW, 150, "Cow", "A large cow. Provides milk once a day.", ItemType.COW_FEED,
        ItemType.MILK);
  }
}
