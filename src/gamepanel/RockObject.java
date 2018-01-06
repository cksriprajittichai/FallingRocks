package fallingrocks;

import java.awt.Color;

public class RockObject {

	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int xBound; // Bound of GamePanel
	private int yBound;
	private float currentVel;
	private final float gravitationalAcc;
	private Color color;

	public RockObject(int xPos_arg, int yPos_arg, int width_arg, int height_arg, int currentVel_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		width = width_arg;
		height = height_arg;

		xBound = 799;
		yBound = 599;
		currentVel = currentVel_arg;
		gravitationalAcc = 0.05f;

		color = Color.RED;
	}

	// Returns true if the whole rock is still above the ground.
	public boolean move(char direction) {
		switch (direction) {
		case 'D':
			yPos += currentVel; // Positive y position is farther down the panel
			currentVel += gravitationalAcc;
			if (yPos > yBound) { // Delete the rock when the whole thing exits screen
				return false;
			}
			break;
		}

		return true;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
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

	public int getXBound() {
		return xBound;
	}

	public void setXBound(int xBound) {
		this.xBound = xBound;
	}

	public int getYBound() {
		return yBound;
	}

	public void setYBound(int yBound) {
		this.yBound = yBound;
	}

	public float getCurrentVel() {
		return currentVel;
	}

	public void setCurrentVel(int currentVel) {
		this.currentVel = currentVel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
