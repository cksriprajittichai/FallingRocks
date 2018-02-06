package non_gamepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.JPanel;

import gamepanel.GamePanel;

public class LightUpPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Color pauseColor;
	private Color slowDownColor;
	private Color leftColor;
	private Color rightColor;
	private Color upColor;
	private Color downColor;
	private String modeString; // Includes scoreString if in rush mode
	private GamePanel gamePanel; // To interact between panels
	private HashSet<Integer> keysPressedNow; // From Listener, same object. Initialized by Listener constructor.

	public LightUpPanel(KeyConverter keyConverter_arg, GamePanel gamePanel_arg) {
		keysPressedNow = keyConverter_arg.getKeysPressedNow(); // Cleared() by GamePanel, which is created before this
																// panel
		gamePanel = gamePanel_arg;

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(800, 200));

		pauseColor = Color.DARK_GRAY;
		slowDownColor = Color.GREEN;
		leftColor = Color.DARK_GRAY;
		rightColor = Color.DARK_GRAY;
		upColor = Color.DARK_GRAY;
		downColor = Color.DARK_GRAY;

		if (gamePanel.getMode().equals("ZEN")) {
			modeString = "ZEN";
		} else if (gamePanel.getMode().equals("RUSH")) {
			modeString = "RUSH: " + gamePanel.getScore(); // Must be updated
		}
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		// Make function synchronized because the keysPressedNow is being used in GamePanel and KeyConverter.

		if (gamePanel.isGameOver() == true) {
			// Continue to paint everything gray. React to nothing.
			pauseColor = Color.GRAY;
			slowDownColor = Color.GRAY;
			leftColor = Color.GRAY;
			rightColor = Color.GRAY;
			upColor = Color.GRAY;
			downColor = Color.GRAY;
			repaint();
			return;
		}

		if (keysPressedNow.contains(KeyEvent.VK_P)) { // Still must repaint other drawings in their deactivated states.
			pauseColor = Color.GREEN;
			keysPressedNow.remove(KeyEvent.VK_SPACE);
			keysPressedNow.remove(KeyEvent.VK_LEFT);
			keysPressedNow.remove(KeyEvent.VK_RIGHT);
			keysPressedNow.remove(KeyEvent.VK_UP);
			keysPressedNow.remove(KeyEvent.VK_DOWN);
		} else {
			pauseColor = Color.DARK_GRAY;
			if (gamePanel.getMode().equals("RUSH")) {
				modeString = "SCORE: " + gamePanel.getScore(); // Update
			}
		}

		if (keysPressedNow.contains(KeyEvent.VK_SPACE)) {
			slowDownColor = Color.RED;
		} else {
			slowDownColor = Color.GREEN;
		}

		// Only light up arrows if the user is moving in that direction. So if left and right keys are being pressed,
		// neither arrow will light up because the user will not move left or right.
		if (keysPressedNow.contains(KeyEvent.VK_LEFT) && !keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
			leftColor = Color.WHITE;
		} else {
			leftColor = Color.DARK_GRAY;
		}
		if (keysPressedNow.contains(KeyEvent.VK_RIGHT) && !keysPressedNow.contains(KeyEvent.VK_LEFT)) {
			rightColor = Color.WHITE;
		} else {
			rightColor = Color.DARK_GRAY;
		}
		if (keysPressedNow.contains(KeyEvent.VK_UP) && !keysPressedNow.contains(KeyEvent.VK_DOWN)) {
			upColor = Color.WHITE;
		} else {
			upColor = Color.DARK_GRAY;
		}
		if (keysPressedNow.contains(KeyEvent.VK_DOWN) && !keysPressedNow.contains(KeyEvent.VK_UP)) {
			downColor = Color.WHITE;
		} else {
			downColor = Color.DARK_GRAY;
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g; // Display antialiased text using rendering hints
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (keysPressedNow.contains(KeyEvent.VK_U)) {
			g2d.setColor(Color.MAGENTA);
		} else {
			g2d.setColor(Color.BLUE);
		}
		g2d.drawLine(0, 0, 799, 0); // Show top and bottom of panel
		g2d.drawLine(0, 199, 799, 199);

		g2d.setFont(new Font("Serif", Font.BOLD, 15)); // Pause
		g2d.setColor(pauseColor);
		g2d.drawString("[P]", 775, 20);

		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		g2d.setColor(Color.WHITE);
		g2d.drawString(modeString, 25, 25); // Increment score

		g2d.setColor(Color.WHITE);
		// g2d.drawArc(391, 92, 15, 15, 0, 360); // Draw circle between all four arrows
		g2d.fillOval(391, 92, 15, 15);

		g2d.setColor(Color.WHITE);
		g2d.drawRect(25, 85, 101, 31); // Draw slowDown ability bar
		g2d.setColor(slowDownColor);
		g2d.fillRect(26, 86, gamePanel.getSlowDownCharge() / 10, 30); // Rescale so that full bar has width = 100.
																		// The slowDownCharge value for all difficulties
																		// is on a scale of 1000.

		// Leave 20x20 square in between all four arrows
		g2d.setColor(leftColor);
		g2d.fillPolygon(new Arrow('L', 339, 100, 10));
		g2d.setColor(rightColor);
		g2d.fillPolygon(new Arrow('R', 459, 100, 10));
		g2d.setColor(upColor);
		g2d.fillPolygon(new Arrow('U', 399, 40, 10));
		g2d.setColor(downColor);
		g2d.fillPolygon(new Arrow('D', 399, 160, 10));
	}

}
