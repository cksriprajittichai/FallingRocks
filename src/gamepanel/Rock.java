package gamepanel;

import java.awt.Color;
import java.awt.Rectangle;

public class Rock {

	private float xPos;
	private float yPos;
	private int width;
	private int height;
	private float currentVel;
	private final float gravitationalAcc;
	private boolean inBounds;
	private Color color;

	public Rock(int xPos_arg, int yPos_arg, int width_arg, int height_arg, float currentVel_arg, Color color_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		width = width_arg;
		height = height_arg;

		currentVel = currentVel_arg;
		gravitationalAcc = 0.001f;

		inBounds = true; // Assume in bounds initialization

		color = color_arg;
	}

	// Updates the position of the rock. Sets inBounds to false if rock exits bounds. Accounts for slowDown effect.
	public void move(boolean slowDownActivated) {
		if (slowDownActivated == true) {
			yPos += (currentVel * 0.5f); // Slow down
		} else {
			yPos += currentVel; // Positive y position is farther down the panel
			currentVel += gravitationalAcc;
		}

		if (yPos > 599) { // When the whole thing exits the screen
			inBounds = false;
		}
	}

	// Use Java's intersects (Class Rectangle) method. Returns true if this rock is touching the @param user.
	public boolean isTouchingUser(User user) {
		return new Rectangle((int) user.getxPos(), (int) user.getyPos(), user.getWidth(), user.getHeight())
				.intersects(new Rectangle((int) getxPos(), (int) getyPos(), getWidth(), getHeight()));
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

	public float getgraviationalAcc() {
		return gravitationalAcc;
	}

	public boolean isInBounds() {
		return inBounds;
	}

	public void setInBounds(boolean inBounds) {
		this.inBounds = inBounds;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
