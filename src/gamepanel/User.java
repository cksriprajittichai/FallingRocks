package gamepanel;

import java.awt.Color;

import non_gamepanel.GameManager;

public class User {

	private float xPos;
	private float yPos;
	private int width;
	private int height;
	private float xVel;
	private Color color;

	public User(int xPos_arg, int yPos_arg, int width_arg, int height_arg, int xVel_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		xVel = xVel_arg;
		width = width_arg;
		height = height_arg;

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
			if (xPos + width > GameManager.rightXBound) {
				xPos = (int) (GameManager.rightXBound - width);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
