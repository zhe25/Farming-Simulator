package main.java.animals;

import main.java.items.Item.ItemType;

public class Pig extends Animal {

  public Pig() {
    super(AnimalType.PIG, 50, "Pig", "A fat pig. Magically provides bacon when tended.",
        ItemType.PIG_FEED, ItemType.BACON);
  }

}
