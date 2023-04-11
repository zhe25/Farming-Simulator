package main.java.crops;

import main.java.items.Item.ItemType;

public class PotatoeCrop extends Crop {

  public PotatoeCrop() {
    super(CropType.POTATOE, 3, 2, ItemType.POTATOE_SEED, ItemType.FERTILIZER, ItemType.POTATOE,
        "Potatoe", "Potatoes", "A growing potatoe.");
  }
}
