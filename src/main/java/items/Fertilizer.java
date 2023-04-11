package main.java.items;

public class Fertilizer extends Item {

  public Fertilizer() {
    super(5, "Fertilizer", "Bags of Fertilizer", "Reduces a crop's required water.",
        ItemType.FERTILIZER, ItemCategory.FERTILIZER);
  }
}
