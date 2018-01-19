package non_gamepanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ArrayList<ImageIcon> backgroundImageList;
	private ImageIcon currentBackground;
	private DrawnLabel rushBtn;
	/** MUST UPDATE GAMEMANAGER TO LISTEN TO THESE */
	private DrawnLabel highScoreBtn;
	private DrawnLabel zenBtn;
	private DrawnLabel optionsBtn;
	private DrawnLabel helpBtn;
	private int focusIndex; // Rush = 0, zen = 1, options = 2, help = 4 (indexes in drawnLabels).
	private DrawnLabel drawnLabels[];
	private HashSet<Integer> keysPressedNow;
	private HashSet<Integer> pendingMoves; // When up or down is pressed, move the focus if possible. Add up or down to
											// this set. Do not allow another up or down move to happen until up or down
											// is removed from this set (move is cleared = button released).

	public MainMenuPanel(KeyConverter keyConverter) {
		setupBackgroundImageList();
		currentBackground = backgroundImageList.get(new Random().nextInt(backgroundImageList.size()));

		keysPressedNow = keyConverter.getKeysPressedNow();
		keysPressedNow.clear();
		pendingMoves = new HashSet<Integer>();

		setPreferredSize(new Dimension(800, 800));
		setOpaque(false);

		// Hide cursor.
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // Transparent cursor image
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		setCursor(blankCursor); // Set this panels cursor to blank cursor

		focusIndex = 0; // Initiate focus on rushBtn

		rushBtn = new DrawnLabel(0, 330, "PLAY RUSH", new Font("Serif", Font.BOLD, 75), Color.BLUE);
		zenBtn = new DrawnLabel(0, 460, "PLAY ZEN", new Font("Serif", Font.BOLD, 75), Color.WHITE);
		optionsBtn = new DrawnLabel(0, 600, "OPTIONS", new Font("Serif", Font.BOLD, 45), Color.WHITE);
		helpBtn = new DrawnLabel(0, 650, "HELP", new Font("Serif", Font.BOLD, 45), Color.WHITE);
		highScoreBtn = new DrawnLabel(0, 250, "BEST: " + new Random().nextInt(), new Font("Serif", Font.PLAIN, 40),
				Color.LIGHT_GRAY);

		// Put highScoreBtn specifically at the end of array, because it cannot be focused on. So when iterating through
		// drawnLabels, highScoreBtn can be ignored.
		drawnLabels = new DrawnLabel[5];
		drawnLabels[0] = rushBtn;
		drawnLabels[1] = zenBtn;
		drawnLabels[2] = optionsBtn;
		drawnLabels[3] = helpBtn;
		drawnLabels[4] = highScoreBtn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (keysPressedNow.contains(KeyEvent.VK_SPACE)) {
			drawnLabels[focusIndex].setPressed(true); // Selection made, exit
			return;
		}

		int oldFocusIndex = focusIndex; // Only repaint if changes are made

		if (keysPressedNow.contains(KeyEvent.VK_UP)) {
			if (focusIndex > 0 && !pendingMoves.contains(KeyEvent.VK_UP)) {
				focusIndex--;
				pendingMoves.add(KeyEvent.VK_UP);
			}
		} else { // Up isn't being pressed now, up move is cleared from pendingMoves
			pendingMoves.remove(KeyEvent.VK_UP);
		}
		if (keysPressedNow.contains(KeyEvent.VK_DOWN)) {
			if (focusIndex < 3 && !pendingMoves.contains(KeyEvent.VK_DOWN)) {
				focusIndex++;
				pendingMoves.add(KeyEvent.VK_DOWN);
			}
		} else {
			pendingMoves.remove(KeyEvent.VK_DOWN);
		}

		if (oldFocusIndex != focusIndex) {
			drawnLabels[oldFocusIndex].setColor(Color.WHITE); // Turn off focus for previous button
			drawnLabels[focusIndex].setColor(Color.BLUE);

			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; // Display antialiased text using rendering hints
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.drawImage(currentBackground.getImage(), 0, 0, this);

		g2d.setFont(new Font("Serif", Font.BOLD, 92));
		g2d.setColor(Color.BLUE);
		g2d.drawString("FALLING ROCKS", 17, 149); // Add depth to title
		g2d.drawString("FALLING ROCKS", 19, 151);
		g2d.setColor(Color.WHITE);
		g2d.drawString("FALLING ROCKS", 18, 150);

		for (DrawnLabel drawnLabel : drawnLabels) {
			g2d.setFont(drawnLabel.getFont());
			g2d.setColor(drawnLabel.getColor());
			g2d.drawString(drawnLabel.getText(), getXPosCenteredString(g2d, drawnLabel.getText(),
					new Rectangle(0, 0, 800, 800), drawnLabel.getFont()), drawnLabel.getYPos());
		}

	}

	/*
	 * The drawString() method makes it difficult to determine where a string will show up in the panel. The int
	 * returned by this function is the x parameter for drawString() method, and will align the text in the middle of
	 * the x-axis. Draws the string in the middle of the @param rectangle.
	 */
	public int getXPosCenteredString(Graphics2D g2d, String text, Rectangle rectangle, Font font) {
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = rectangle.x + (rectangle.width - metrics.stringWidth(text)) / 2; // Determine the X coordinate for the
																					// text
		return x;
	}

	private void setupBackgroundImageList() {
		backgroundImageList = new ArrayList<ImageIcon>();

		// Took 26 screenshots, first is background01.png, last is background26.png.
		// Random colored rocks are screenshots 1 through 18, selected colored rocks are 19 through 26.
		for (int count = 1; count <= 18; count++) {
			if (count < 10) {
				backgroundImageList
						.add(new ImageIcon(Main.class.getResource("/background_images/background0" + count + ".png"))); // ?
			} else {
				backgroundImageList
						.add(new ImageIcon(Main.class.getResource("/background_images/background" + count + ".png")));
			}
		}
	}

	public DrawnLabel getRushBtn() {
		return rushBtn;
	}

	public void setRushBtn(DrawnLabel rushBtn) {
		this.rushBtn = rushBtn;
	}

	public DrawnLabel getZenBtn() {
		return zenBtn;
	}

	public void setZenBtn(DrawnLabel zenBtn) {
		this.zenBtn = zenBtn;
	}

	public DrawnLabel getOptionsBtn() {
		return optionsBtn;
	}

	public void setOptionsBtn(DrawnLabel optionsBtn) {
		this.optionsBtn = optionsBtn;
	}

	public DrawnLabel getHelpBtn() {
		return helpBtn;
	}

	public void setHelpBtn(DrawnLabel helpBtn) {
		this.helpBtn = helpBtn;
	}

}
