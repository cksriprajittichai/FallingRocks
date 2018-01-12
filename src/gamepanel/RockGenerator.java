package gamepanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import non_gamepanel.GameManager;

public class RockGenerator {

	// Max bounds are inclusive
	private static float STARTING_VEL_MAX;
	private static float STARTING_VEL_MIN;
	private static int MIN_WIDTH;
	private static int MIN_HEIGHT;
	private static int MAX_WIDTH;
	private static int MAX_HEIGHT;
	private ArrayList<Color> possibleColors;
	private Random random;

	// 1 is Easy, 2 is Medium, 3 is Hard
	public RockGenerator() {
		random = new Random();

		possibleColors = new ArrayList<Color>();
		possibleColors.add(Color.BLUE);
		possibleColors.add(Color.BLUE);
		possibleColors.add(Color.YELLOW);
		possibleColors.add(Color.YELLOW);
		possibleColors.add(Color.GRAY);
		possibleColors.add(Color.LIGHT_GRAY);
		// possibleColors.add(Color.DARK_GRAY);
		possibleColors.add(Color.GREEN);
		possibleColors.add(Color.GREEN);
		possibleColors.add(Color.MAGENTA);
		possibleColors.add(Color.MAGENTA);
		possibleColors.add(Color.ORANGE);
		possibleColors.add(Color.ORANGE);
		possibleColors.add(Color.PINK);
		possibleColors.add(Color.PINK);
		possibleColors.add(Color.RED);
		possibleColors.add(Color.RED);
		possibleColors.add(Color.WHITE);
		possibleColors.add(Color.WHITE);

		if (GameManager.DIFFICULTY.compareTo("Easy") == 0) {
			MIN_WIDTH = 20;
			MIN_HEIGHT = 20;
			MAX_WIDTH = 30;
			MAX_HEIGHT = 30;
			STARTING_VEL_MIN = 0;
			STARTING_VEL_MAX = 1;
		} else if (GameManager.DIFFICULTY.compareTo("Medium") == 0) {
			MIN_WIDTH = 15;
			MIN_HEIGHT = 15;
			MAX_WIDTH = 50;
			MAX_HEIGHT = 50;
			STARTING_VEL_MIN = 1;
			STARTING_VEL_MAX = 3;
		} else if (GameManager.DIFFICULTY.compareTo("Hard") == 0) {
			MIN_WIDTH = 10;
			MIN_HEIGHT = 5;
			MAX_WIDTH = 80;
			MAX_HEIGHT = 80;
			STARTING_VEL_MIN = 2;
			STARTING_VEL_MAX = 5;
		}
	}

	public Rock next() {
		// The bound parameter in Random.nextInt() is excluded from possible return values.
		int width = random.nextInt(MAX_WIDTH - MIN_WIDTH + 1) + MIN_WIDTH;
		int height = random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;

		// Generate xPos of rocks to be any value that will leave any portion of the rock in the user's playable range.
		// So generate rocks that have xPos left of the playable range, but have portions of their right-width in the
		// screen. Also generate rocks that have portions of their left-width in the playable range of the screen, that
		// are cutoff by the right edge of the playable screen.
		// Example: If the rock has width 10, the possible xPos that it can spawn in are [-9, 799].
		return new Rock(random.nextInt(GamePanel.WIDTH + width - 2) - (width - 1), -height + 1, width, height,
				random.nextInt((int) (STARTING_VEL_MAX - STARTING_VEL_MIN)) + STARTING_VEL_MIN + random.nextFloat(),
				possibleColors.get(random.nextInt(possibleColors.size())));
	}

}
