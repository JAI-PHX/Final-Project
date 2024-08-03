import java.io.*;

import java.util.*;

public class HighScoreManager {

    //Use the highscores.txt file to store user names and scores
    private static final String HIGH_SCORE_FILE = "highscores.txt";

    private Map<String, Integer> highScores;

    public HighScoreManager() {

        highScores = new HashMap<>();

        loadHighScores();
    }

    // Create file if not found existing file
    private void loadHighScores() {

        try (BufferedReader br = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":");

                if (parts.length == 2) {

                    highScores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {

            System.out.println("No high score file found. Starting a new one.");
        }
    }

    //Add users earned score to the highscores.txt file
    public void addScore(String player, int score) {

        highScores.put(player, Math.max(highScores.getOrDefault(player, 0), score));

        saveHighScores();
    }

    private void saveHighScores() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {

            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {

                bw.write(entry.getKey() + ":" + entry.getValue());

                bw.newLine();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public Map<String, Integer> getHighScores() {
        return highScores;
    }
}
