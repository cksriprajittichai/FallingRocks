package gamepanel;

import java.awt.Color;

public class User {

	private float xPos;
	private float yPos;
	private int width;
	private int height;
	private float xVel;
	private float yVel;
	private int xBound;
	private int yBound;
	private Color color;

	public User(int xPos_arg, int yPos_arg, int width_arg, int height_arg, float velocity_arg, int xBound_arg, int yBound_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		xVel = velocity_arg;
		yVel = velocity_arg;
		width = width_arg;
		height = height_arg;
		xBound = xBound_arg;
		yBound = yBound_arg;

		color = Color.WHITE;
	}

	// Changes the position of the object, considering GamePanel bounds.
	public void move(char direction) {
		switch (direction) {
			case 'U' :
				yPos -= yVel;

				if (yPos < 0) {
					yPos = 0;
				}
				break;
			case 'D' :
				yPos += yVel;

				if (yPos + height - 1 > (yBound - 1)) { // The most down pixel of the user is at y = yPos + height - 1.
					yPos = (yBound - 1) - height + 1; // Algebra from yPos + height - 1 = 799
				}
				break;
			case 'L' :
				xPos -= xVel;

				if (xPos < 0) {
					xPos = 0;
				}
				break;
			case 'R' :
				xPos += xVel;

				if (xPos + width - 1 > (xBound - 1)) { // The most right pixel of the user is at x = xPos + width - 1.
					xPos = (xBound - 1) - width + 1; // Algebra from xPos + width - 1 = 799.
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

	public float getyVel() {
		return yVel;
	}

	public void setyVel(float yVel) {
		this.yVel = yVel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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

}
