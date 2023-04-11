package main.java;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.java.Farm.FarmType;
import main.java.Farmer.GenderType;
import main.java.animals.Animal;
import main.java.animals.Animal.AnimalType;
import main.java.animals.Animal.HungerLevel;
import main.java.crops.Crop;
import main.java.crops.Crop.CropType;
import main.java.helpers.GeneralHelpers;
import main.java.items.Item.ItemCategory;
import main.java.items.Item.ItemType;
import main.swing.GameWindow;

/**
 * This is the game's main environment class. It contains all the required
 * methods for the gameplay to progress.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class GameEnvironment {

  /**
   * A static scanner used throughout the class.
   */
  public static Scanner scanner = new Scanner(System.in);

  /**
   * A static input flag used to validate input.
   */
  static boolean validInput = false;

  /**
   * A reference to the Swing Frame when running with the GUI.
   */
  public static GameWindow window;

  /**
   * Prints the given text to a new line with a 5ms delay.
   *
   * @param text The input text to print.
   */
  public static void print(Object text) {
    System.out.println(text);
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  /**
   * Prints an empty line with a 5ms delay.
   *
   */
  public static void print() {
    System.out.println();
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  /**
   * Prints the given text to the window's output area.
   *
   * @param text The input text to print.
   */
  public static void windowPrint(Object text) {
    if (window != null) {
      window.getOutputArea().setText(window.getOutputArea().getText() + "\n" + text);
    } else {
      print(text);
    }
  }

  /**
   * Called upon starting the game with a GUI to make the welcome pane visible.
   */
  public static void initialize() {
    window.setLayer(window.getWelcomePane());
    windowPrint("Hover over Crops, Animals, and Items to see their details.");
  }

  /**
   * Refresh the components on the info panel to display current information.
   */
  public static void refreshInfoPanel() {
    if (window != null && GameVariables.farm != null) {
      window.getStatusLabel().setText(String.valueOf(GameVariables.farm.name));
      window.getMoneyOutputLabel().setText(String.valueOf(GameVariables.farm.money));
      window.getNameOutputLabel().setText(String.valueOf(GameVariables.farmer.name));
      window.getAgeOutputLabel().setText(String.valueOf(GameVariables.farmer.age));
      window.getFarmTypeOutputLabel()
          .setText(String.valueOf(GameVariables.farm.farmType.toString()));
      window.getTidinessOutputText()
          .setText(String.valueOf(GameVariables.farm.tidinessDescription()));
      window.getDayOutputLabel().setText(String.valueOf(GameVariables.currentDay));

      window.getActionsRemainingLabel().setText(GameVariables.actions + " Actions Remaining:");

      if (GameVariables.actions > 0) {
        window.getFeedAnimalsButton().setEnabled(true);
        window.getHarvestAnimalsButton().setEnabled(true);
        window.getHarvestCropsButton().setEnabled(true);
        window.getPlayAnimalsButton().setEnabled(true);
        window.getTendCropsButton().setEnabled(true);
        window.getTendFarmButton().setEnabled(true);
      } else {
        window.getFeedAnimalsButton().setEnabled(false);
        window.getHarvestAnimalsButton().setEnabled(false);
        window.getHarvestCropsButton().setEnabled(false);
        window.getPlayAnimalsButton().setEnabled(false);
        window.getTendCropsButton().setEnabled(false);
        window.getTendFarmButton().setEnabled(false);
      }
    }
  }

  /**
   * Refresh the components on the store pane to display current information.
   */
  public static void refreshStorePane() {

    if (window != null && GameVariables.farm != null) {

      window.getStorePaneMoneyLabel().setText("Money: $" + GameVariables.farm.money);

      window.getStorePaneInfoPane().removeAll();

      JLabel animalsLabel = new JLabel("Animals:");
      animalsLabel.setHorizontalAlignment(SwingConstants.CENTER);
      animalsLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints animalsLabelGbc = new GridBagConstraints();
      animalsLabelGbc.insets = new Insets(0, 0, 5, 5);
      animalsLabelGbc.gridx = 0;
      animalsLabelGbc.gridy = 0;
      window.getStorePaneInfoPane().add(animalsLabel, animalsLabelGbc);

      JLabel storePanePriceLabel = new JLabel("Price:");
      storePanePriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
      storePanePriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints storePanePriceLabelGbc = new GridBagConstraints();
      storePanePriceLabelGbc.insets = new Insets(0, 0, 5, 5);
      storePanePriceLabelGbc.gridx = 1;
      storePanePriceLabelGbc.gridy = 0;
      window.getStorePaneInfoPane().add(storePanePriceLabel, storePanePriceLabelGbc);

      int count = 1;

      for (AnimalType type : GameVariables.store.animals) {

        GridBagConstraints animalLabelGbc = new GridBagConstraints();
        animalLabelGbc.anchor = GridBagConstraints.WEST;
        animalLabelGbc.insets = new Insets(0, 0, 5, 5);
        animalLabelGbc.gridx = 0;
        animalLabelGbc.gridy = count;
        JLabel animalLabel = new JLabel(type.get().name);
        animalLabel.setToolTipText(type.get().description);
        window.getStorePaneInfoPane().add(animalLabel, animalLabelGbc);

        GridBagConstraints animalPriceLabelGbc = new GridBagConstraints();
        animalPriceLabelGbc.insets = new Insets(0, 0, 5, 5);
        animalPriceLabelGbc.gridx = 1;
        animalPriceLabelGbc.gridy = count;
        JLabel animalPriceLabel = new JLabel("$" + type.get().price);
        window.getStorePaneInfoPane().add(animalPriceLabel, animalPriceLabelGbc);

        JButton purchaseAnimalButton = new JButton("Purchase");
        purchaseAnimalButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            GameVariables.store.buyAnimalFromType(type);
          }
        });
        purchaseAnimalButton.setFocusable(false);
        GridBagConstraints purchaseAnimalButtonGbc = new GridBagConstraints();
        purchaseAnimalButtonGbc.insets = new Insets(0, 0, 5, 0);
        purchaseAnimalButtonGbc.gridx = 2;
        purchaseAnimalButtonGbc.gridy = count;
        window.getStorePaneInfoPane().add(purchaseAnimalButton, purchaseAnimalButtonGbc);

        count++;
      }

      JLabel storePaneItemsLabel = new JLabel("Items:");
      storePaneItemsLabel.setHorizontalAlignment(SwingConstants.CENTER);
      storePaneItemsLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints storePaneItemsLabelGbc = new GridBagConstraints();
      storePaneItemsLabelGbc.insets = new Insets(0, 0, 5, 5);
      storePaneItemsLabelGbc.gridx = 0;
      storePaneItemsLabelGbc.gridy = count;
      window.getStorePaneInfoPane().add(storePaneItemsLabel, storePaneItemsLabelGbc);

      count++;

      for (ItemCategory category : ItemCategory.values()) {

        if (category == ItemCategory.PRODUCT) {
          continue;
        }
        JLabel storePaneCategoryLabel = new JLabel(category.toString() + ":");
        storePaneCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        storePaneCategoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        GridBagConstraints storePaneCategoryLabelGbc = new GridBagConstraints();
        storePaneCategoryLabelGbc.insets = new Insets(0, 0, 5, 5);
        storePaneCategoryLabelGbc.gridx = 0;
        storePaneCategoryLabelGbc.gridy = count;
        window.getStorePaneInfoPane().add(storePaneCategoryLabel, storePaneCategoryLabelGbc);

        count++;

        for (ItemType item : GameVariables.store.items) {
          if (item.get().category == category) {

            GridBagConstraints itemsLabelGbc = new GridBagConstraints();
            itemsLabelGbc.anchor = GridBagConstraints.WEST;
            itemsLabelGbc.insets = new Insets(0, 0, 0, 5);
            itemsLabelGbc.gridx = 0;
            itemsLabelGbc.gridy = count;
            JLabel itemsLabel = new JLabel(item.get().name);
            itemsLabel.setToolTipText(item.get().description);
            window.getStorePaneInfoPane().add(itemsLabel, itemsLabelGbc);

            GridBagConstraints priceLabelGbc = new GridBagConstraints();
            priceLabelGbc.insets = new Insets(0, 0, 0, 5);
            priceLabelGbc.gridx = 1;
            priceLabelGbc.gridy = count;
            JLabel priceLabel = new JLabel("$" + item.get().price);
            window.getStorePaneInfoPane().add(priceLabel, priceLabelGbc);

            JButton purchaseButton = new JButton("Purchase");
            purchaseButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                GameVariables.store.buyItemFromType(item);
              }
            });
            purchaseButton.setFocusable(false);
            GridBagConstraints purchaseButtonGbc = new GridBagConstraints();
            purchaseButtonGbc.gridx = 2;
            purchaseButtonGbc.gridy = count;
            window.getStorePaneInfoPane().add(purchaseButton, purchaseButtonGbc);

            count++;
          }
        }
      }

      window.getStorePaneInfoPane().revalidate();
      window.getStorePaneInfoPane().repaint();
    }
  }

  /**
   * Refresh the components on the feed animals pane to display current
   * information.
   */
  public static void refreshFeedAnimalsPane() {

    if (GameVariables.farm != null) {

      window.getFeedAnimalsPaneInfoPane().removeAll();

      JLabel feedAnimalsPaneAnimalLabel = new JLabel("Animal:");
      feedAnimalsPaneAnimalLabel.setHorizontalAlignment(SwingConstants.CENTER);
      feedAnimalsPaneAnimalLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints feedAnimalsPaneAnimalLabelGbc = new GridBagConstraints();
      feedAnimalsPaneAnimalLabelGbc.insets = new Insets(0, 0, 5, 5);
      feedAnimalsPaneAnimalLabelGbc.gridx = 0;
      feedAnimalsPaneAnimalLabelGbc.gridy = 0;
      window.getFeedAnimalsPaneInfoPane().add(feedAnimalsPaneAnimalLabel,
          feedAnimalsPaneAnimalLabelGbc);

      JLabel feedAnimalsPaneAmountLabel = new JLabel("Amount:");
      feedAnimalsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
      feedAnimalsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints feedAnimalsPaneAmountLabelGbc = new GridBagConstraints();
      feedAnimalsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
      feedAnimalsPaneAmountLabelGbc.gridx = 1;
      feedAnimalsPaneAmountLabelGbc.gridy = 0;
      window.getFeedAnimalsPaneInfoPane().add(feedAnimalsPaneAmountLabel,
          feedAnimalsPaneAmountLabelGbc);

      JLabel feedAnimalsPaneHungerLabel = new JLabel("Worst Hunger:");
      feedAnimalsPaneHungerLabel.setHorizontalAlignment(SwingConstants.CENTER);
      feedAnimalsPaneHungerLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints feedAnimalsPaneHungerLabelGbc = new GridBagConstraints();
      feedAnimalsPaneHungerLabelGbc.insets = new Insets(0, 0, 5, 5);
      feedAnimalsPaneHungerLabelGbc.gridx = 2;
      feedAnimalsPaneHungerLabelGbc.gridy = 0;
      window.getFeedAnimalsPaneInfoPane().add(feedAnimalsPaneHungerLabel,
          feedAnimalsPaneHungerLabelGbc);

      int count = 1;

      for (AnimalType type : AnimalType.values()) {
        if (GameVariables.farm.hasAnimal(type)) {

          GridBagConstraints feedAnimalLabelGbc = new GridBagConstraints();
          feedAnimalLabelGbc.insets = new Insets(0, 0, 5, 5);
          feedAnimalLabelGbc.gridx = 0;
          feedAnimalLabelGbc.gridy = count;
          JLabel feedAnimalLabel = new JLabel(type.get().name + "s");
          feedAnimalLabel.setToolTipText(type.get().description);
          window.getFeedAnimalsPaneInfoPane().add(feedAnimalLabel, feedAnimalLabelGbc);

          GridBagConstraints feedAnimalAmountLabelGbc = new GridBagConstraints();
          feedAnimalAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
          feedAnimalAmountLabelGbc.gridx = 1;
          feedAnimalAmountLabelGbc.gridy = count;
          JLabel feedAnimalAmountLabel = new JLabel(
              String.valueOf(GameVariables.farm.animalAmount(type)));
          window.getFeedAnimalsPaneInfoPane().add(feedAnimalAmountLabel, feedAnimalAmountLabelGbc);

          HungerLevel hunger = HungerLevel.FULL;

          for (Animal animal : GameVariables.farm.animals) {
            if (animal.type == type) {
              if (animal.hunger.ordinal() < hunger.ordinal()) {
                hunger = animal.hunger;
              }
            }
          }

          GridBagConstraints feedAnimalHungerLabelGbc = new GridBagConstraints();
          feedAnimalHungerLabelGbc.insets = new Insets(0, 0, 5, 5);
          feedAnimalHungerLabelGbc.gridx = 2;
          feedAnimalHungerLabelGbc.gridy = count;
          JLabel feedAnimalHungerLabel = new JLabel(hunger.toString());
          window.getFeedAnimalsPaneInfoPane().add(feedAnimalHungerLabel, feedAnimalHungerLabelGbc);

          if (GameVariables.actions > 0 && GameVariables.farm.items.hasItem(type.get().feedItem)
              && hunger != HungerLevel.FULL) {

            JButton feedAnimalButton = new JButton("Feed");
            feedAnimalButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                feedAnimalFromButton(type);
              }
            });
            feedAnimalButton.setFocusable(false);
            GridBagConstraints feedAnimalButtonGbc = new GridBagConstraints();
            feedAnimalButtonGbc.insets = new Insets(0, 0, 5, 5);
            feedAnimalButtonGbc.gridx = 3;
            feedAnimalButtonGbc.gridy = count;
            window.getFeedAnimalsPaneInfoPane().add(feedAnimalButton, feedAnimalButtonGbc);
          }

          count++;
        }
      }

      window.getFeedAnimalsPaneInfoPane().revalidate();
      window.getFeedAnimalsPaneInfoPane().repaint();
    }
  }

  /**
   * Feed all animals in farm of a given type. Called from a button press on the
   * feed animals pane.
   * 
   * @param type The animal type to feed.
   * @see Farm
   */
  public static void feedAnimalFromButton(AnimalType type) {

    if (GameVariables.farm.items.removeItem(type.get().feedItem)) {
      for (Animal animal : GameVariables.farm.animals) {
        if (animal.type == type) {
          animal.eat();
        }
      }
      GameVariables.actions--;
      windowPrint("You have used one " + type.get().feedItem.get().name + " to feed your "
          + type.get().name + "s.");
    } else {
      windowPrint("You don't have the correct item to feed " + type.get().name + "s.");
    }

    refreshFeedAnimalsPane();
  }

  /**
   * Refresh the components on the tend crops pane to display current information.
   */
  public static void refreshTendCropsPane() {

    if (GameVariables.farm != null) {

      window.getTendCropsPaneInfoPane().removeAll();

      JLabel tendCropsPaneTitleLabel = new JLabel("Crops:");
      tendCropsPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      tendCropsPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints tendCropsPaneTitleLabelGbc = new GridBagConstraints();
      tendCropsPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
      tendCropsPaneTitleLabelGbc.gridx = 0;
      tendCropsPaneTitleLabelGbc.gridy = 0;
      window.getTendCropsPaneInfoPane().add(tendCropsPaneTitleLabel, tendCropsPaneTitleLabelGbc);

      JLabel tendCropsPaneAmountLabel = new JLabel("Amount:");
      tendCropsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
      tendCropsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints tendCropsPaneAmountLabelGbc = new GridBagConstraints();
      tendCropsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
      tendCropsPaneAmountLabelGbc.gridx = 1;
      tendCropsPaneAmountLabelGbc.gridy = 0;
      window.getTendCropsPaneInfoPane().add(tendCropsPaneAmountLabel, tendCropsPaneAmountLabelGbc);

      JLabel tendCropsPaneFertilizingAmountLabel = new JLabel("Need Fertilizing:");
      tendCropsPaneFertilizingAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
      tendCropsPaneFertilizingAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints tendCropsPaneFertilizingAmountLabelGbc = new GridBagConstraints();
      tendCropsPaneFertilizingAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
      tendCropsPaneFertilizingAmountLabelGbc.gridx = 2;
      tendCropsPaneFertilizingAmountLabelGbc.gridy = 0;
      window.getTendCropsPaneInfoPane().add(tendCropsPaneFertilizingAmountLabel,
          tendCropsPaneFertilizingAmountLabelGbc);

      int count = 1;

      for (CropType type : CropType.values()) {
        if (GameVariables.farm.hasCrop(type)) {

          GridBagConstraints tendCropLabelGbc = new GridBagConstraints();
          tendCropLabelGbc.insets = new Insets(0, 0, 0, 5);
          tendCropLabelGbc.gridx = 0;
          tendCropLabelGbc.gridy = count;
          JLabel tendCropLabel = new JLabel(type.get().pluralName);
          tendCropLabel.setToolTipText(type.get().description);
          window.getTendCropsPaneInfoPane().add(tendCropLabel, tendCropLabelGbc);

          GridBagConstraints tendCropAmountLabelGbc = new GridBagConstraints();
          tendCropAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
          tendCropAmountLabelGbc.gridx = 1;
          tendCropAmountLabelGbc.gridy = count;
          JLabel tendCropAmountLabel = new JLabel(
              String.valueOf(GameVariables.farm.cropAmount(type)));
          window.getTendCropsPaneInfoPane().add(tendCropAmountLabel, tendCropAmountLabelGbc);

          int fertilizedCount = 0;
          for (Crop crop : GameVariables.farm.crops) {
            if (crop.type == type && !crop.fertilized) {
              fertilizedCount++;
            }
          }

          GridBagConstraints tendCropFertilizeLabelGbc = new GridBagConstraints();
          tendCropFertilizeLabelGbc.insets = new Insets(0, 0, 0, 5);
          tendCropFertilizeLabelGbc.gridx = 2;
          tendCropFertilizeLabelGbc.gridy = count;
          JLabel tendCropFertilizeLabel = new JLabel(String.valueOf(fertilizedCount));
          window.getTendCropsPaneInfoPane().add(tendCropFertilizeLabel, tendCropFertilizeLabelGbc);

          if (GameVariables.actions > 0) {

            JButton tendCropWaterButton = new JButton("Water");
            tendCropWaterButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                tendCropFromButton(type, null);
              }
            });
            tendCropWaterButton.setFocusable(false);
            GridBagConstraints tendCropWaterButtonGbc = new GridBagConstraints();
            tendCropWaterButtonGbc.insets = new Insets(0, 0, 0, 5);
            tendCropWaterButtonGbc.gridx = 3;
            tendCropWaterButtonGbc.gridy = count;
            window.getTendCropsPaneInfoPane().add(tendCropWaterButton, tendCropWaterButtonGbc);
          }

          if (GameVariables.actions > 0 && GameVariables.farm.items.hasItem(type.get().tendingItem)
              && fertilizedCount > 0) {

            JButton tendCropFertilizeButton = new JButton("Fertilize");
            tendCropFertilizeButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                tendCropFromButton(type, type.get().tendingItem);
              }
            });
            tendCropFertilizeButton.setFocusable(false);
            GridBagConstraints tendCropFertilizeButtonGbc = new GridBagConstraints();
            tendCropFertilizeButtonGbc.gridx = 4;
            tendCropFertilizeButtonGbc.gridy = count;
            window.getTendCropsPaneInfoPane().add(tendCropFertilizeButton,
                tendCropFertilizeButtonGbc);
          }

          count++;
        }
      }

      window.getTendCropsPaneInfoPane().revalidate();
      window.getTendCropsPaneInfoPane().repaint();
    }
  }

  /**
   * Fertilizes all crops of a given type using the given item.
   * 
   * @param type The type of crop to fertilize.
   * @param item The item to use. Only waters the crops if null.
   */
  public static void tendCropFromButton(CropType type, ItemType item) {
    if (item == null || item == type.get().tendingItem) {
      if (GameVariables.farm.hasCrop(type)) {

        GameVariables.actions--;

        if (item != null) {
          GameVariables.farm.items.removeItem(type.get().tendingItem);
          windowPrint("You have fertilized your " + type.get().pluralName.toLowerCase() + ".");
        } else {
          windowPrint("You have watered your " + type.get().pluralName.toLowerCase() + ".");
        }

        for (Crop crop : GameVariables.farm.crops) {

          if (crop.type == type) {
            crop.water();
            if (item != null) {
              crop.fertilized = true;
            }
          }
        }
      } else {
        windowPrint("You have no planted " + type.get().pluralName.toLowerCase() + ".");
      }
    } else {
      windowPrint("Incorrect item to fertilize " + type.get().pluralName.toLowerCase() + ".");
    }

    refreshTendCropsPane();
  }

  /**
   * Refresh the components on the animals pane to display current information.
   */
  public static void refreshAnimalPane() {

    if (GameVariables.farm != null) {

      window.getAnimalPaneInfoPane().removeAll();

      JLabel animalPaneTitleLabel = new JLabel("Animals:");
      animalPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      animalPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints animalPaneTitleLabelGbc = new GridBagConstraints();
      animalPaneTitleLabelGbc.anchor = GridBagConstraints.WEST;
      animalPaneTitleLabelGbc.insets = new Insets(0, 5, 5, 0);
      animalPaneTitleLabelGbc.gridx = 0;
      animalPaneTitleLabelGbc.gridy = 0;
      window.getAnimalPaneInfoPane().add(animalPaneTitleLabel, animalPaneTitleLabelGbc);

      int count = 1;

      for (AnimalType type : AnimalType.values()) {
        if (GameVariables.farm.hasAnimal(type)) {

          JLabel animalHeaderLabel = new JLabel(type.get().name + "s:");
          animalHeaderLabel.setToolTipText(type.get().description);
          animalHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
          animalHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
          GridBagConstraints animalHeaderLabelGbc = new GridBagConstraints();
          animalHeaderLabelGbc.anchor = GridBagConstraints.WEST;
          animalHeaderLabelGbc.insets = new Insets(0, 5, 5, 0);
          animalHeaderLabelGbc.gridx = 0;
          animalHeaderLabelGbc.gridy = count;
          window.getAnimalPaneInfoPane().add(animalHeaderLabel, animalHeaderLabelGbc);

          count++;
        }

        for (Animal animal : GameVariables.farm.animals) {
          if (animal.type == type) {

            JLabel animalLabel = new JLabel(animal.name + " - Hunger level: " + animal.hunger
                + ", Happiness level: " + animal.happiness() + ", Health: " + animal.health);
            animalLabel.setToolTipText(type.get().description);
            animalLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
            GridBagConstraints animalLabelGbc = new GridBagConstraints();
            animalLabelGbc.anchor = GridBagConstraints.WEST;
            animalLabelGbc.gridwidth = 5;
            animalLabelGbc.insets = new Insets(0, 5, 5, 0);
            animalLabelGbc.gridx = 0;
            animalLabelGbc.gridy = count;
            window.getAnimalPaneInfoPane().add(animalLabel, animalLabelGbc);

            count++;
          }
        }
      }

      window.getAnimalPaneInfoPane().revalidate();
      window.getAnimalPaneInfoPane().repaint();
    }
  }

  /**
   * Refresh the components on the crops pane to display current information.
   */
  public static void refreshCropPane() {

    if (GameVariables.farm != null) {

      window.getCropPaneInfoPane().removeAll();

      JLabel cropPaneTitleLabel = new JLabel("Crops:");
      cropPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      cropPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints cropPaneTitleLabelGbc = new GridBagConstraints();
      cropPaneTitleLabelGbc.anchor = GridBagConstraints.WEST;
      cropPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
      cropPaneTitleLabelGbc.gridx = 0;
      cropPaneTitleLabelGbc.gridy = 0;
      window.getCropPaneInfoPane().add(cropPaneTitleLabel, cropPaneTitleLabelGbc);

      int count = 1;

      for (CropType type : CropType.values()) {
        if (GameVariables.farm.hasCrop(type)) {

          JLabel cropHeaderLabel = new JLabel(type.get().pluralName + ":");
          cropHeaderLabel.setToolTipText(type.get().description);
          cropHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
          cropHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
          GridBagConstraints cropHeaderLabelGbc = new GridBagConstraints();
          cropHeaderLabelGbc.anchor = GridBagConstraints.WEST;
          cropHeaderLabelGbc.insets = new Insets(0, 0, 5, 5);
          cropHeaderLabelGbc.gridx = 0;
          cropHeaderLabelGbc.gridy = count;
          window.getCropPaneInfoPane().add(cropHeaderLabel, cropHeaderLabelGbc);

          count++;
        }

        for (Crop crop : GameVariables.farm.crops) {
          if (crop.type == type) {

            JLabel cropLabel = new JLabel(
                crop.name + " - Days until dehydrated: " + crop.daysUntilDehydrated()
                    + ", Days until harvestable: " + crop.daysUntilHarvestable() + ", Health: "
                    + crop.health + ", Fertilized: " + crop.fertilized);
            cropLabel.setToolTipText(type.get().description);
            cropLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
            GridBagConstraints cropLabelGbc = new GridBagConstraints();
            cropLabelGbc.insets = new Insets(0, 0, 5, 0);
            cropLabelGbc.anchor = GridBagConstraints.WEST;
            cropLabelGbc.gridwidth = 5;
            cropLabelGbc.gridx = 0;
            cropLabelGbc.gridy = count;
            window.getCropPaneInfoPane().add(cropLabel, cropLabelGbc);

            count++;
          }
        }
      }

      window.getCropPaneInfoPane().revalidate();
      window.getCropPaneInfoPane().repaint();
    }
  }

  /**
   * Refresh the components on the inventory pane to display current information.
   */
  public static void refreshInventoryPane() {

    if (GameVariables.farm != null) {

      window.getInventoryPaneInfoPane().removeAll();

      JLabel inventoryPaneTitleLabel = new JLabel("Inventory");
      inventoryPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      inventoryPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints inventoryPaneTitleLabelGbc = new GridBagConstraints();
      inventoryPaneTitleLabelGbc.insets = new Insets(0, 0, 0, 5);
      inventoryPaneTitleLabelGbc.gridx = 0;
      inventoryPaneTitleLabelGbc.gridy = 0;
      window.getInventoryPaneInfoPane().add(inventoryPaneTitleLabel, inventoryPaneTitleLabelGbc);

      JLabel inventoryPaneAmountLabel = new JLabel("Amount:");
      inventoryPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
      inventoryPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints inventoryPaneAmountLabelGbc = new GridBagConstraints();
      inventoryPaneAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
      inventoryPaneAmountLabelGbc.gridx = 1;
      inventoryPaneAmountLabelGbc.gridy = 0;
      window.getInventoryPaneInfoPane().add(inventoryPaneAmountLabel, inventoryPaneAmountLabelGbc);

      int count = 1;
      for (ItemType type : ItemType.values()) {
        if (GameVariables.farm.items.hasItem(type)) {

          GridBagConstraints itemLabelGbc = new GridBagConstraints();
          itemLabelGbc.anchor = GridBagConstraints.WEST;
          itemLabelGbc.insets = new Insets(0, 10, 0, 5);
          itemLabelGbc.gridx = 0;
          itemLabelGbc.gridy = count;
          JLabel itemLabel = new JLabel(type.get().name);
          itemLabel.setToolTipText(type.get().description);
          window.getInventoryPaneInfoPane().add(itemLabel, itemLabelGbc);

          GridBagConstraints itemAmountLabelGbc = new GridBagConstraints();
          itemAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
          itemAmountLabelGbc.gridx = 1;
          itemAmountLabelGbc.gridy = count;
          JLabel itemAmountLabel = new JLabel(
              String.valueOf(GameVariables.farm.items.itemAmount(type)));
          window.getInventoryPaneInfoPane().add(itemAmountLabel, itemAmountLabelGbc);

          count++;
        }
      }

      window.getInventoryPaneInfoPane().revalidate();
      window.getInventoryPaneInfoPane().repaint();
    }
  }

  /**
   * Refresh the components on the plant crops pane to display current
   * information.
   */
  public static void refreshPlantCropsPane() {

    if (GameVariables.farm != null) {

      window.getPlantCropsPaneInfoPane().removeAll();

      JLabel plantCropsPaneTitleLabel = new JLabel("Crops:");
      plantCropsPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      plantCropsPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints plantCropsPaneTitleLabelGbc = new GridBagConstraints();
      plantCropsPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
      plantCropsPaneTitleLabelGbc.gridx = 0;
      plantCropsPaneTitleLabelGbc.gridy = 0;
      window.getPlantCropsPaneInfoPane().add(plantCropsPaneTitleLabel, plantCropsPaneTitleLabelGbc);

      JLabel plantCropsPaneAmountLabel = new JLabel("Amount:");
      plantCropsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
      plantCropsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
      GridBagConstraints plantCropsPaneAmountLabelGbc = new GridBagConstraints();
      plantCropsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
      plantCropsPaneAmountLabelGbc.gridx = 1;
      plantCropsPaneAmountLabelGbc.gridy = 0;
      window.getPlantCropsPaneInfoPane().add(plantCropsPaneAmountLabel,
          plantCropsPaneAmountLabelGbc);

      int count = 1;
      for (ItemType type : ItemType.values()) {
        if (type.get().category == ItemCategory.SEED) {
          if (GameVariables.farm.items.hasItem(type)) {

            GridBagConstraints seedConstraint = new GridBagConstraints();
            seedConstraint.anchor = GridBagConstraints.WEST;
            seedConstraint.insets = new Insets(0, 30, 5, 5);
            seedConstraint.gridx = 0;
            seedConstraint.gridy = count;
            JLabel seedLabel = new JLabel(type.get().pluralName);
            seedLabel.setToolTipText(type.get().description);
            window.getPlantCropsPaneInfoPane().add(seedLabel, seedConstraint);

            GridBagConstraints amountConstraint = new GridBagConstraints();
            amountConstraint.insets = new Insets(0, 0, 5, 5);
            amountConstraint.gridx = 1;
            amountConstraint.gridy = count;
            JLabel amountLabel = new JLabel(
                String.valueOf(GameVariables.farm.items.itemAmount(type)));
            window.getPlantCropsPaneInfoPane().add(amountLabel, amountConstraint);

            JButton plantButton = new JButton("Plant");
            plantButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent arg0) {
                plantCropFromButton(type);
              }
            });
            GridBagConstraints plantConstraint = new GridBagConstraints();
            plantConstraint.insets = new Insets(0, 0, 5, 0);
            plantConstraint.gridx = 2;
            plantConstraint.gridy = count;
            window.getPlantCropsPaneInfoPane().add(plantButton, plantConstraint);

            count++;
          }
        }
      }

      window.getPlantCropsPaneInfoPane().revalidate();
      window.getPlantCropsPaneInfoPane().repaint();
    }
  }

  /**
   * Plants a crop of a given type by calling the Farm's plant crop method.
   * 
   * @param type The type of the seed to plant.
   */
  public static void plantCropFromButton(ItemType type) {
    GameVariables.farm.plantCrop(Crop.getCropTypeFromItem(type));
    refreshPlantCropsPane();
  }

  /**
   * Called from the setup window when using the GUI. Validates all input values
   * from components and creates new Farmer and Farm instances in GameVariables
   * using the values.
   * 
   * @see Farmer
   * @see Farm
   * @see GameVariables
   */
  public static void setupFromWindow() {

    // Create a totalDays variable and take user input until a valid input between 5
    // and 10 is received.
    int totalDays = 0;
    validInput = false;
    Object totalDaysRaw = window.getDaysSpinner().getValue();
    if (GeneralHelpers.tryCastInt(totalDaysRaw)) {
      totalDays = (int) totalDaysRaw;
      if (totalDays >= 5 && totalDays <= 10) {
        validInput = true;
      }
    }
    if (!validInput) {
      windowPrint("Days must be between 5 and 10");
      return;
    }

    // Create a farmerName variable and take user input until a valid input between
    // 3 and 15 characters and only letters is received.
    String farmerName = "";
    validInput = false;
    farmerName = window.getFarmerNameText().getText();
    if (farmerName.length() >= 3 && farmerName.length() <= 15
        && Pattern.matches("[a-zA-Z]+", farmerName)) {
      validInput = true;
    }
    if (!validInput) {
      windowPrint("Farmer name must be of length 3-15 with no numbers or special characters");
      return;
    }

    // Create a farmerAge variable and take user input until a valid input between
    // 18 and 80 is received.
    int farmerAge = 0;
    validInput = false;
    Object farmerAgeRaw = window.getAgeSpinner().getValue();
    if (GeneralHelpers.tryCastInt(farmerAgeRaw)) {
      farmerAge = (int) farmerAgeRaw;
      if (farmerAge >= 18 && farmerAge <= 80) {
        validInput = true;
      }
    }
    if (!validInput) {
      windowPrint("Farmer age must be between 18 and 80");
      return;
    }

    // Create a farmTypeNumber and take user input until a valid input between 1 and
    // the length of the FarmType values is received.
    int farmTypeNumber = 0;
    validInput = false;
    Object farmTypeRaw = window.getFarmTypeSlider().getValue();
    if (GeneralHelpers.tryCastInt(farmTypeRaw)) {
      farmTypeNumber = (int) farmTypeRaw;
      if (farmTypeNumber >= 0 && farmTypeNumber <= FarmType.values().length - 1) {
        validInput = true;
      }
    }
    if (!validInput) {
      windowPrint("Farm type must be between 0 and " + (FarmType.values().length - 1));
      return;
    }

    // Create a farmName variable and take user input until a valid input between 3
    // and 15 characters and only letters is received.
    String farmName = "";
    validInput = false;
    farmName = window.getFarmNameText().getText();
    if (farmName.length() >= 3 && farmName.length() <= 15) {
      validInput = true;
    }
    if (!validInput) {
      windowPrint("Farm name must be of length 3-15");
      return;
    }

    // Set total days in gameVariables.
    GameVariables.totalDays = totalDays;

    // Set farmer in gameVariables.
    GameVariables.farmer = new Farmer(farmerName, farmerAge, Farmer.GenderType.MALE);

    // Get the farm type based on the value given.
    FarmType farmType = FarmType.values()[farmTypeNumber];

    // Create farm.
    GameVariables.farm = new Farm(farmName, GameVariables.startingMoney, farmType);

    window.setLayer(window.getGamePane());
  }

  /**
   * The main gameplay method for the game when in command line mode. Called by
   * running the jar with the argument "text". Not used when running the game with
   * GUI.
   */
  public static void setupGame() {

    boolean quickStart = false;
    if (quickStart) {
      GameVariables.totalDays = 10;
      GameVariables.farmer = new Farmer("Test Farmer", 20, GenderType.MALE);
      GameVariables.farm = new Farm("Test Farm", GameVariables.startingMoney, FarmType.TEMPERATE);

    } else {

      // Create a totalDays variable and take user input until a valid input between 5
      // and 10 is received.
      int totalDays = 0;
      validInput = false;
      print("How many days would you like to play? (5-10)");
      while (!validInput) {
        System.out.print(": ");
        String totalDaysRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(totalDaysRaw)) {
          totalDays = Integer.parseInt(totalDaysRaw);
          if (totalDays >= 5 && totalDays <= 10) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between 5 and 10");
        }
      }
      // Set total days in gameVariables.
      GameVariables.totalDays = totalDays;

      // Create a farmerName variable and take user input until a valid input between
      // 3 and 15 characters and only letters is received.
      String farmerName = "";
      validInput = false;
      print("What is your farmer's name?");
      while (!validInput) {
        System.out.print(": ");
        farmerName = scanner.nextLine();
        if (farmerName.length() >= 3 && farmerName.length() <= 15
            && Pattern.matches("[a-zA-Z]+", farmerName)) {
          validInput = true;
        }
        if (!validInput) {
          print("Please enter a string of length 3-15 with no numbers or special characters");
        }
      }

      // Create a farmerAge variable and take user input until a valid input between
      // 18 and 80 is received.
      int farmerAge = 0;
      validInput = false;
      print("How old is your farmer? (18-80)");
      while (!validInput) {
        System.out.print(": ");
        String farmerAgeRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(farmerAgeRaw)) {
          farmerAge = Integer.parseInt(farmerAgeRaw);
          if (farmerAge >= 18 && farmerAge <= 80) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between 18 and 80");
        }
      }

      // Set farmer in gameVariables.
      GameVariables.farmer = new Farmer(farmerName, farmerAge, Farmer.GenderType.MALE);

      // Create a farmTypeNumber and take user input until a valid input between 1 and
      // the length of the FarmType values is received.
      int farmTypeNumber = 0;
      validInput = false;
      print("Which farm type would you like to play? (1-" + FarmType.values().length + ")");
      print("(Drier farms will rain less and require more watering of crops)");
      for (FarmType type : FarmType.values()) {
        print((type.ordinal() + 1) + " " + type);
      }
      while (!validInput) {
        System.out.print(": ");
        String farmTypeRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(farmTypeRaw)) {
          farmTypeNumber = Integer.parseInt(farmTypeRaw);
          if (farmTypeNumber >= 1 && farmTypeNumber <= FarmType.values().length) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between 1 and " + FarmType.values().length);
        }
      }

      // Create a farmName variable and take user input until a valid input between 3
      // and 15 characters and only letters is received.
      String farmName = "";
      validInput = false;
      print("What is your farm's name?");
      while (!validInput) {
        System.out.print(": ");
        farmName = scanner.nextLine();
        if (farmName.length() >= 3 && farmName.length() <= 15) {
          validInput = true;
        }
        if (!validInput) {
          print("Please enter a string of length 3-15");
        }
      }

      // Get the farm type based on the value given.
      FarmType farmType = FarmType.values()[farmTypeNumber - 1];

      // Create farm.
      GameVariables.farm = new Farm(farmName, GameVariables.startingMoney, farmType);

    }

    // Print all received variables.
    print();
    checkFarm();

    print();
    print("Welcome to your farm!");
  }

  /**
   * The main gameplay method for the game when in command line mode. Called by
   * running the jar with the argument "text". Not used when running the game with
   * GUI.
   */
  public static void startGame() {

    setupGame();

    boolean gameActive = true;

    while (gameActive) {

      if (GameVariables.currentDay > GameVariables.totalDays) {

        print();
        print("Game Over");
        print("Score: " + GameVariables.score());
        ScoreRecorder.addScore(GameVariables.farmer.name, GameVariables.score());
        print("Leaderboard:");
        for (int i = 0; i < Math.min(5, ScoreRecorder.scores.size()); i++) {
          print(
              i + 1 + ". " + ScoreRecorder.scoreNames.get(i) + " - " + ScoreRecorder.scores.get(i));
        }

        gameActive = false;
        return;
      }

      print();
      print("What would you like to do:");
      print("Free Actions:");
      print("1. View Farm Status");
      print("2. View Animals");
      print("3. View Crops");
      print("4. View Inventory");
      print("5. Visit Store");
      print("6. Plant Crops");
      print("7. Go To Bed");
      if (GameVariables.actions > 0) {
        print("---------------------");
        print(GameVariables.actions + " Actions Remaining:");
        print("8. Tend To Crops");
        print("9. Feed Animals");
        print("10. Play With Animals");
        print("11. Harvest Crops");
        print("12. Harvest Animal Products");
        print("13. Tend To The Farm");
      }

      // Create an action variable and take user input until a valid input between 1
      // and totalActions is received.
      int action = 0;
      int totalActions = 7;
      if (GameVariables.actions > 0) {
        totalActions = 13;
      }
      validInput = false;
      while (!validInput) {
        System.out.print(": ");
        String actionRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(actionRaw)) {
          action = Integer.parseInt(actionRaw);
          if (action >= 1 && action <= totalActions) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between 1 and " + totalActions);
        }
      }

      print();

      // Switch case for every action number.
      switch (action) {
        case 1:
          checkFarm();
          break;
        case 2:
          checkAnimals();
          break;
        case 3:
          checkCrops();
          break;
        case 4:
          checkInventory();
          break;
        case 5:
          visitStore();
          break;
        case 6:
          plantCrops();
          break;
        case 7:
          endDay();
          break;
        case 8:
          tendCrops();
          break;
        case 9:
          feedAnimals();
          break;
        case 10:
          playWithAnimals();
          break;
        case 11:
          harvestCrops();
          break;
        case 12:
          harvestAnimals();
          break;
        case 13:
          tendFarm();
          break;
        default:
          print("Invalid Action.");
          break;
      }
    }

    // End of game.
    // Close the scanner.
    scanner.close();
  }

  /**
   * A command line action. Presents the play with a list of animals in their farm
   * to feed. Takes input to either select an animal or to return.
   * 
   * @see Farm
   * @see Animal
   */
  public static void feedAnimals() {

    if (GameVariables.farm.hasAnyAnimals()) {

      boolean hasCorrectFeed = false;
      for (AnimalType animal : AnimalType.values()) {
        if (GameVariables.farm.hasAnimal(animal)) {
          if (GameVariables.farm.items.hasItem(animal.get().feedItem)) {
            hasCorrectFeed = true;
            break;
          }
        }
      }

      if (hasCorrectFeed) {
        print("Which animal would you like to feed:");

        int count = 1;
        for (AnimalType animal : AnimalType.values()) {
          if (GameVariables.farm.hasAnimal(animal)) {
            if (GameVariables.farm.items.hasItem(animal.get().feedItem)) {
              print(count + ". " + animal.get().name + "s");
              count++;
            }
          }
        }
        print("0. Return");
        // Create an action variable and take user input until a valid input between 1
        // and totalActions is received.
        int action = 0;
        int totalActions = count - 1;
        boolean validInput = false;
        while (!validInput) {
          System.out.print(": ");
          String actionRaw = scanner.nextLine();
          if (GeneralHelpers.tryParseInt(actionRaw)) {
            action = Integer.parseInt(actionRaw);
            if (action >= 0 && action <= totalActions) {
              validInput = true;
            }
          }
          if (!validInput) {
            print("Please enter an integer between 0 and " + totalActions);
          }
        }
        if (action == 0) {
          return;
        } else {
          AnimalType feedingAnimal = null;
          count = 1;
          for (AnimalType type : AnimalType.values()) {
            if (GameVariables.farm.hasAnimal(type)) {
              if (GameVariables.farm.items.hasItem(type.get().feedItem)) {
                if (count == action) {
                  feedingAnimal = type;
                  break;
                }
                count++;
              }
            }
          }
          if (feedingAnimal != null) {
            if (GameVariables.farm.items.removeItem(feedingAnimal.get().feedItem)) {
              for (Animal animal : GameVariables.farm.animals) {
                if (animal.type == feedingAnimal) {
                  animal.eat();
                }
              }
              GameVariables.actions--;
              print("You have used one " + feedingAnimal.get().feedItem.get().name
                  + " to feed your " + feedingAnimal.get().name + "s.");
            } else {
              print("You don't have the correct item to feed " + feedingAnimal.get().name + "s.");
            }

          } else {
            print("Error getting animal.");
          }
        }

      } else {
        print("You don't have any correct feed for your animals.");
      }
    } else {
      print("You don't have any animals.");
    }
  }

  /**
   * A command line action. Restores the farm to maximum tidiness.
   * 
   * @see Farm
   */
  public static void tendFarm() {

    if (GameVariables.farm.tidiness < 4) {
      if (GameVariables.actions > 0) {
        GameVariables.farm.resetTidiness();
        windowPrint("You have tidied the farm.");
        GameVariables.actions--;
      } else {
        windowPrint("Error. No actions remaining.");
      }
    } else {
      windowPrint("Your farm is already tidy.");
    }

    refreshInfoPanel();
  }

  /**
   * A command line action. Harvests all crops in the farm that are ready for
   * harvest.
   * 
   * @see Crop
   * @see Farm
   */
  public static void harvestCrops() {

    if (GameVariables.farm.hasAnyCrops()) {

      ArrayList<Crop> crops = new ArrayList<Crop>();
      Inventory harvestItems = new Inventory();

      for (Crop crop : GameVariables.farm.crops) {
        if (crop.daysUntilHarvestable() == 0) {
          crops.add(crop);
          harvestItems.addItem(crop.harvestItem);
        }
      }
      if (crops.size() > 0) {
        if (GameVariables.actions > 0) {
          for (Crop crop : crops) {
            GameVariables.farm.crops.remove(crop);
          }
          windowPrint("You have harvested:");
          for (ItemType type : ItemType.values()) {
            if (harvestItems.hasItem(type)) {
              if (harvestItems.itemAmount(type) > 1) {
                windowPrint(harvestItems.itemAmount(type) + " X " + type.get().pluralName);
              } else {
                windowPrint(harvestItems.itemAmount(type) + " X " + type.get().name);
              }
              for (int i = 0; i < harvestItems.itemAmount(type); i++) {
                GameVariables.farm.items.addItem(type);
              }
            }
          }
          GameVariables.actions--;
        } else {
          windowPrint("Error. No actions remaining.");
        }
      } else {
        windowPrint("You don't have any crops ready for harvest.");
      }
    } else {
      windowPrint("You don't have any crops.");
    }

    refreshInfoPanel();
  }

  /**
   * A command line action. Harvests products from all animals in the farm that
   * are ready for harvest.
   * 
   * @see Farm
   * @see Animal
   */
  public static void harvestAnimals() {

    Inventory items = new Inventory();

    if (GameVariables.farm.hasAnyAnimals()) {
      for (Animal animal : GameVariables.farm.animals) {
        if (!animal.harvested) {
          items.addItem(animal.harvestItem);
          animal.harvested = true;
        }
      }

      if (!items.isEmpty()) {
        if (GameVariables.actions > 0) {
          windowPrint("You have harvested:");
          for (ItemType type : ItemType.values()) {
            if (items.hasItem(type)) {
              if (items.itemAmount(type) > 1) {
                windowPrint(items.itemAmount(type) + " X " + type.get().pluralName);
              } else {
                windowPrint(items.itemAmount(type) + " X " + type.get().name);
              }
              for (int i = 0; i < items.itemAmount(type); i++) {
                GameVariables.farm.items.addItem(type);
              }
            }
          }
          GameVariables.actions--;
        } else {
          windowPrint("Error. No actions remaining.");
        }
      } else {
        windowPrint("All of your animals have had their products harvested already.");
      }
    } else {
      windowPrint("You don't have any animals.");
    }

    refreshInfoPanel();
  }

  /**
   * A command line action. Plays with all animals in the farm to restore their
   * health.
   * 
   * @see Farm
   * @see Animal
   */
  public static void playWithAnimals() {

    if (GameVariables.farm.hasAnyAnimals()) {
      if (GameVariables.actions > 0) {
        for (Animal animal : GameVariables.farm.animals) {
          animal.health = Animal.HealthLevel.HEALTHY;
        }
        windowPrint("You play with your animals. They are all healthy.");
        GameVariables.actions--;
      } else {
        windowPrint("Error. No actions remaining.");
      }
    } else {
      windowPrint("You don't have any animals.");
    }

    refreshInfoPanel();
  }

  /**
   * A command line action. Presents the player with a list of crops in the farm
   * to be fertilized or watered.
   * 
   * @see Farm
   * @see Crop
   */
  public static void tendCrops() {

    // If the farm has any crops.
    if (GameVariables.farm.hasAnyCrops()) {
      if (GameVariables.actions > 0) {

        print("What crop would you like to tend to:");

        int count = 1;
        for (CropType type : CropType.values()) {
          if (GameVariables.farm.hasCrop(type)) {
            print(count + ". " + type.get().pluralName);
            count++;
          }
        }
        print("0. Return");
        // Create an action variable and take user input until a valid input between 1
        // and totalActions is received.
        int action = 0;
        int totalActions = count - 1;
        boolean validInput = false;
        while (!validInput) {
          System.out.print(": ");
          String actionRaw = scanner.nextLine();
          if (GeneralHelpers.tryParseInt(actionRaw)) {
            action = Integer.parseInt(actionRaw);
            if (action >= 0 && action <= totalActions) {
              validInput = true;
            }
          }
          if (!validInput) {
            print("Please enter an integer between 0 and " + totalActions);
          }
        }
        if (action == 0) {
          return;
        } else {
          CropType crop = null;
          count = 1;
          for (CropType type : CropType.values()) {
            if (GameVariables.farm.hasCrop(type)) {
              if (count == action) {
                crop = type;
              }
              count++;
            }
          }
          if (crop != null) {
            tendCrop(crop);
          } else {
            print("Error getting crop.");
          }
        }
      } else {
        windowPrint("Error. No actions remaining.");
      }
    } else {
      print("You don't have any crops.");
    }
  }

  /**
   * Presents the player with the option to water or fertilize the crop selected
   * previously.
   * 
   * @param type The crop type selected to water or fertilize
   */
  public static void tendCrop(CropType type) {

    int action = 0;
    int totalActions = 1;

    print();
    print(type.get().pluralName + ":");
    print("1. Water all " + type.get().pluralName);
    if (GameVariables.farm.items.hasItem(type.get().tendingItem)) {
      print("2. Fertilize all " + type.get().pluralName);
      totalActions = 2;
    }
    print("0. Return");

    boolean validInput = false;
    while (!validInput) {
      System.out.print(": ");
      String actionRaw = scanner.nextLine();
      if (GeneralHelpers.tryParseInt(actionRaw)) {
        action = Integer.parseInt(actionRaw);
        if (action >= 0 && action <= totalActions) {
          validInput = true;
        }
      }
      if (!validInput) {
        print("Please enter an integer between 0 and " + totalActions);
      }
    }
    if (action == 0) {
      return;
    } else if (action == 1) {
      waterCrops(type);
      GameVariables.actions--;
      print("All " + type.get().pluralName + " have been watered");
    } else if (action == 2) {
      if (GameVariables.farm.items.hasItem(type.get().tendingItem)) {
        fertilizeCrops(type, type.get().tendingItem);
        GameVariables.farm.items.removeItem(type.get().tendingItem);
        GameVariables.actions--;
        print("All " + type.get().pluralName + " have been fertilized");
      } else {
        print(type.get().tendingItem.get().name + " needed to fertlize " + type.get().pluralName);
      }

    } else {
      print("Error getting tending action.");
    }
  }

  /**
   * A command line action. Presents the player with a list of seeds in the farm's
   * inventory to plant.
   * 
   * @see Farm
   * @see main.java.items.Item
   */
  public static void plantCrops() {

    // Whether the farm has any seeds to plant.
    boolean hasPlantable = false;

    // Check each itemtype. If it is a seed and it is present in the farm's
    // inventory, set hasPlantable to true.
    for (ItemType type : ItemType.values()) {
      if (type.get().category == ItemCategory.SEED) {
        if (GameVariables.farm.items.hasItem(type)) {
          hasPlantable = true;
          break;
        }
      }
    }

    // If the farm has any seeds to plant.
    if (hasPlantable) {

      print("What would you like to plant:");

      boolean looping = true;
      while (looping) {
        int count = 1;
        for (ItemType type : ItemType.values()) {
          if (type.get().category == ItemCategory.SEED) {
            if (GameVariables.farm.items.hasItem(type)) {
              print(count + ". " + type.get().pluralName + " X "
                  + GameVariables.farm.items.itemAmount(type));
              count++;
            }
          }
        }
        print("0. Return");
        // Create an action variable and take user input until a valid input between 1
        // and totalActions is received.
        int action = 0;
        int totalActions = count - 1;
        boolean validInput = false;
        while (!validInput) {
          System.out.print(": ");
          String actionRaw = scanner.nextLine();
          if (GeneralHelpers.tryParseInt(actionRaw)) {
            action = Integer.parseInt(actionRaw);
            if (action >= 0 && action <= totalActions) {
              validInput = true;
            }
          }
          if (!validInput) {
            print("Please enter an integer between 0 and " + totalActions);
          }
        }
        if (action == 0) {
          looping = false;
        } else {
          ItemType item = null;
          count = 1;
          for (ItemType type : ItemType.values()) {
            if (type.get().category == ItemCategory.SEED) {
              if (GameVariables.farm.items.hasItem(type)) {
                if (count == action) {
                  item = type;
                }
                count++;
              }
            }
          }

          if (item != null) {
            GameVariables.farm.plantCrop(Crop.getCropTypeFromItem(item));
          } else {
            print("Error getting item.");
          }
        }
      }
    } else {
      print("You don't have any seeds to plant.");
    }
  }

  /**
   * A command line action. Presents the player with a list of items to buy in the
   * store, to view animals, sell products, or to return home.
   * 
   * @see Store
   */
  public static void visitStore() {

    print("Welcome to the General Store!");
    print("Heres what we have:");

    while (true) {
      GameVariables.store.printItems();
      print("You have $" + GameVariables.farm.money);
      // Create an action variable and take user input until a valid input between 1
      // and totalActions is received.
      int action = 0;
      int totalActions = GameVariables.store.items.size();
      boolean validInput = false;
      while (!validInput) {
        System.out.print(": ");
        String actionRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(actionRaw)) {
          action = Integer.parseInt(actionRaw);
          if (action >= -2 && action <= totalActions) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between -2 and " + totalActions);
        }
      }
      if (action == -2) {
        sellProducts();
      } else if (action == -1) {
        viewStoreAnimals();
      } else if (action == 0) {
        break;
      } else {
        GameVariables.store.buyItem(action);
      }
    }
  }

  /**
   * Sell all of the player's products in the farm's inventory generated by
   * harvesting crops or from animals.
   * 
   * @see Store
   * @see main.java.items.Item
   * @see Farm
   * @see Inventory
   */
  public static void sellProducts() {

    Inventory sellingItems = new Inventory();

    for (ItemType type : ItemType.values()) {
      if (type.get().category == ItemCategory.PRODUCT) {
        if (GameVariables.farm.items.hasItem(type)) {
          for (int i = 0; i < GameVariables.farm.items.itemAmount(type); i++) {
            sellingItems.addItem(type);
          }
        }
      }
    }

    if (!sellingItems.isEmpty()) {
      windowPrint("You have sold:");
      int total = 0;
      for (ItemType type : ItemType.values()) {
        if (sellingItems.hasItem(type)) {
          if (sellingItems.itemAmount(type) > 1) {
            windowPrint(sellingItems.itemAmount(type) + " X " + type.get().pluralName);
          } else {
            windowPrint(sellingItems.itemAmount(type) + " X " + type.get().name);
          }
          for (int i = 0; i < sellingItems.itemAmount(type); i++) {
            total += type.get().price;
            GameVariables.farm.items.removeItem(type);
          }
        }
      }
      windowPrint("For a total of $" + total);
      GameVariables.farm.money += total;

    } else {
      windowPrint("You have no products to sell.");
    }

    refreshStorePane();
  }

  /**
   * A command line action. Views the animals available to buy from the store.
   * 
   * @see Store
   * @see Animal
   */
  public static void viewStoreAnimals() {

    print("Here are our animals:");

    while (true) {
      GameVariables.store.printAnimals();
      print("You have $" + GameVariables.farm.money);
      // Create an action variable and take user input until a valid input between 1
      // and totalActions is received.
      int action = 0;
      int totalActions = GameVariables.store.animals.size();
      boolean validInput = false;
      while (!validInput) {
        System.out.print(": ");
        String actionRaw = scanner.nextLine();
        if (GeneralHelpers.tryParseInt(actionRaw)) {
          action = Integer.parseInt(actionRaw);
          if (action >= 0 && action <= totalActions) {
            validInput = true;
          }
        }
        if (!validInput) {
          print("Please enter an integer between 0 and " + totalActions);
        }
      }
      if (action == 0) {
        break;
      } else {
        GameVariables.store.buyAnimal(action);
      }
    }
  }

  /**
   * A command line action. Prints a list of items present in the farm's
   * inventory.
   * 
   * @see Farm
   * @see Inventory
   * @see main.java.items.Item
   */
  public static void checkInventory() {
    print("Inventory:");
    for (ItemType type : ItemType.values()) {
      if (GameVariables.farm.items.hasItem(type)) {
        if (GameVariables.farm.items.itemAmount(type) > 1) {
          print(GameVariables.farm.items.itemAmount(type) + " X " + type.get().pluralName);
        } else {
          print(GameVariables.farm.items.itemAmount(type) + " X " + type.get().name);
        }

      }
    }
  }

  /**
   * A command line action. Prints a list of information regarding the farm's and
   * the farmer's attributes.
   * 
   * @see Farm
   * @see Farmer
   */
  public static void checkFarm() {
    print(GameVariables.farm.name + ":");
    print("Money: $" + GameVariables.farm.money);
    print("Farmer Name: " + GameVariables.farmer.name);
    print("Farmer Age: " + GameVariables.farmer.age);
    print("Current Day: " + GameVariables.currentDay);
    print("Farm Type: " + GameVariables.farm.farmType);
    print(GameVariables.farm.tidinessDescription());
  }

  /**
   * A command line action. Prints a list of animals in the farm as well as their
   * current attributes.
   * 
   * @see Farm
   * @see Animal
   */
  public static void checkAnimals() {

    print("Animals:");
    for (AnimalType type : AnimalType.values()) {
      if (!GameVariables.farm.hasAnimal(type)) {
        continue;
      }
      print("\n" + type.get().name + "s:");
      for (Animal animal : GameVariables.farm.animals) {
        if (animal.type == type) {
          print(animal.name + " - " + "Hunger level: " + animal.hunger + ", " + "Happiness level: "
              + animal.happiness() + ", " + "Health: " + animal.health);
        }
      }
    }
  }

  /**
   * A command line action. Prints a list of crops in the farm as well as their
   * current attributes.
   * 
   * @see Farm
   * @see Crop
   */
  public static void checkCrops() {

    print("Crops:");
    for (CropType type : CropType.values()) {
      if (!GameVariables.farm.hasCrop(type)) {
        continue;
      }
      print("\n" + type.get().pluralName + ":");
      for (Crop crop : GameVariables.farm.crops) {
        if (crop.type == type) {
          print(crop.name + " - " + "Days until dehydrated: " + crop.daysUntilDehydrated() + ", "
              + "Days until harvestable: " + crop.daysUntilHarvestable() + ", " + "Health: "
              + crop.health + ", " + "Fertilized: " + crop.fertilized);
        }
      }
    }
  }

  /**
   * Alias for calling fertilizeCrops with the given CropType and with null as the ItemType.
   * 
   * @see main.java.items.Item.ItemType
   * @param type The type of crop to water.
   */
  public static void waterCrops(CropType type) {
    fertilizeCrops(type, null);
  }

  /**
   * Calls the water() method on every crop of the given type and if the item is
   * the correct type, set their fertilized attribute to true.
   * 
   * @see Crop
   * @see Farm
   * 
   * @param type The type of crop to fertilize
   * @param item The ItemType to use to fertilize the the crops.
   */
  public static void fertilizeCrops(CropType type, ItemType item) {

    if (item == null || item == type.get().tendingItem) {
      if (GameVariables.farm.hasCrop(type)) {
        for (Crop crop : GameVariables.farm.crops) {
          if (crop.type == type) {
            crop.water();
            if (item != null) {
              crop.fertilized = true;
            }
          }
        }
      } else {
        print("You have no planted " + type.get().pluralName.toLowerCase() + ".");
      }
    } else {
      print("Incorrect item to fertilize " + type.get().pluralName.toLowerCase() + ".");
    }
  }

  // Called at the end of each day.
  // Waters crops if it rains, calls overnight functions on crops and animals,
  // checks animal happiness for bonus check.
  /**
   * A command line action. Called to end the day and begin the next day. Resets
   * actions, calls crop and animal sleep methods, runs random events, and checks
   * whether to end the game.
   */
  public static void endDay() {

    if (GameVariables.currentDay == GameVariables.totalDays) {
      GameVariables.currentDay++;
      if (window != null) {
        window.getScoreLabel().setText("Score: " + GameVariables.score());
        window.setLayer(window.getEndPane());
        window.getEndDetailsLabel().setText(GameVariables.farm.name + " - " 
            + GameVariables.totalDays + " Days - $" + GameVariables.farm.money + " In Profit");
        ScoreRecorder.addScore(GameVariables.farmer.name, GameVariables.score());
        String leaderboard = "";
        leaderboard += "<html>Leaderboard:";
        for (int i = 0; i < Math.min(5, ScoreRecorder.scores.size()); i++) {
          leaderboard += "<br>" + (i + 1) + ". " + ScoreRecorder.scoreNames.get(i) + " - "
              + ScoreRecorder.scores.get(i);
        }
        window.getLeaderboardLabel().setText(leaderboard);
      }
      return;
    }

    if (GameVariables.rand.nextFloat() <= GameVariables.farm.rainChance()) {
      windowPrint("It has rained and all of your crops have been watered.");
      for (Crop crop : GameVariables.farm.crops) {
        crop.water();
      }
    }

    for (Crop crop : GameVariables.farm.crops) {
      crop.sleep();
    }
    for (Animal animal : GameVariables.farm.animals) {
      animal.sleep();
    }

    if (GameVariables.farm.hasAnyAnimals()) {
      // If all animals are happy then the player receives a bonus.
      // The bonus gets larger depending on how many days have passed.
      boolean allHappy = true;
      for (Animal animal : GameVariables.farm.animals) {
        if (animal.happiness() == Animal.HappinessLevel.SAD) {
          allHappy = false;
        }
      }
      if (allHappy) {
        GameVariables.farm.money += GameVariables.currentDay * 10;
        windowPrint(
            "We at Animal Rights Inc congratulate you for keeping all of your animals happy."
                + "\nYour appointed farm stalker has sent you a check for $"
                + (GameVariables.currentDay * 10) + "!");
      }
    }

    GameVariables.currentDay++;
    GameVariables.actions = 2;
    GameVariables.farm.reduceTidyness();

    animalDeaths();
    cropDeaths();

    refreshInfoPanel();
  }

  /**
   * Called from endDay() to check whether any animals in farm have starved to
   * death.
   * 
   * @see Animal
   */
  public static void animalDeaths() {

    ArrayList<Animal> removeAnimals = new ArrayList<Animal>();
    for (Animal animal : GameVariables.farm.animals) {
      if (!animal.alive()) {
        windowPrint("A " + animal.name + " has starved in the night.");
        removeAnimals.add(animal);
      }
    }
    for (Animal animal : removeAnimals) {
      GameVariables.farm.animals.remove(animal);
    }
  }
  
  /**
   * Called from endDay() to check whether any crops in farm have died.
   * 
   * @see Crop
   */
  public static void cropDeaths() {

    ArrayList<Crop> removeCrops = new ArrayList<Crop>();
    for (Crop crop : GameVariables.farm.crops) {
      if (!crop.alive()) {
        windowPrint("A " + crop.name + " has withered in the night.");
        removeCrops.add(crop);
      }
    }
    for (Crop crop : removeCrops) {
      GameVariables.farm.crops.remove(crop);
    }
  }
}
