package gamepanel;

import java.awt.Color;

import non_gamepanel.GameManager;

public class Rock {

	private float xPos;
	private float yPos;
	private int width;
	private int height;
	private float currentVel;
	private final float gravitationalAcc;
	private Color color;

	public Rock(int xPos_arg, int yPos_arg, int width_arg, int height_arg, int currentVel_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		width = width_arg;
		height = height_arg;

		currentVel = currentVel_arg;
		gravitationalAcc = 0.001f;

		color = Color.RED;
	}

	// Returns true if the whole rock is still above the ground.
	public boolean move(char direction, boolean slowDownActivated) {
		switch (direction) {
		case 'D':
			if (slowDownActivated == true) {
				yPos += (currentVel * 0.7f); // Slow down
			} else {
				yPos += currentVel; // Positive y position is farther down the panel
				currentVel += gravitationalAcc;
			}

			if (yPos > GameManager.bottomYBound) { // Delete the rock when the whole thing exits screen
				return false;
			}
			break;
		}

		return true;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
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

	public float getCurrentVel() {
		return currentVel;
	}

	public void setCurrentVel(float currentVel) {
		this.currentVel = currentVel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
