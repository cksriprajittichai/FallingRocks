package gamepanel;

import java.awt.Color;

public class User {

	private float xPos;
	private float yPos;
	private int width;
	private int height;
	private float xVel;
	private int xBound; // Bound of GamePanel
	private Color color;

	public User(int xPos_arg, int yPos_arg, int xVel_arg, int width_arg, int height_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		xVel = xVel_arg;
		width = width_arg;
		height = height_arg;

		xBound = 799;

		color = Color.BLUE;
	}

	// Changes the position of the object, considering GamePanel bounds.
	public void move(char direction, boolean slowDownActivated) {
		switch (direction) {
		case 'L':
			if (slowDownActivated == true) {
				xPos -= xVel * 0.5;
			} else {
				xPos -= xVel;
			}
			if (xPos < 0) {
				xPos = 0;
			}
			break;
		case 'R':
			if (slowDownActivated == true) {
				xPos += xVel * 0.5;
			} else {
				xPos += xVel;
			}
			if (xPos + width > xBound) {
				xPos = (int) (xBound - width);
			}
			break;
		}
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

	public float getxVel() {
		return xVel;
	}

	public void setxVel(float xVel) {
		this.xVel = xVel;
	}

	public int getxBound() {
		return xBound;
	}

	public void setxBound(int xBound) {
		this.xBound = xBound;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
