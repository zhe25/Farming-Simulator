package main.java.crops;

import main.java.items.Item.ItemType;

public class WheatCrop extends Crop {

  public WheatCrop() {
    super(CropType.WHEAT, 2, 1, ItemType.WHEAT_SEED, ItemType.FERTILIZER, ItemType.WHEAT,
        "Wheat", "Wheat", "A growing bundle of wheat.");
  }
}
