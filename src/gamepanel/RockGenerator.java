package gamepanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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

	public RockGenerator(GamePanel gamePanel) {
		random = new Random();

		possibleColors = new ArrayList<Color>();
		possibleColors.add(Color.BLUE);
		possibleColors.add(Color.BLUE);
		possibleColors.add(Color.YELLOW);
		possibleColors.add(Color.GREEN);
		possibleColors.add(Color.GREEN);
		possibleColors.add(Color.MAGENTA);
		possibleColors.add(Color.MAGENTA);
		possibleColors.add(Color.ORANGE);
		possibleColors.add(Color.RED);
		possibleColors.add(Color.RED);
		possibleColors.add(Color.WHITE);

		if (gamePanel.getMode().equals("ZEN")) {
			MIN_WIDTH = 20;
			MIN_HEIGHT = 20;
			MAX_WIDTH = 50;
			MAX_HEIGHT = 50;
			STARTING_VEL_MIN = 2;
			STARTING_VEL_MAX = 3;
		} else if (gamePanel.getMode().equals("RUSH")) {
			MIN_WIDTH = 20;
			MIN_HEIGHT = 20;
			MAX_WIDTH = 50;
			MAX_HEIGHT = 50;
			STARTING_VEL_MIN = 3;
			STARTING_VEL_MAX = 4;
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

		// Formula for random numbers in a range: random * (max - min) + min.
		float b = (random.nextFloat() * (1.0f - 0.6f)) + 0.6f; // Brightness
		float h = (random.nextFloat() * (1.0f - 0.3f)) + 0.3f; // Hue is the actual color's number..?
		float s = (random.nextFloat() * (1.0f - 0.3f) + 0.3f); // Saturation is the intensity of the color (hue)
		return new Rock(random.nextInt(800 + width - 2) - (width - 1), -height + 1, width, height,
				random.nextInt((int) (STARTING_VEL_MAX - STARTING_VEL_MIN)) + STARTING_VEL_MIN + random.nextFloat(),
				Color.getHSBColor(h, s, b));

		// return new Rock(random.nextInt(800 + width - 2) - (width - 1), -height + 1, width, height,
		// random.nextInt((int) (STARTING_VEL_MAX - STARTING_VEL_MIN)) + STARTING_VEL_MIN + random.nextFloat(),
		// possibleColors.get(random.nextInt(possibleColors.size())));

	}

}
