package non_gamepanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import gamepanel.GamePanel;

public class DrawnGameOverRectangle implements ActionListener {

	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private DrawnLabel playAgainLabel;
	private DrawnLabel mainMenuLabel;
	private DrawnLabel drawnLabels[];
	private int focusIndex; // PlayAgain = 0, mainMenu = 1 (indexes in drawnLabels).
	private GamePanel gamePanel;
	private HashSet<Integer> keysPressedNow;
	private HashSet<Integer> pendingMoves; // When up or down is pressed, move the focus if possible. Add up or down to
											// this set. Do not allow another up or down move to happen until up or down
											// is removed from this set (move is cleared = button released).

	public DrawnGameOverRectangle(int xPos_arg, int yPos_arg, int width_arg, int height_arg, GamePanel gamePanel_arg,
			KeyConverter keyConverter) {
		keysPressedNow = keyConverter.getKeysPressedNow();
		pendingMoves = new HashSet<Integer>();
		gamePanel = gamePanel_arg;

		xPos = xPos_arg;
		yPos = yPos_arg;
		width = width_arg;
		height = height_arg;

		setFocusIndex(0); // Initiate focus on playAgainLabel

		playAgainLabel = new DrawnLabel(0, 300, "PLAY AGAIN: " + gamePanel.getMode(),
				new Font("Impact", Font.PLAIN, 60), Color.WHITE);
		mainMenuLabel = new DrawnLabel(0, 420, "MAIN MENU", new Font("Impact", Font.PLAIN, 60), Color.WHITE);

		drawnLabels = new DrawnLabel[2];
		drawnLabels[0] = playAgainLabel;
		drawnLabels[1] = mainMenuLabel;
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
			if (focusIndex < 1 && !pendingMoves.contains(KeyEvent.VK_DOWN)) {
				focusIndex++;
				pendingMoves.add(KeyEvent.VK_DOWN);
			}
		} else {
			pendingMoves.remove(KeyEvent.VK_DOWN);
		}

		if (oldFocusIndex != focusIndex) {

			// Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
			drawnLabels[oldFocusIndex].setFontSize(drawnLabels[oldFocusIndex].getFontSize() - 6);
			drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + 6);
		}

	}

	public boolean isPlayAgainPressed() {
		return playAgainLabel.isPressed();
	}

	public boolean isMainMenuPressed() {
		return mainMenuLabel.isPressed();
	}

	public DrawnLabel[] getDrawnLabels() {
		return drawnLabels;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFocusIndex() {
		return focusIndex;
	}

	public void setFocusIndex(int focusIndex) {
		this.focusIndex = focusIndex;
	}

}
