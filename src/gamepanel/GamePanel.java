package gamepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JPanel;

import non_gamepanel.DrawnGameOverRectangle;
import non_gamepanel.DrawnLabel;
import non_gamepanel.KeyConverter;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String mode; // Zen or rush
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private int score;
	private int slowDownCharge; // Scaled to 1000 for both modes
	private int slowDownChargeDischarge;
	private int slowDownChargeRecharge;
	private int timerModeratorCounter; // The timerModeratorCounter will be used as a marker to have better control over
										// the frequency (speed) that things happen. The timerModeratorCounter will be
										// incremented every fired actionEvent. To increase frequency of actions (e.g.
										// rocks added), do actions per every fewer number of timerModeratorCounter
										// increments (decrease timerModeratorLimit, resetting timerModeratorCounter = 1
										// when timerModeratorCounter equals timerModeratorLimit). To decrease frequency
										// of actions, do actions per every greater number of timerModeratorCounter
										// increments (increase timerModeratorLimit).
	private int timerModeratorLimit;
	private int timerModeratorUpdateCounter; // When the difficulty-dependent update amount (timerModeratorUpdateLimit)
												// is reached, the timerModeratorLimit decreases. This controls how
												// quickly the frequency of actions changes.
	private int timerModeratorUpdateLimit;
	private User user;
	private LinkedList<Rock> rockList;
	private RockGenerator rockGenerator;
	private boolean slowDownActivated;
	private boolean gameOver;
	private DrawnGameOverRectangle drawnGameOverRectangle;
	private HashSet<Integer> keysPressedNow;

	public GamePanel(KeyConverter keyConverter_arg, String mode_arg) {
		keysPressedNow = keyConverter_arg.getKeysPressedNow();
		keysPressedNow.clear();
		mode = mode_arg;
		rockList = new LinkedList<Rock>();
		rockGenerator = new RockGenerator(this);
		user = new User(WIDTH - 1, HEIGHT - 1, 1, 1, 3.0f, WIDTH, HEIGHT);
		score = 0;

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		timerModeratorCounter = 1;
		timerModeratorUpdateCounter = 1;
		if (mode.equals("ZEN")) {
			slowDownCharge = 1000;
			slowDownChargeDischarge = 10;
			slowDownChargeRecharge = 30;

			timerModeratorLimit = 20; // Start frequency
			timerModeratorUpdateLimit = 4000; // Decrement timerModeratorLimit every (timerModeratorUpdateLimit / 67)
												// seconds.
		} else if (mode.equals("RUSH")) {
			slowDownCharge = 1000;
			slowDownChargeDischarge = 25;
			slowDownChargeRecharge = 5;
			timerModeratorLimit = 9;
			timerModeratorUpdateLimit = 2000;
		}

		slowDownActivated = false;
		gameOver = false;

		drawnGameOverRectangle = new DrawnGameOverRectangle(200, 230, 400, 200, this, keyConverter_arg);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		// Make function synchronized because the keysPressedNow is being used in LightUpPanel and KeyConverter.

		if (gameOver) {
			
			// Move existing rocks to their exit. Don't add new rocks.
			ListIterator<Rock> iterator = rockList.listIterator();
			Rock tempRock;
			while (iterator.hasNext()) {
				tempRock = iterator.next();

				tempRock.move(false); // SlowDown cannot be activated
				if (!tempRock.isInBounds()) {
					iterator.remove();
				}
			}

			// Manager is aware of when gameOver is true, and activates the DrawnGameOverRectangle by adding it to the
			// timer's list of ActionListeners.

			repaint(); // Paints DrawnGameOverRectangle and falling rocks
			return;
		}

		if (keysPressedNow.contains(KeyEvent.VK_P)) {
			return; // Ignore left and right keys. Don't repaint() (stop animation).
		}
		score++;

		if (keysPressedNow.contains(KeyEvent.VK_SPACE)) {
			slowDownCharge -= slowDownChargeDischarge;
			if (slowDownCharge > 0) {
				slowDownActivated = true;
			} else { // No charge for slow down
				slowDownActivated = false;
				slowDownCharge = 0;
			}
		} else {
			slowDownActivated = false;
			slowDownCharge += slowDownChargeRecharge;
			if (slowDownCharge > 1000) { // Max slowDownCharge is 1000
				slowDownCharge = 1000;
			}
		}

		if (keysPressedNow.contains(KeyEvent.VK_LEFT) && !keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
			user.move('L');
		}
		if (keysPressedNow.contains(KeyEvent.VK_RIGHT) && !keysPressedNow.contains(KeyEvent.VK_LEFT)) {
			user.move('R');
		}
		if (keysPressedNow.contains(KeyEvent.VK_UP) && !keysPressedNow.contains(KeyEvent.VK_DOWN)) {
			user.move('U');
		}
		if (keysPressedNow.contains(KeyEvent.VK_DOWN) && !keysPressedNow.contains(KeyEvent.VK_UP)) {
			user.move('D');
		}

		ListIterator<Rock> iterator = rockList.listIterator();
		Rock tempRock;
		while (iterator.hasNext()) {
			tempRock = iterator.next();

			if (tempRock.isTouchingUser(user) && !keysPressedNow.contains(KeyEvent.VK_U)) { /** INVINCIBLE MODE */
				gameOver = true;
				keysPressedNow.clear();
				repaint(); // Don't miss a repaint
				return;
			} else {
				tempRock.move(slowDownActivated);
				if (!tempRock.isInBounds()) {
					iterator.remove();
				}
			}
		}

		// New rocks don't need to be moved, so put after rock-moving code above
		if (timerModeratorCounter++ >= timerModeratorLimit) { // Moderate the frequency that rocks are added
			timerModeratorCounter = 1;

			rockList.add(rockGenerator.next());
			rockList.add(rockGenerator.next());
		}

		if (mode.equals("RUSH")) { // When the game ends, and the DrawnGameOverRectangle is shown, don't speed up rocks
			if (timerModeratorUpdateCounter++ >= timerModeratorUpdateLimit) {
				timerModeratorUpdateCounter = 1;
				timerModeratorUpdateLimit *= 1.2; // Make the levels longer as you get farther in.
				timerModeratorLimit--;

				System.out.println("Decreased timerModeratorLimit to: " + timerModeratorLimit + "\n");
			}
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Everything else is always painted on top of rocks.
		for (Rock tempRock : rockList) {
			g.setColor(tempRock.getColor());
			g.fillRect((int) tempRock.getxPos(), (int) tempRock.getyPos(), tempRock.getWidth(), tempRock.getHeight());
		}

		if (gameOver) {
			Graphics2D g2d = (Graphics2D) g; // Display antialiased text using rendering hints
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

//			g2d.setColor(Color.DARK_GRAY);
//			g2d.fillRect(drawnGameOverRectangle.getxPos(), drawnGameOverRectangle.getyPos(),
//					drawnGameOverRectangle.getWidth(), drawnGameOverRectangle.getHeight());

			DrawnLabel drawnLabels[] = drawnGameOverRectangle.getDrawnLabels();
			DrawnLabel drawnLabel;
			for (int index = 0; index < drawnLabels.length; index++) {
				drawnLabel = drawnLabels[index];
				g2d.setFont(drawnLabel.getFont());

				if (index == drawnGameOverRectangle.getFocusIndex()) { // The DrawnLabel at this index is in focus

					// Draw underline. Algebra to find correct x2.
					g2d.setColor(Color.BLACK);
					g2d.fillRect(drawnLabel.getXPosCenteredString(g2d), (int) drawnLabel.getYPos() + 4,
							2 * (400 - drawnLabel.getXPosCenteredString(g2d)), 4);
					g2d.setColor(Color.WHITE);
					g2d.fillRect(drawnLabel.getXPosCenteredString(g2d), (int) drawnLabel.getYPos() + 5,
							2 * (400 - drawnLabel.getXPosCenteredString(g2d)), 2);
				}

				drawnLabel.drawTextLayers(g2d, 1, Color.BLACK);
				g2d.setColor(drawnLabel.getColor());
				g2d.drawString(drawnLabel.getText(), drawnLabel.getXPosCenteredString(g2d), drawnLabel.getYPos());
			}
		} else {
			g.setColor(Color.WHITE);
			g.drawLine((int) user.getxPos(), 0, (int) user.getxPos(), HEIGHT - 1);
			g.drawLine(0, (int) user.getyPos(), WIDTH - 1, (int) user.getyPos());
		}
	}

	public DrawnGameOverRectangle getDrawnGameOverRectangle() {
		return drawnGameOverRectangle;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public int getSlowDownCharge() {
		return slowDownCharge;
	}

	public int getScore() {
		return score / 30; // Score increases too fast, qualify
	}

	public String getMode() {
		return mode;
	}

}
