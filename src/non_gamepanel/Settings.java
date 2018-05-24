package non_gamepanel;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Settings {

    // This class is a singleton.
    private static Settings privateInstance = new Settings();
    private final int NUM_SETTINGS = 4;
    private int highScore;
    private int zenDifficulty;
    private int userSpeed;
    private Color userColor;
    private File settingsFile;
    private StringBuilder fileContents = new StringBuilder();

    /*
     * EXPECTED FILE FORMAT: Each value (all represented by numbers) is on a new line, ordered:
     * 1) highScore
     * 2) zenDifficulty (in range [1, 5])
     * 3) user speed (in range [1, 5])
     * 4) user color (int representing the RGB value)
     */


    private Settings() {
        if (privateInstance != null) {
            return;
        }

        settingsFile = new File(System.getProperty("user.home") + "/FallingRocks/local_settings.txt");

        if (settingsFile.exists()) {
            try {
                Scanner scanner = new Scanner(settingsFile);

                LinkedList<Integer> numsFound = new LinkedList<>();
                while (scanner.hasNextInt()) {
                    numsFound.add(scanner.nextInt());
                }

                if (numsFound.size() == NUM_SETTINGS) {
                    highScore = numsFound.get(0);
                    zenDifficulty = numsFound.get(1);
                    userSpeed = numsFound.get(2);
                    userColor = new Color(numsFound.get(3));
                } else {
                    // If some settings are missing, assume that file has been edited/ruined somehow.
                    // Execute the same procedure as if file doesn't exist.
                    highScore = 0;
                    zenDifficulty = 2;
                    userSpeed = 2;
                    userColor = Color.WHITE;

                    writeSettingsToFile();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // File doesn't exist, create it
            try {
                if (!settingsFile.getParentFile().exists()) {
                    settingsFile.getParentFile().mkdirs(); // Make directory
                }
                settingsFile.createNewFile();

                highScore = 0;
                zenDifficulty = 2;
                userSpeed = 2;
                userColor = Color.WHITE;

                writeSettingsToFile();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void writeSettingsToFile() {
        try {
            // Clear fileContents and replace with updated values
            fileContents = new StringBuilder();
            fileContents.append(highScore);
            fileContents.append('\n');
            fileContents.append(zenDifficulty);
            fileContents.append('\n');
            fileContents.append(userSpeed);
            fileContents.append('\n');
            fileContents.append(userColor.getRGB());
            fileContents.append('\n');

            PrintWriter pw = new PrintWriter(settingsFile.getPath());
            pw.print(fileContents);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static synchronized Settings getInstance() {
        return privateInstance;
    }


    public int getHighScore() {
        return highScore;
    }


    public void setHighScore(int highScore) {
        this.highScore = highScore;
        writeSettingsToFile();
    }


    public int getZenDifficulty() {
        return zenDifficulty;
    }


    public void setZenDifficulty(int zenDifficulty) {
        this.zenDifficulty = zenDifficulty;
        writeSettingsToFile();
    }


    public int getUserSpeed() {
        return userSpeed;
    }


    public void setUserSpeed(int userSpeed) {
        this.userSpeed = userSpeed;
        writeSettingsToFile();
    }


    public Color getUserColor() {
        return userColor;
    }


    public void setUserColor(Color userColor) {
        this.userColor = userColor;
        writeSettingsToFile();
    }

}
