package non_gamepanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	private DrawnLabel titleLabel;
	private DrawnLabel highScoreLabel;
	private DrawnLabel rushLabel;
	private DrawnLabel zenLabel;
	private DrawnLabel optionsLabel;
	private DrawnLabel helpLabel;
	private DrawnLabel drawnLabels[];
	private int focusIndex; // Rush = 0, zen = 1, options = 2, help = 4 (indexes in drawnLabels).
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

		// Hide cursor
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		setCursor(blankCursor);

		focusIndex = 0; // Initiate focus on rushLabel

		// XPos of DrawnLabels is arbitrary, because they are all center aligned (function exists for this)
		titleLabel = new DrawnLabel(0, 150, "FALLING ROCKS", new Font("Serif", Font.BOLD, 92), Color.WHITE);
		highScoreLabel = new DrawnLabel(0, 254, "BEST: " + Manager.settings.getHighScore(),
				new Font("Arial_Black", Font.PLAIN, 40), Color.WHITE);

		rushLabel = new DrawnLabel(0, 386, "PLAY RUSH", new Font("Impact", Font.PLAIN, 70), Color.WHITE);
		zenLabel = new DrawnLabel(0, 482, "PLAY ZEN", new Font("Impact", Font.PLAIN, 70), Color.WHITE);
		optionsLabel = new DrawnLabel(0, 556, "OPTIONS", new Font("Impact", Font.PLAIN, 50), Color.WHITE);
		helpLabel = new DrawnLabel(0, 626, "HELP", new Font("Impact", Font.PLAIN, 50), Color.WHITE);

		drawnLabels = new DrawnLabel[4];
		drawnLabels[0] = rushLabel;
		drawnLabels[1] = zenLabel;
		drawnLabels[2] = optionsLabel;
		drawnLabels[3] = helpLabel;
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

			// Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
			drawnLabels[oldFocusIndex].setFontSize(drawnLabels[oldFocusIndex].getFontSize() - 7);
			drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + 7);

			repaint(); // Only repaint if changes were made
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g; // Display antialiased text using rendering hints
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.drawImage(currentBackground.getImage(), 0, 0, this);

		g2d.setFont(titleLabel.getFont());
		titleLabel.drawTextLayers(g2d, 4, Color.BLACK);
		titleLabel.drawTextLayers(g2d, 3, Color.WHITE);
		titleLabel.drawTextLayers(g2d, 2, Color.BLACK);
		titleLabel.drawTextLayers(g2d, 1, Color.BLUE);
		g2d.setColor(titleLabel.getColor());
		g2d.drawString(titleLabel.getText(), titleLabel.getXPosCenteredString(g2d), titleLabel.getYPos());

		g2d.setFont(highScoreLabel.getFont());
		highScoreLabel.drawTextLayers(g2d, 2, Color.BLACK);
		highScoreLabel.drawTextLayers(g2d, 1, Color.DARK_GRAY);
		g2d.setColor(highScoreLabel.getColor());
		g2d.drawString(highScoreLabel.getText(), highScoreLabel.getXPosCenteredString(g2d), highScoreLabel.getYPos());

		DrawnLabel drawnLabel;
		for (int index = 0; index < drawnLabels.length; index++) {
			drawnLabel = drawnLabels[index];
			g2d.setFont(drawnLabel.getFont());

			if (index == focusIndex) { // The DrawnLabel at this index is in focus

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

	public boolean isRushPressed() {
		return rushLabel.isPressed();
	}

	public boolean isZenPressed() {
		return zenLabel.isPressed();
	}

	public boolean isOptionsPressed() {
		return optionsLabel.isPressed();
	}

	public boolean isHelpPressed() {
		return helpLabel.isPressed();
	}

}
