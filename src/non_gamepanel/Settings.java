package non_gamepanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings {

	private boolean isFirstPlay;
	private int highScore;

	private File settingsFile;
	private StringBuilder contents;

	/**
	 * EXPECTED FILE FORMAT: Each value (all lower case) is on a new line, in the order 1) highScore. Boolean
	 * isFirstPlay is determined based on the existence of a highScore at the start of the file. If no highScore exists,
	 * then it is the first play. No additional words besides the string-represented values of these variables.
	 * 
	 * A JAR file is a Java Archive file and shouldn't be changed. So write local settings to a directory in the user's
	 * home folder.
	 */

	public Settings() {

		settingsFile = new File(System.getProperty("user.home") + "/FallingRocks/local_settings.txt");
		contents = new StringBuilder();

		Scanner scanner;

		if (settingsFile.exists()) {
			try {
				scanner = new Scanner(settingsFile);

				if (scanner.hasNextInt()) {
					setHighScore(scanner.nextInt());
					setFirstPlay(false);
				} else {
					setHighScore(0);
					setFirstPlay(true);

					contents.append(highScore + "\n");
					PrintWriter pw = new PrintWriter(settingsFile);
					pw.print(contents);
					pw.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else { // File doesn't exist, create it
			try {
				if (settingsFile.getParentFile().exists() == false) {
					settingsFile.getParentFile().mkdirs(); // Make directory
				}
				settingsFile.createNewFile();

				setHighScore(0);
				setFirstPlay(true);

				contents.append(highScore + "\n");
				PrintWriter pw = new PrintWriter(settingsFile);
				pw.print(contents);
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isFirstPlay() {
		return isFirstPlay;
	}

	public void setFirstPlay(boolean isFirstPlay) {
		this.isFirstPlay = isFirstPlay;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;

		try {
			contents = new StringBuilder(highScore + "\n"); // Clear contents and replace with new highScore
			PrintWriter pw = new PrintWriter(settingsFile.getPath());
			pw.print(contents);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
