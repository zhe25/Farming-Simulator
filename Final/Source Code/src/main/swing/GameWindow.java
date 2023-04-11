package main.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.FontUIResource;
import main.java.GameEnvironment;

/**
 * Creates the game's window and stores all required window components. Contains
 * the main entry function of the program.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class GameWindow {
  /**
   * Main entry function. Launches the game and creates a GameWindow instance. If
   * args contains "text", launch the game as a command line program instead of
   * using the GUI.
   * 
   * @param args The program arguments.
   */
  public static void main(String[] args) {
    if (args != null) {
      for (String arg : args) {
        if ("text".equals(arg)) {
          GameEnvironment.startGame();
          return;
        }
      }
    }
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          GameWindow window = new GameWindow();
          window.farmingSimulatorFrame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private JFrame farmingSimulatorFrame;

  private JLayeredPane layeredPane;
  private JPanel welcomePane;
  private JPanel setupPane;
  private JPanel gamePane;
  private JPanel informationPane;
  private JPanel inventoryPane;
  private JPanel plantCropsPane;
  private JPanel cropPane;
  private JPanel animalPane;
  private JPanel tendCropsPane;
  private JPanel feedAnimalsPane;
  private JPanel storePane;
  private JPanel endPane;

  private JSpinner daysSpinner;
  private JTextField farmerNameText;
  private JSpinner ageSpinner;
  private JTextField farmNameText;
  private JSlider farmTypeSlider;
  private JTextArea outputArea;

  private JLabel moneyOutputLabel;
  private JLabel nameOutputLabel;
  private JLabel ageOutputLabel;
  private JLabel farmTypeOutputLabel;
  private JTextArea tidinessOutputText;
  private JLabel dayOutputLabel;
  private JLabel statusLabel;

  private JPanel plantCropsPaneInfoPane;
  private JPanel inventoryPaneInfoPane;
  private JPanel cropPaneInfoPane;
  private JPanel animalPaneInfoPane;
  private JLabel actionsRemainingLabel;
  private JButton playAnimalsButton;
  private JButton harvestCropsButton;
  private JButton tendFarmButton;
  private JButton feedAnimalsButton;
  private JButton tendCropsButton;
  private JButton harvestAnimalsButton;
  private JPanel tendCropsPaneInfoPane;
  private JPanel feedAnimalsPaneInfoPane;
  private JPanel storePaneInfoPane;
  private JLabel storePaneMoneyLabel;
  private JLabel scoreLabel;
  private JLabel leaderboardLabel;
  private JLabel endDetailsLabel;

  /**
   * Construct a new GameWindow. Calls initialize(), sets the window of
   * GameEnvironment to this and calls initialize on GameEnvironment.
   * 
   * @see GameEnvironment#window
   * @see GameEnvironment#initialize()
   */
  public GameWindow() {
    initialize();
    GameEnvironment.window = this;
    GameEnvironment.initialize();
  }

  private void initialize() {
    
    UIManager.put("ToolTip.background", Color.TRANSLUCENT);
    UIManager.put("ToolTip.font", new FontUIResource("Tahoma", Font.BOLD, 14));
    ToolTipManager.sharedInstance().setInitialDelay(0);

    farmingSimulatorFrame = new JFrame();
    farmingSimulatorFrame.setTitle("Farming Simulator");
    farmingSimulatorFrame.setResizable(false);
    farmingSimulatorFrame.setBounds(100, 100, 500, 500);
    farmingSimulatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    farmingSimulatorFrame.getContentPane().setLayout(null);

    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 494, 385);

    farmingSimulatorFrame.getContentPane().add(layeredPane);
    
    JPanel overlapInterceptPane = new JPanel();
    overlapInterceptPane.setName("Intercept");
    layeredPane.setLayer(overlapInterceptPane, 1);
    overlapInterceptPane.setLayout(null);
    overlapInterceptPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    overlapInterceptPane.setBackground(Color.WHITE);
    overlapInterceptPane.setBounds(10, 11, 474, 374);
    layeredPane.add(overlapInterceptPane);
    
    JButton interceptButton = new JButton("");
    interceptButton.setForeground(Color.WHITE);
    interceptButton.setFocusable(false);
    interceptButton.setToolTipText("");
    interceptButton.setFocusable(false);
    interceptButton.setBounds(0, 0, 474, 374);
    overlapInterceptPane.add(interceptButton);

    welcomePane = new JPanel();
    welcomePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    layeredPane.setLayer(welcomePane, 2);
    welcomePane.setBounds(10, 11, 474, 374);
    layeredPane.add(welcomePane);
    welcomePane.setBackground(Color.WHITE);
    welcomePane.setLayout(null);

    JLabel welcomeLabel = new JLabel("Welcome to Farming Simulator");
    welcomeLabel.setBounds(98, 11, 275, 25);
    welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    welcomePane.add(welcomeLabel);
    JButton startButton = new JButton("Start Game");
    startButton.setToolTipText("");
    startButton.setFocusable(false);
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        setLayer(setupPane);
      }
    });
    startButton.setBounds(190, 171, 93, 32);
    welcomePane.add(startButton);

    JButton aboutUsButton = new JButton("About Us");
    aboutUsButton.setFocusable(false);
    aboutUsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setLayer(informationPane);
      }
    });
    aboutUsButton.setBounds(371, 331, 93, 32);
    welcomePane.add(aboutUsButton);

    JLabel welcomeImageLabel = new JLabel("");
    welcomeImageLabel
        .setIcon(new ImageIcon(GameWindow.class.getResource("/main/resources/Background1.jpg")));
    welcomeImageLabel.setBounds(0, 0, 474, 374);
    welcomePane.add(welcomeImageLabel);

    informationPane = new JPanel();
    informationPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    layeredPane.setLayer(informationPane, 0);
    informationPane.setBounds(10, 11, 474, 374);
    layeredPane.add(informationPane);
    informationPane.setBackground(Color.WHITE);
    informationPane.setLayout(null);

    JLabel gameDesignerLabel1 = new JLabel("Game Designer");
    gameDesignerLabel1.setBounds(59, 52, 124, 28);
    gameDesignerLabel1.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    informationPane.add(gameDesignerLabel1);

    JLabel gameDesignerOutputLabel1 = new JLabel("Daniel Felgate");
    gameDesignerOutputLabel1.setBounds(266, 52, 144, 28);
    gameDesignerOutputLabel1.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    informationPane.add(gameDesignerOutputLabel1);

    JLabel gameDesignerLabel2 = new JLabel("Game Designer");
    gameDesignerLabel2.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    gameDesignerLabel2.setBounds(59, 163, 116, 28);
    informationPane.add(gameDesignerLabel2);

    JLabel gameDesignerOutputLabel2 = new JLabel("He Zheng Jing Rui");
    gameDesignerOutputLabel2.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    gameDesignerOutputLabel2.setBounds(266, 163, 156, 28);
    informationPane.add(gameDesignerOutputLabel2);

    JLabel questionsLabel = new JLabel(
        "If you have any questions about the code, please email us:)");
    questionsLabel.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    questionsLabel.setBounds(29, 253, 420, 78);
    informationPane.add(questionsLabel);

    JLabel emailLabel1 = new JLabel("Email:");
    emailLabel1.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    emailLabel1.setBounds(61, 91, 54, 15);
    informationPane.add(emailLabel1);

    JLabel emailLabel2 = new JLabel("Email:");
    emailLabel2.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    emailLabel2.setBounds(59, 202, 70, 15);
    informationPane.add(emailLabel2);

    JLabel emailOutputLabel1 = new JLabel("zhe25@uclive.ac.nz");
    emailOutputLabel1.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    emailOutputLabel1.setBounds(266, 202, 144, 15);
    informationPane.add(emailOutputLabel1);

    JLabel emailOutputLabel2 = new JLabel("dcf29@uclive.ac.nz");
    emailOutputLabel2.setFont(new Font("Bradley Hand ITC", Font.BOLD, 15));
    emailOutputLabel2.setBounds(266, 91, 144, 15);
    informationPane.add(emailOutputLabel2);

    JButton informationPaneReturnButton = new JButton("Return");
    informationPaneReturnButton.setFocusable(false);
    informationPaneReturnButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setLayer(welcomePane);
      }
    });
    informationPaneReturnButton.setBounds(371, 331, 93, 32);
    informationPane.add(informationPaneReturnButton);

    setupPane = new JPanel();
    setupPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    layeredPane.setLayer(setupPane, 0);
    setupPane.setBackground(SystemColor.window);
    setupPane.setBounds(10, 11, 474, 374);
    layeredPane.add(setupPane);
    setupPane.setLayout(null);

    JLabel daysLabel = new JLabel("Days To Play:");
    daysLabel.setBounds(10, 14, 114, 14);
    setupPane.add(daysLabel);

    daysSpinner = new JSpinner();
    daysSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
    daysSpinner.setBounds(134, 11, 46, 20);
    setupPane.add(daysSpinner);

    JLabel farmerNameLabel = new JLabel("Farmer Name:");
    farmerNameLabel.setBounds(10, 39, 114, 14);
    setupPane.add(farmerNameLabel);

    farmerNameText = new JTextField();
    farmerNameText.setColumns(10);
    farmerNameText.setBounds(134, 36, 330, 20);
    setupPane.add(farmerNameText);

    JLabel ageInputLabel = new JLabel("Farmer Age:");
    ageInputLabel.setBounds(10, 64, 114, 14);
    setupPane.add(ageInputLabel);

    ageSpinner = new JSpinner();
    ageSpinner.setModel(new SpinnerNumberModel(18, 18, 80, 1));
    ageSpinner.setBounds(134, 61, 46, 20);
    setupPane.add(ageSpinner);

    JLabel farmTypeLabel = new JLabel("Farm Type:");
    farmTypeLabel.setBounds(10, 129, 114, 14);
    setupPane.add(farmTypeLabel);
    farmTypeSlider = new JSlider();
    farmTypeSlider.setMajorTickSpacing(1);
    farmTypeSlider.setPaintTicks(true);
    farmTypeSlider.setMaximum(3);
    farmTypeSlider.setBounds(134, 129, 330, 20);
    setupPane.add(farmTypeSlider);

    JLabel rainyLabel = new JLabel("Rainy");
    rainyLabel.setBounds(134, 155, 46, 14);
    setupPane.add(rainyLabel);

    JLabel temperateLabel = new JLabel("Temperate");
    temperateLabel.setBounds(222, 155, 62, 14);
    setupPane.add(temperateLabel);

    JLabel dryLabel = new JLabel("Dry");
    dryLabel.setBounds(345, 155, 39, 14);
    setupPane.add(dryLabel);

    JLabel desertLabel = new JLabel("Desert");
    desertLabel.setBounds(425, 155, 39, 14);
    setupPane.add(desertLabel);

    JLabel farmNameLabel = new JLabel("Farm Name:");
    farmNameLabel.setBounds(10, 101, 114, 14);
    setupPane.add(farmNameLabel);

    farmNameText = new JTextField();
    farmNameText.setColumns(10);
    farmNameText.setBounds(134, 98, 330, 20);
    setupPane.add(farmNameText);
    JButton startGameButton = new JButton("Start Game");
    startGameButton.setFocusable(false);
    startGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        GameEnvironment.setupFromWindow();
      }
    });
    startGameButton.setBounds(10, 180, 454, 30);
    setupPane.add(startGameButton);

    JSeparator setupSeparator = new JSeparator();
    setupSeparator.setBounds(10, 89, 454, 2);
    setupPane.add(setupSeparator);

    gamePane = new JPanel();
    gamePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    layeredPane.setLayer(gamePane, 0);
    gamePane.setBackground(Color.WHITE);
    gamePane.setBounds(10, 11, 474, 374);
    layeredPane.add(gamePane);
    gamePane.setLayout(null);

    JButton sleepButton = new JButton("Go To Bed");
    sleepButton.setFocusable(false);
    sleepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.endDay();
        GameEnvironment.refreshInfoPanel();
      }
    });
    sleepButton.setBounds(326, 333, 138, 30);
    gamePane.add(sleepButton);

    JButton visitStoreButton = new JButton("Visit Store");
    visitStoreButton.setFocusable(false);
    visitStoreButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshStorePane();
        setLayer(storePane);
      }
    });
    visitStoreButton.setFocusable(false);
    visitStoreButton.setBounds(10, 333, 306, 30);
    gamePane.add(visitStoreButton);

    JPanel limitedActionsPane = new JPanel();
    limitedActionsPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    limitedActionsPane.setBackground(SystemColor.window);
    limitedActionsPane.setBounds(168, 11, 158, 311);
    gamePane.add(limitedActionsPane);

    limitedActionsPane.setLayout(null);
    tendFarmButton = new JButton("Tend To The Farm");
    tendFarmButton.setFocusable(false);
    tendFarmButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.tendFarm();
      }
    });
    tendFarmButton.setBounds(10, 243, 138, 30);
    limitedActionsPane.add(tendFarmButton);

    harvestAnimalsButton = new JButton("Harvest Animal Products");
    harvestAnimalsButton.setFocusable(false);
    harvestAnimalsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.harvestAnimals();
      }
    });
    harvestAnimalsButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
    harvestAnimalsButton.setBounds(10, 202, 138, 30);
    limitedActionsPane.add(harvestAnimalsButton);

    playAnimalsButton = new JButton("Play With Animals");
    playAnimalsButton.setFocusable(false);
    playAnimalsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.playWithAnimals();
      }
    });
    playAnimalsButton.setBounds(10, 161, 138, 30);
    limitedActionsPane.add(playAnimalsButton);

    feedAnimalsButton = new JButton("Feed Animals");
    feedAnimalsButton.setFocusable(false);
    feedAnimalsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshFeedAnimalsPane();
        setLayer(feedAnimalsPane);
      }
    });
    feedAnimalsButton.setFocusable(false);
    feedAnimalsButton.setBounds(10, 120, 138, 30);
    limitedActionsPane.add(feedAnimalsButton);

    harvestCropsButton = new JButton("Harvest Crops");
    harvestCropsButton.setFocusable(false);
    harvestCropsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.harvestCrops();
      }
    });
    harvestCropsButton.setBounds(10, 79, 138, 30);
    limitedActionsPane.add(harvestCropsButton);

    tendCropsButton = new JButton("Tend To Crops");
    tendCropsButton.setFocusable(false);
    tendCropsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshTendCropsPane();
        setLayer(tendCropsPane);
      }
    });
    tendCropsButton.setFocusable(false);
    tendCropsButton.setBounds(10, 38, 138, 30);
    limitedActionsPane.add(tendCropsButton);

    actionsRemainingLabel = new JLabel("2 Actions Remaining:");
    actionsRemainingLabel.setBounds(12, 11, 136, 16);
    limitedActionsPane.add(actionsRemainingLabel);
    actionsRemainingLabel.setHorizontalAlignment(SwingConstants.CENTER);
    actionsRemainingLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));

    JPanel farmStatusPane = new JPanel();
    farmStatusPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    farmStatusPane.setBackground(SystemColor.control);
    farmStatusPane.setBounds(326, 11, 138, 311);
    gamePane.add(farmStatusPane);
    farmStatusPane.setLayout(null);

    statusLabel = new JLabel("Dan's Farm:");
    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    statusLabel.setBounds(10, 11, 118, 14);
    farmStatusPane.add(statusLabel);

    JLabel moneyLabel = new JLabel("Money:");
    moneyLabel.setBounds(10, 40, 70, 14);
    farmStatusPane.add(moneyLabel);

    moneyOutputLabel = new JLabel("$1000");
    moneyOutputLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    moneyOutputLabel.setBounds(78, 40, 50, 14);
    farmStatusPane.add(moneyOutputLabel);

    JLabel nameLabel = new JLabel("Farmer Name:");
    nameLabel.setBounds(10, 65, 70, 14);
    farmStatusPane.add(nameLabel);

    nameOutputLabel = new JLabel("Dan");
    nameOutputLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    nameOutputLabel.setBounds(78, 65, 50, 14);
    farmStatusPane.add(nameOutputLabel);

    JLabel ageLabel = new JLabel("Farmer Age:");
    ageLabel.setBounds(10, 90, 70, 14);
    farmStatusPane.add(ageLabel);

    ageOutputLabel = new JLabel("20");
    ageOutputLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    ageOutputLabel.setBounds(78, 90, 50, 14);
    farmStatusPane.add(ageOutputLabel);

    JLabel farmTypeStatusLabel = new JLabel("Farm Type:");
    farmTypeStatusLabel.setBounds(10, 115, 70, 14);
    farmStatusPane.add(farmTypeStatusLabel);

    farmTypeOutputLabel = new JLabel("Temperate");
    farmTypeOutputLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    farmTypeOutputLabel.setBounds(68, 115, 60, 14);
    farmStatusPane.add(farmTypeOutputLabel);

    JLabel dayStatusLabel = new JLabel("Current Day:");
    dayStatusLabel.setBounds(10, 286, 70, 14);
    farmStatusPane.add(dayStatusLabel);

    dayOutputLabel = new JLabel("1");
    dayOutputLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    dayOutputLabel.setBounds(78, 286, 50, 14);
    farmStatusPane.add(dayOutputLabel);

    tidinessOutputText = new JTextArea();
    tidinessOutputText.setWrapStyleWord(true);
    tidinessOutputText.setBackground(SystemColor.control);
    tidinessOutputText.setFont(new Font("Tahoma", Font.PLAIN, 11));
    tidinessOutputText.setLineWrap(true);
    tidinessOutputText.setText("Your farm is incredibly tidy");
    tidinessOutputText.setEditable(false);
    tidinessOutputText.setBounds(10, 140, 118, 135);
    farmStatusPane.add(tidinessOutputText);

    JPanel freeActionsPane = new JPanel();
    freeActionsPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    freeActionsPane.setBackground(SystemColor.window);
    freeActionsPane.setBounds(10, 11, 158, 311);
    gamePane.add(freeActionsPane);
    freeActionsPane.setLayout(null);

    JButton viewAnimalsButton = new JButton("View Animals");
    viewAnimalsButton.setFocusable(false);
    viewAnimalsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshAnimalPane();
        setLayer(animalPane);
      }
    });
    viewAnimalsButton.setBounds(10, 38, 138, 30);
    freeActionsPane.add(viewAnimalsButton);

    JButton viewCropsButton = new JButton("View Crops");
    viewCropsButton.setFocusable(false);
    viewCropsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshCropPane();
        setLayer(cropPane);
      }
    });
    viewCropsButton.setBounds(10, 79, 138, 30);
    freeActionsPane.add(viewCropsButton);

    JButton viewInventoryButton = new JButton("View Inventory");
    viewInventoryButton.setFocusable(false);
    viewInventoryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshInventoryPane();
        setLayer(inventoryPane);
      }
    });
    viewInventoryButton.setBounds(10, 120, 138, 30);
    freeActionsPane.add(viewInventoryButton);

    JButton plantCropsButton = new JButton("Plant Crops");
    plantCropsButton.setFocusable(false);
    plantCropsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.refreshPlantCropsPane();
        setLayer(plantCropsPane);
      }
    });
    plantCropsButton.setBounds(10, 161, 138, 30);
    freeActionsPane.add(plantCropsButton);

    JLabel freeActionsLabel = new JLabel("Free Actions:");
    freeActionsLabel.setBounds(12, 11, 136, 16);
    freeActionsPane.add(freeActionsLabel);
    freeActionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    freeActionsLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));

    animalPane = new JPanel();
    layeredPane.setLayer(animalPane, 0);
    animalPane.setLayout(null);
    animalPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    animalPane.setBackground(Color.WHITE);
    animalPane.setBounds(10, 11, 474, 374);
    layeredPane.add(animalPane);

    JButton animalPaneReturnButton = new JButton("Return");
    animalPaneReturnButton.setFocusable(false);
    animalPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    animalPaneReturnButton.setBounds(326, 333, 138, 30);
    animalPane.add(animalPaneReturnButton);

    JScrollPane animalPaneScrollPane = new JScrollPane();
    animalPaneScrollPane.setBounds(10, 11, 454, 311);
    animalPane.add(animalPaneScrollPane);

    animalPaneInfoPane = new JPanel();
    animalPaneScrollPane.setViewportView(animalPaneInfoPane);
    animalPaneInfoPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    animalPaneInfoPane.setBackground(Color.WHITE);
    GridBagLayout animalPaneInfoPaneGbl = new GridBagLayout();
    animalPaneInfoPaneGbl.columnWidths = new int[] { 100, 70 };
    animalPaneInfoPaneGbl.rowHeights = new int[] { 30 };
    animalPaneInfoPaneGbl.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
    animalPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0, 0.0 };
    animalPaneInfoPane.setLayout(animalPaneInfoPaneGbl);

    JLabel animalPaneTitleLabel = new JLabel("Animals:");
    animalPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    animalPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints animalPaneTitleLabelGbc = new GridBagConstraints();
    animalPaneTitleLabelGbc.anchor = GridBagConstraints.WEST;
    animalPaneTitleLabelGbc.insets = new Insets(0, 5, 5, 0);
    animalPaneTitleLabelGbc.gridx = 0;
    animalPaneTitleLabelGbc.gridy = 0;
    animalPaneInfoPane.add(animalPaneTitleLabel, animalPaneTitleLabelGbc);

    JLabel animalHeaderLabel = new JLabel("Chickens:");
    animalHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
    animalHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints animalHeaderLabelGbc = new GridBagConstraints();
    animalHeaderLabelGbc.anchor = GridBagConstraints.WEST;
    animalHeaderLabelGbc.insets = new Insets(0, 5, 5, 0);
    animalHeaderLabelGbc.gridx = 0;
    animalHeaderLabelGbc.gridy = 1;
    animalPaneInfoPane.add(animalHeaderLabel, animalHeaderLabelGbc);

    JLabel animalLabel = new JLabel(
        "Chicken - Hunger level: FULL, Happiness level: HAPPY, Health: HEALTHY");
    animalLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
    GridBagConstraints animalLabelGbc = new GridBagConstraints();
    animalLabelGbc.anchor = GridBagConstraints.WEST;
    animalLabelGbc.gridwidth = 5;
    animalLabelGbc.insets = new Insets(0, 5, 5, 0);
    animalLabelGbc.gridx = 0;
    animalLabelGbc.gridy = 2;
    animalPaneInfoPane.add(animalLabel, animalLabelGbc);

    cropPane = new JPanel();
    layeredPane.setLayer(cropPane, 0);
    cropPane.setLayout(null);
    cropPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    cropPane.setBackground(Color.WHITE);
    cropPane.setBounds(10, 11, 474, 374);
    layeredPane.add(cropPane);

    JButton cropPaneReturnButton = new JButton("Return");
    cropPaneReturnButton.setFocusable(false);
    cropPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    cropPaneReturnButton.setBounds(326, 333, 138, 30);
    cropPane.add(cropPaneReturnButton);

    JScrollPane cropPaneScrollPane = new JScrollPane();
    cropPaneScrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    cropPaneScrollPane.setBounds(10, 11, 454, 311);
    cropPane.add(cropPaneScrollPane);

    cropPaneInfoPane = new JPanel();
    cropPaneScrollPane.setViewportView(cropPaneInfoPane);
    cropPaneInfoPane.setBackground(Color.WHITE);
    GridBagLayout cropPaneInfoPaneGbl = new GridBagLayout();
    cropPaneInfoPaneGbl.rowHeights = new int[] { 30 };
    cropPaneInfoPaneGbl.columnWidths = new int[] { 100, 70 };
    cropPaneInfoPaneGbl.columnWeights = new double[] { 0.0, 0.0 };
    cropPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0, 0.0 };
    cropPaneInfoPane.setLayout(cropPaneInfoPaneGbl);

    JLabel cropPaneTitleLabel = new JLabel("Crops:");
    cropPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    cropPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints cropPaneTitleLabelGbc = new GridBagConstraints();
    cropPaneTitleLabelGbc.anchor = GridBagConstraints.WEST;
    cropPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
    cropPaneTitleLabelGbc.gridx = 0;
    cropPaneTitleLabelGbc.gridy = 0;
    cropPaneInfoPane.add(cropPaneTitleLabel, cropPaneTitleLabelGbc);

    JLabel cropHeaderLabel = new JLabel("Watermelon:");
    cropHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
    cropHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints cropHeaderLabelGbc = new GridBagConstraints();
    cropHeaderLabelGbc.anchor = GridBagConstraints.WEST;
    cropHeaderLabelGbc.insets = new Insets(0, 0, 5, 5);
    cropHeaderLabelGbc.gridx = 0;
    cropHeaderLabelGbc.gridy = 1;
    cropPaneInfoPane.add(cropHeaderLabel, cropHeaderLabelGbc);

    GridBagConstraints cropLabelGbc = new GridBagConstraints();
    cropLabelGbc.insets = new Insets(0, 0, 5, 0);
    cropLabelGbc.anchor = GridBagConstraints.WEST;
    cropLabelGbc.gridwidth = 5;
    cropLabelGbc.gridx = 0;
    cropLabelGbc.gridy = 2;
    JLabel cropLabel = new JLabel(
        "Watermelon - Days until dehydrated: 2, Days until harvestable: 2, "
        + "Health: HEALTHY, Fertilized: false");
    cropLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
    cropPaneInfoPane.add(cropLabel, cropLabelGbc);

    inventoryPane = new JPanel();
    layeredPane.setLayer(inventoryPane, 0);
    inventoryPane.setLayout(null);
    inventoryPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    inventoryPane.setBackground(Color.WHITE);
    inventoryPane.setBounds(10, 11, 474, 374);
    layeredPane.add(inventoryPane);

    JButton inventoryPaneReturnButton = new JButton("Return");
    inventoryPaneReturnButton.setFocusable(false);
    inventoryPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    inventoryPaneReturnButton.setBounds(326, 333, 138, 30);
    inventoryPane.add(inventoryPaneReturnButton);

    JScrollPane inventoryPaneInfoScrollPane = new JScrollPane();
    inventoryPaneInfoScrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    inventoryPaneInfoScrollPane
        .setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    inventoryPaneInfoScrollPane.setBounds(77, 11, 319, 311);
    inventoryPane.add(inventoryPaneInfoScrollPane);

    inventoryPaneInfoPane = new JPanel();
    inventoryPaneInfoPane.setBackground(Color.WHITE);
    inventoryPaneInfoScrollPane.setViewportView(inventoryPaneInfoPane);
    GridBagLayout inventoryPaneInfoPaneGbl = new GridBagLayout();
    inventoryPaneInfoPaneGbl.rowHeights = new int[] { 30 };
    inventoryPaneInfoPaneGbl.columnWidths = new int[] { 100, 70 };
    inventoryPaneInfoPaneGbl.columnWeights = new double[] { 0.0, 0.0 };
    inventoryPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0 };
    inventoryPaneInfoPane.setLayout(inventoryPaneInfoPaneGbl);

    JLabel inventoryPaneTitleLabel = new JLabel("Inventory");
    inventoryPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    inventoryPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints inventoryPaneTitleLabelGbc = new GridBagConstraints();
    inventoryPaneTitleLabelGbc.insets = new Insets(0, 0, 0, 5);
    inventoryPaneTitleLabelGbc.gridx = 0;
    inventoryPaneTitleLabelGbc.gridy = 0;
    inventoryPaneInfoPane.add(inventoryPaneTitleLabel, inventoryPaneTitleLabelGbc);

    JLabel inventoryPaneAmountLabel = new JLabel("Amount:");
    inventoryPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    inventoryPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints inventoryPaneAmountLabelGbc = new GridBagConstraints();
    inventoryPaneAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
    inventoryPaneAmountLabelGbc.gridx = 1;
    inventoryPaneAmountLabelGbc.gridy = 0;
    inventoryPaneInfoPane.add(inventoryPaneAmountLabel, inventoryPaneAmountLabelGbc);

    GridBagConstraints itemLabelGbc = new GridBagConstraints();
    itemLabelGbc.anchor = GridBagConstraints.WEST;
    itemLabelGbc.insets = new Insets(0, 10, 0, 5);
    itemLabelGbc.gridx = 0;
    itemLabelGbc.gridy = 1;
    JLabel itemLabel = new JLabel("Cactus Seeds");
    inventoryPaneInfoPane.add(itemLabel, itemLabelGbc);

    GridBagConstraints itemAmountLabelGbc = new GridBagConstraints();
    itemAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
    itemAmountLabelGbc.gridx = 1;
    itemAmountLabelGbc.gridy = 1;
    JLabel itemAmountLabel = new JLabel("4");
    inventoryPaneInfoPane.add(itemAmountLabel, itemAmountLabelGbc);

    plantCropsPane = new JPanel();
    layeredPane.setLayer(plantCropsPane, 0);
    plantCropsPane.setLayout(null);
    plantCropsPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    plantCropsPane.setBackground(Color.WHITE);
    plantCropsPane.setBounds(10, 11, 474, 374);
    layeredPane.add(plantCropsPane);

    JButton plantCropsPaneReturnButton = new JButton("Return");
    plantCropsPaneReturnButton.setFocusable(false);
    plantCropsPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    plantCropsPaneReturnButton.setBounds(326, 333, 138, 30);
    plantCropsPane.add(plantCropsPaneReturnButton);

    JScrollPane plantCropsPaneInfoScrollPane = new JScrollPane();
    plantCropsPaneInfoScrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    plantCropsPaneInfoScrollPane
        .setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    plantCropsPaneInfoScrollPane.setBounds(77, 11, 319, 311);
    plantCropsPane.add(plantCropsPaneInfoScrollPane);

    plantCropsPaneInfoPane = new JPanel();
    plantCropsPaneInfoPane.setBorder(null);
    plantCropsPaneInfoScrollPane.setViewportView(plantCropsPaneInfoPane);
    plantCropsPaneInfoPane.setBackground(Color.WHITE);
    GridBagLayout plantCropsPaneInfoPaneGbl = new GridBagLayout();
    plantCropsPaneInfoPaneGbl.columnWidths = new int[] { 100, 70 };
    plantCropsPaneInfoPaneGbl.columnWeights = new double[] { 0.0 };
    plantCropsPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0 };
    plantCropsPaneInfoPane.setLayout(plantCropsPaneInfoPaneGbl);

    JLabel plantCropsPaneTitleLabel = new JLabel("Crops:");
    plantCropsPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    plantCropsPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints plantCropsPaneTitleLabelGbc = new GridBagConstraints();
    plantCropsPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
    plantCropsPaneTitleLabelGbc.gridx = 0;
    plantCropsPaneTitleLabelGbc.gridy = 0;
    plantCropsPaneInfoPane.add(plantCropsPaneTitleLabel, plantCropsPaneTitleLabelGbc);

    JLabel plantCropsPaneAmountLabel = new JLabel("Amount:");
    plantCropsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    plantCropsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints plantCropsPaneAmountLabelGbc = new GridBagConstraints();
    plantCropsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
    plantCropsPaneAmountLabelGbc.gridx = 1;
    plantCropsPaneAmountLabelGbc.gridy = 0;
    plantCropsPaneInfoPane.add(plantCropsPaneAmountLabel, plantCropsPaneAmountLabelGbc);

    JButton plantButton = new JButton("Plant");
    plantButton.setFocusable(false);
    plantButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

      }
    });
    GridBagConstraints plantButtonGbc = new GridBagConstraints();
    plantButtonGbc.insets = new Insets(0, 0, 5, 0);
    plantButtonGbc.gridx = 2;
    plantButtonGbc.gridy = 1;
    plantCropsPaneInfoPane.add(plantButton, plantButtonGbc);

    tendCropsPane = new JPanel();
    layeredPane.setLayer(tendCropsPane, 0);
    tendCropsPane.setLayout(null);
    tendCropsPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    tendCropsPane.setBackground(Color.WHITE);
    tendCropsPane.setBounds(10, 11, 474, 374);
    layeredPane.add(tendCropsPane);

    JButton tendCropsPaneReturnButton = new JButton("Return");
    tendCropsPaneReturnButton.setFocusable(false);
    tendCropsPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    tendCropsPaneReturnButton.setFocusable(false);
    tendCropsPaneReturnButton.setBounds(326, 333, 138, 30);
    tendCropsPane.add(tendCropsPaneReturnButton);

    JScrollPane tendCropsPaneInfoScrollPane = new JScrollPane();
    tendCropsPaneInfoScrollPane
        .setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    tendCropsPaneInfoScrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    tendCropsPaneInfoScrollPane.setBounds(10, 11, 454, 311);
    tendCropsPane.add(tendCropsPaneInfoScrollPane);

    tendCropsPaneInfoPane = new JPanel();
    tendCropsPaneInfoPane.setBackground(Color.WHITE);
    tendCropsPaneInfoScrollPane.setViewportView(tendCropsPaneInfoPane);
    GridBagLayout tendCropsPaneInfoPaneGbl = new GridBagLayout();
    tendCropsPaneInfoPaneGbl.columnWidths = new int[] { 70, 70, 70 };
    tendCropsPaneInfoPaneGbl.rowHeights = new int[] { 30 };
    tendCropsPaneInfoPaneGbl.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
    tendCropsPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0 };
    tendCropsPaneInfoPane.setLayout(tendCropsPaneInfoPaneGbl);

    JLabel tendCropsPaneTitleLabel = new JLabel("Crops:");
    tendCropsPaneTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    tendCropsPaneTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints tendCropsPaneTitleLabelGbc = new GridBagConstraints();
    tendCropsPaneTitleLabelGbc.insets = new Insets(0, 0, 5, 5);
    tendCropsPaneTitleLabelGbc.gridx = 0;
    tendCropsPaneTitleLabelGbc.gridy = 0;
    tendCropsPaneInfoPane.add(tendCropsPaneTitleLabel, tendCropsPaneTitleLabelGbc);

    JLabel tendCropsPaneAmountLabel = new JLabel("Amount:");
    tendCropsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    tendCropsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints tendCropsPaneAmountLabelGbc = new GridBagConstraints();
    tendCropsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
    tendCropsPaneAmountLabelGbc.gridx = 1;
    tendCropsPaneAmountLabelGbc.gridy = 0;
    tendCropsPaneInfoPane.add(tendCropsPaneAmountLabel, tendCropsPaneAmountLabelGbc);

    JLabel tendCropsPaneFertilizingAmountLabel = new JLabel("Need Fertilizing:");
    tendCropsPaneFertilizingAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    tendCropsPaneFertilizingAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints tendCropsPaneFertilizingAmountLabelGbc = new GridBagConstraints();
    tendCropsPaneFertilizingAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
    tendCropsPaneFertilizingAmountLabelGbc.gridx = 2;
    tendCropsPaneFertilizingAmountLabelGbc.gridy = 0;
    tendCropsPaneInfoPane.add(tendCropsPaneFertilizingAmountLabel,
        tendCropsPaneFertilizingAmountLabelGbc);

    GridBagConstraints tendCropLabelGbc = new GridBagConstraints();
    tendCropLabelGbc.insets = new Insets(0, 0, 0, 5);
    tendCropLabelGbc.gridx = 0;
    tendCropLabelGbc.gridy = 1;
    JLabel tendCropLabel = new JLabel("Watermelon");
    tendCropsPaneInfoPane.add(tendCropLabel, tendCropLabelGbc);

    GridBagConstraints tendCropAmountLabelGbc = new GridBagConstraints();
    tendCropAmountLabelGbc.insets = new Insets(0, 0, 0, 5);
    tendCropAmountLabelGbc.gridx = 1;
    tendCropAmountLabelGbc.gridy = 1;
    JLabel tendCropAmountLabel = new JLabel("4");
    tendCropsPaneInfoPane.add(tendCropAmountLabel, tendCropAmountLabelGbc);

    GridBagConstraints tendCropFertilizeLabelGbc = new GridBagConstraints();
    tendCropFertilizeLabelGbc.insets = new Insets(0, 0, 0, 5);
    tendCropFertilizeLabelGbc.gridx = 2;
    tendCropFertilizeLabelGbc.gridy = 1;
    JLabel tendCropFertilizeLabel = new JLabel("4");
    tendCropsPaneInfoPane.add(tendCropFertilizeLabel, tendCropFertilizeLabelGbc);

    JButton tendCropWaterButton = new JButton("Water");
    tendCropWaterButton.setFocusable(false);
    GridBagConstraints tendCropWaterButtonGbc = new GridBagConstraints();
    tendCropWaterButtonGbc.insets = new Insets(0, 0, 0, 5);
    tendCropWaterButtonGbc.gridx = 3;
    tendCropWaterButtonGbc.gridy = 1;
    tendCropsPaneInfoPane.add(tendCropWaterButton, tendCropWaterButtonGbc);

    JButton tendCropFertilizeButton = new JButton("Fertilize");
    tendCropFertilizeButton.setFocusable(false);
    GridBagConstraints tendCropFertilizeButtonGbc = new GridBagConstraints();
    tendCropFertilizeButtonGbc.gridx = 4;
    tendCropFertilizeButtonGbc.gridy = 1;
    tendCropsPaneInfoPane.add(tendCropFertilizeButton, tendCropFertilizeButtonGbc);

    feedAnimalsPane = new JPanel();
    layeredPane.setLayer(feedAnimalsPane, 0);
    feedAnimalsPane.setLayout(null);
    feedAnimalsPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    feedAnimalsPane.setBackground(Color.WHITE);
    feedAnimalsPane.setBounds(10, 11, 474, 374);
    layeredPane.add(feedAnimalsPane);

    JButton feedAnimalsPaneReturnButton = new JButton("Return");
    feedAnimalsPaneReturnButton.setFocusable(false);
    feedAnimalsPaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    feedAnimalsPaneReturnButton.setFocusable(false);
    feedAnimalsPaneReturnButton.setBounds(326, 333, 138, 30);
    feedAnimalsPane.add(feedAnimalsPaneReturnButton);

    JScrollPane feedAnimalsPaneInfoScrollPane = new JScrollPane();
    feedAnimalsPaneInfoScrollPane
        .setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    feedAnimalsPaneInfoScrollPane.setBounds(10, 11, 454, 311);
    feedAnimalsPane.add(feedAnimalsPaneInfoScrollPane);

    feedAnimalsPaneInfoPane = new JPanel();
    feedAnimalsPaneInfoPane.setBackground(Color.WHITE);
    feedAnimalsPaneInfoScrollPane.setViewportView(feedAnimalsPaneInfoPane);
    GridBagLayout feedAnimalsPaneInfoPaneGbl = new GridBagLayout();
    feedAnimalsPaneInfoPaneGbl.columnWidths = new int[] { 70, 70, 70 };
    feedAnimalsPaneInfoPaneGbl.rowHeights = new int[] { 30 };
    feedAnimalsPaneInfoPaneGbl.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
    feedAnimalsPaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0 };
    feedAnimalsPaneInfoPane.setLayout(feedAnimalsPaneInfoPaneGbl);

    JLabel feedAnimalsPaneAnimalLabel = new JLabel("Animal:");
    feedAnimalsPaneAnimalLabel.setHorizontalAlignment(SwingConstants.CENTER);
    feedAnimalsPaneAnimalLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints feedAnimalsPaneAnimalLabelGbc = new GridBagConstraints();
    feedAnimalsPaneAnimalLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalsPaneAnimalLabelGbc.gridx = 0;
    feedAnimalsPaneAnimalLabelGbc.gridy = 0;
    feedAnimalsPaneInfoPane.add(feedAnimalsPaneAnimalLabel, feedAnimalsPaneAnimalLabelGbc);

    JLabel feedAnimalsPaneAmountLabel = new JLabel("Amount:");
    feedAnimalsPaneAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    feedAnimalsPaneAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints feedAnimalsPaneAmountLabelGbc = new GridBagConstraints();
    feedAnimalsPaneAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalsPaneAmountLabelGbc.gridx = 1;
    feedAnimalsPaneAmountLabelGbc.gridy = 0;
    feedAnimalsPaneInfoPane.add(feedAnimalsPaneAmountLabel, feedAnimalsPaneAmountLabelGbc);

    JLabel feedAnimalsPaneHungerLabel = new JLabel("Worst Hunger:");
    feedAnimalsPaneHungerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    feedAnimalsPaneHungerLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints feedAnimalsPaneHungerLabelGbc = new GridBagConstraints();
    feedAnimalsPaneHungerLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalsPaneHungerLabelGbc.gridx = 2;
    feedAnimalsPaneHungerLabelGbc.gridy = 0;
    feedAnimalsPaneInfoPane.add(feedAnimalsPaneHungerLabel, feedAnimalsPaneHungerLabelGbc);

    GridBagConstraints feedAnimalLabelGbc = new GridBagConstraints();
    feedAnimalLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalLabelGbc.gridx = 0;
    feedAnimalLabelGbc.gridy = 1;
    JLabel feedAnimalLabel = new JLabel("Watermelon");
    feedAnimalsPaneInfoPane.add(feedAnimalLabel, feedAnimalLabelGbc);

    GridBagConstraints feedAnimalAmountLabelGbc = new GridBagConstraints();
    feedAnimalAmountLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalAmountLabelGbc.gridx = 1;
    feedAnimalAmountLabelGbc.gridy = 1;
    JLabel feedAnimalAmountLabel = new JLabel("4");
    feedAnimalsPaneInfoPane.add(feedAnimalAmountLabel, feedAnimalAmountLabelGbc);

    GridBagConstraints feedAnimalHungerLabelGbc = new GridBagConstraints();
    feedAnimalHungerLabelGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalHungerLabelGbc.gridx = 2;
    feedAnimalHungerLabelGbc.gridy = 1;
    JLabel feedAnimalHungerLabel = new JLabel("STARVING");
    feedAnimalsPaneInfoPane.add(feedAnimalHungerLabel, feedAnimalHungerLabelGbc);

    JButton feedAnimalButton = new JButton("Feed");
    feedAnimalButton.setFocusable(false);
    GridBagConstraints feedAnimalButtonGbc = new GridBagConstraints();
    feedAnimalButtonGbc.insets = new Insets(0, 0, 5, 5);
    feedAnimalButtonGbc.gridx = 3;
    feedAnimalButtonGbc.gridy = 1;
    feedAnimalsPaneInfoPane.add(feedAnimalButton, feedAnimalButtonGbc);

    storePane = new JPanel();
    layeredPane.setLayer(storePane, 0);
    storePane.setLayout(null);
    storePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    storePane.setBackground(Color.WHITE);
    storePane.setBounds(10, 11, 474, 374);
    layeredPane.add(storePane);

    JButton storePaneSellButton = new JButton("Sell Products");
    storePaneSellButton.setFocusable(false);
    storePaneSellButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GameEnvironment.sellProducts();
      }
    });
    storePaneSellButton.setFocusable(false);
    storePaneSellButton.setBounds(10, 333, 138, 30);
    storePane.add(storePaneSellButton);

    JButton storePaneReturnButton = new JButton("Return");
    storePaneReturnButton.setFocusable(false);
    storePaneReturnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setLayer(gamePane);
      }
    });
    storePaneReturnButton.setFocusable(false);
    storePaneReturnButton.setBounds(326, 333, 138, 30);
    storePane.add(storePaneReturnButton);

    storePaneMoneyLabel = new JLabel("Money: $875");
    storePaneMoneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
    storePaneMoneyLabel.setBounds(182, 333, 109, 30);
    storePane.add(storePaneMoneyLabel);

    JScrollPane storePaneInfoScrollPane = new JScrollPane();
    storePaneInfoScrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    storePaneInfoScrollPane.setBounds(10, 11, 454, 311);
    storePane.add(storePaneInfoScrollPane);

    storePaneInfoPane = new JPanel();
    storePaneInfoPane.setBackground(Color.WHITE);
    storePaneInfoScrollPane.setViewportView(storePaneInfoPane);
    GridBagLayout storePaneInfoPaneGbl = new GridBagLayout();
    storePaneInfoPaneGbl.columnWidths = new int[] { 70, 70, 70 };
    storePaneInfoPaneGbl.columnWeights = new double[] { 0.0, 0.0, 0.0 };
    storePaneInfoPaneGbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
    storePaneInfoPane.setLayout(storePaneInfoPaneGbl);

    JLabel storePanePriceLabel = new JLabel("Price:");
    storePanePriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    storePanePriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints storePanePriceLabelGbc = new GridBagConstraints();
    storePanePriceLabelGbc.insets = new Insets(0, 0, 5, 5);
    storePanePriceLabelGbc.gridx = 1;
    storePanePriceLabelGbc.gridy = 0;
    storePaneInfoPane.add(storePanePriceLabel, storePanePriceLabelGbc);

    JLabel storePaneItemLabel = new JLabel("Items:");
    storePaneItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
    storePaneItemLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
    GridBagConstraints storePaneItemLabelGbc = new GridBagConstraints();
    storePaneItemLabelGbc.insets = new Insets(0, 0, 5, 5);
    storePaneItemLabelGbc.gridx = 0;
    storePaneItemLabelGbc.gridy = 4;
    storePaneInfoPane.add(storePaneItemLabel, storePaneItemLabelGbc);

    
    GridBagConstraints itemsLabelGbc = new GridBagConstraints();
    itemsLabelGbc.anchor = GridBagConstraints.WEST;
    itemsLabelGbc.insets = new Insets(0, 0, 0, 5);
    itemsLabelGbc.gridx = 0;
    itemsLabelGbc.gridy = 5;
    JLabel itemsLabel = new JLabel("Watermelon Seed");
    storePaneInfoPane.add(itemsLabel, itemsLabelGbc);

    
    GridBagConstraints priceLabelGbc = new GridBagConstraints();
    priceLabelGbc.insets = new Insets(0, 0, 0, 5);
    priceLabelGbc.gridx = 1;
    priceLabelGbc.gridy = 5;
    JLabel priceLabel = new JLabel("$5");
    storePaneInfoPane.add(priceLabel, priceLabelGbc);

    JButton purchaseButton = new JButton("Purchase");
    purchaseButton.setFocusable(false);
    GridBagConstraints purchaseButtonGbc = new GridBagConstraints();
    purchaseButtonGbc.gridx = 2;
    purchaseButtonGbc.gridy = 5;
    storePaneInfoPane.add(purchaseButton, purchaseButtonGbc);

    endPane = new JPanel();
    layeredPane.setLayer(endPane, 3);
    endPane.setLayout(null);
    endPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    endPane.setBackground(Color.WHITE);
    endPane.setBounds(10, 11, 474, 374);
    layeredPane.add(endPane);
    
    endDetailsLabel = new JLabel("Dan's Farm - 10 Days - $800 In Profit");
    endDetailsLabel.setBounds(10, 11, 454, 25);
    endPane.add(endDetailsLabel);
    endDetailsLabel.setHorizontalAlignment(SwingConstants.LEFT);
    endDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

    JLabel endLabel = new JLabel("Game Over");
    endLabel.setHorizontalAlignment(SwingConstants.CENTER);
    endLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    endLabel.setBounds(99, 87, 275, 25);
    endPane.add(endLabel);

    scoreLabel = new JLabel("\nScore: 13506");
    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    scoreLabel.setBounds(99, 123, 275, 25);
    endPane.add(scoreLabel);

    leaderboardLabel = new JLabel("<html>Leaderboard:\r\n<br>1. Dan - 100\r\n<br>2. Dan - 100");
    leaderboardLabel.setVerticalAlignment(SwingConstants.TOP);
    leaderboardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    leaderboardLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    leaderboardLabel.setBounds(99, 159, 275, 204);
    endPane.add(leaderboardLabel);

    JLabel endImageLabel = new JLabel("");
    endImageLabel
        .setIcon(new ImageIcon(GameWindow.class.getResource("/main/resources/Background1.jpg")));
    endImageLabel.setBounds(0, 0, 474, 374);
    endPane.add(endImageLabel);

    JPanel outputPane = new JPanel();
    outputPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    outputPane.setBackground(SystemColor.window);
    outputPane.setBounds(10, 396, 474, 64);
    farmingSimulatorFrame.getContentPane().add(outputPane);
    outputPane.setLayout(null);

    JScrollPane outputPaneScrollPane = new JScrollPane();
    outputPaneScrollPane.setViewportBorder(null);
    outputPaneScrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    outputPaneScrollPane.setBounds(10, 2, 462, 60);
    outputPane.add(outputPaneScrollPane);

    outputArea = new JTextArea();
    outputPaneScrollPane.setViewportView(outputArea);
    outputArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
    outputArea.setEditable(false);
  }

  /**
   * Sets the layer of all Components in layeredPane to 0, then sets the layer of
   * topPanel to 1 to bring it to the front of the frame.
   * 
   * @see GameWindow#getLayeredPane()
   * @param topPanel The JPanel to bring to front.
   */
  public void setLayer(JPanel topPanel) {
    for (Component panel : layeredPane.getComponents()) {
      if (panel.getName() != "Intercept") {
        layeredPane.setLayer(panel, 0);
      }
    }
    layeredPane.setLayer(topPanel, 2);
    GameEnvironment.refreshInfoPanel();
  }

  // Exposed Components:
  public JLabel getAgeOutputLabel() {
    return ageOutputLabel;
  }

  public JLabel getNameOutputLabel() {
    return nameOutputLabel;
  }

  public JLabel getDayOutputLabel() {
    return dayOutputLabel;
  }

  public JLabel getMoneyOutputLabel() {
    return moneyOutputLabel;
  }

  public JLabel getStatusLabel() {
    return statusLabel;
  }

  public JLabel getFarmTypeOutputLabel() {
    return farmTypeOutputLabel;
  }

  public JTextArea getTidinessOutputText() {
    return tidinessOutputText;
  }

  public JPanel getPlantCropsPaneInfoPane() {
    return plantCropsPaneInfoPane;
  }

  public JPanel getInventoryPaneInfoPane() {
    return inventoryPaneInfoPane;
  }

  public JPanel getCropPaneInfoPane() {
    return cropPaneInfoPane;
  }

  public JPanel getAnimalPaneInfoPane() {
    return animalPaneInfoPane;
  }

  public JLabel getActionsRemainingLabel() {
    return actionsRemainingLabel;
  }

  public JButton getPlayAnimalsButton() {
    return playAnimalsButton;
  }

  public JButton getHarvestCropsButton() {
    return harvestCropsButton;
  }

  public JButton getTendFarmButton() {
    return tendFarmButton;
  }

  public JButton getFeedAnimalsButton() {
    return feedAnimalsButton;
  }

  public JButton getTendCropsButton() {
    return tendCropsButton;
  }

  public JButton getHarvestAnimalsButton() {
    return harvestAnimalsButton;
  }

  public JPanel getTendCropsPaneInfoPane() {
    return tendCropsPaneInfoPane;
  }

  public JPanel getFeedAnimalsPaneInfoPane() {
    return feedAnimalsPaneInfoPane;
  }

  public JPanel getStorePaneInfoPane() {
    return storePaneInfoPane;
  }

  public JLabel getStorePaneMoneyLabel() {
    return storePaneMoneyLabel;
  }

  public JLabel getScoreLabel() {
    return scoreLabel;
  }

  public JLabel getLeaderboardLabel() {
    return leaderboardLabel;
  }

  public JSpinner getDaysSpinner() {
    return daysSpinner;
  }

  public JTextField getFarmerNameText() {
    return farmerNameText;
  }

  public JSpinner getAgeSpinner() {
    return ageSpinner;
  }

  public JTextField getFarmNameText() {
    return farmNameText;
  }

  public JSlider getFarmTypeSlider() {
    return farmTypeSlider;
  }

  public JPanel getEndPane() {
    return endPane;
  }

  public JPanel getGamePane() {
    return gamePane;
  }

  public JLayeredPane getLayeredPane() {
    return layeredPane;
  }

  public JPanel getWelcomePane() {
    return welcomePane;
  }

  public JTextArea getOutputArea() {
    return outputArea;
  }
  
  public JLabel getEndDetailsLabel() {
    return endDetailsLabel;
  }
}
