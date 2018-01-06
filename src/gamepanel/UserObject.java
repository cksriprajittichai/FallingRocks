package fallingrocks;

import java.awt.Color;

public class UserObject {

	private int xPos;
	private int yPos;
	private int xVel;
	private int yVel;
	private int width;
	private int height;
	private int xBound; // Bound of GamePanel
	private int yBound;
	private Color color;

	public UserObject(int xPos_arg, int yPos_arg, int xVel_arg, int yVel_arg, int width_arg, int height_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		xVel = xVel_arg;
		yVel = yVel_arg;
		width = width_arg;
		height = height_arg;

		xBound = 799;
		yBound = 599;

		color = Color.BLUE; // Change later?
	}

	// Changes the position of the object, considering GamePanel bounds.
	public void move(char direction) {
		switch (direction) {
		case 'L':
			xPos -= xVel;
			if (xPos < 0) {
				xPos = 0;
			}
			break;
		case 'R':
			xPos += xVel;
			if (xPos + width > xBound) {
				xPos = (int) (xBound - width);
			}
			break;
		}
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

	public int getXVel() {
		return xVel;
	}

	public void setXVel(int xVel) {
		this.xVel = xVel;
	}

	public int getYVel() {
		return yVel;
	}

	public void setYVel(int yVel) {
		this.yVel = yVel;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
