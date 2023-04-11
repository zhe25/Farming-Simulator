package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Reads and writes the highest scores to a file named scores.data. Makes use of
 * a static initializer to read the scores.data file and populate the scoreNames
 * and scores lists before other methods are processed.
 *
 * @author Daniel Felgate
 * @author He Zheng Jing Rui
 * @version 1.0, May 2020.
 */
public class ScoreRecorder {

  /**
   * The title of the file used to save scores.
   */
  private static final String SCOREFILE = "scores.data";
  /**
   * A list of names to go with scores.
   */
  public static ArrayList<String> scoreNames = new ArrayList<String>();
  /**
   * A list of scores.
   */
  public static ArrayList<Integer> scores = new ArrayList<Integer>();

  /**
   * Adds a new name and score to the scoreNames and scores lists before sorting
   * and saving the lists to the file.
   * 
   * @param name  The name to add.
   * @param score The score to add.
   */
  public static void addScore(String name, int score) {
    scoreNames.add(name);
    scores.add(score);
    sortScores();
    saveScores();
  }

  /**
   * Sort the scores from highest to lowest and removes the lowest scores until
   * only the top 5 are present.
   */
  public static void sortScores() {
    int[] highestIndexes = { -1, -1, -1, -1, -1 };

    for (int i = 0; i < highestIndexes.length; i++) {
      int highestIndex = -1;
      for (int j = 0; j < scores.size(); j++) {
        boolean alreadyIncluded = false;
        for (int num : highestIndexes) {
          if (j == num) {
            alreadyIncluded = true;
            break;

          }
        }
        if (!alreadyIncluded && (highestIndex == -1 || scores.get(j) > scores.get(highestIndex))) {
          highestIndex = j;
        }
      }
      if (highestIndex != -1) {
        highestIndexes[i] = highestIndex;
      } else {
        break;
      }
    }

    ArrayList<String> scoreNamesSorted = new ArrayList<String>();
    ArrayList<Integer> scoresSorted = new ArrayList<Integer>();

    for (int i = 0; i < highestIndexes.length; i++) {
      if (highestIndexes[i] != -1) {
        scoreNamesSorted.add(scoreNames.get(highestIndexes[i]));
        scoresSorted.add(scores.get(highestIndexes[i]));
      } else {
        break;
      }
    }

    scoreNames = scoreNamesSorted;
    scores = scoresSorted;
  }

  /**
   * Save the scores to a file titled by SCOREFILE.
   * 
   * @see ScoreRecorder#SCOREFILE
   */
  public static void saveScores() {

    try {
      File f = new File(SCOREFILE);
      if (f.exists()) {
        f.delete();
      }
      f.createNewFile();

      FileWriter writer = null;
      try {
        writer = new FileWriter(f);
        for (int i = 0; i < scores.size(); i++) {
          writer.write(scoreNames.get(i) + "," + scores.get(i) + "\n");
        }
      } catch (NumberFormatException | IOException e) {
        e.printStackTrace();
      } finally {
        writer.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Runs automatically before this class runs any other method.
  static {
    try {
      File f = new File(SCOREFILE);
      if (!f.exists()) {
        f.createNewFile();
      }

      BufferedReader br = null;
      try {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        boolean reading = true;
        while (reading) {
          String value = br.readLine();
          if (value != null && !"".equals(value)) {
            String[] vs = value.split(",");
            scoreNames.add(vs[0]);
            scores.add(Integer.parseInt(vs[1]));
          } else {
            reading = false;
          }
        }
      } catch (NumberFormatException | IOException e) {
        e.printStackTrace();
      } finally {
        br.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
