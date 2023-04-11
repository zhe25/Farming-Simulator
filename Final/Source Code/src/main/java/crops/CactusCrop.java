package main.java.crops;

import main.java.items.Item.ItemType;

public class CactusCrop extends Crop {

  public CactusCrop() {
    super(CropType.CACTUS, 1, 1, ItemType.CACTUS_SEED, ItemType.FERTILIZER,
        ItemType.PRICKLY_PEAR, "Cactus", "Cacti", "A growing cactus.");
  }
}
