package main.java.crops;

import main.java.items.Item.ItemType;

public class CarrotCrop extends Crop {

  public CarrotCrop() {
    super(CropType.CARROT, 1, 1, ItemType.CARROT_SEED, ItemType.FERTILIZER, ItemType.CARROT,
        "Carrot", "Carrots", "A growing carrot.");
  }
}
