package main.java.crops;

import main.java.items.Item.ItemType;

public class WatermelonCrop extends Crop {

  public WatermelonCrop() {
    super(CropType.WATERMELON, 3, 1, ItemType.WATERMELON_SEED, ItemType.FERTILIZER,
        ItemType.WATERMELON, "Watermelon", "Watermelon", "A growing watermelon.");
  }
}
