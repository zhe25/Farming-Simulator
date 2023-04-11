package main.java.crops;

import main.java.items.Item.ItemType;

public class CucumberCrop extends Crop {

  public CucumberCrop() {
    super(CropType.CUCUMBER, 2, 2, ItemType.CUCUMBER_SEED, ItemType.FERTILIZER, ItemType.CARROT,
        "Cucumber", "Cucumbers", "A growing cucumber.");
  }
}
