package game;

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


    public User(int xPos, int yPos, int width, int height, float velocity, int xBound, int yBound, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = velocity;
        this.yVel = velocity;
        this.width = width;
        this.height = height;
        this.xBound = xBound;
        this.yBound = yBound;
        this.color = color;
    }


    // Changes the position of the user, considering GamePanel bounds.
    public void moveLeft() {
        if (xPos - xVel < 0) {
            xPos = 0;
        } else {
            xPos -= xVel;
        }
    }


    // Changes the position of the user, considering GamePanel bounds.
    public void moveRight() {
        if (xPos + xVel + width > xBound) {
            // The most right pixel of the user is at x = xPos + width - 1
            xPos = xBound - width;
        } else {
            xPos += xVel;
        }
    }


    // Changes the position of the user, considering GamePanel bounds.
    public void moveUp() {
        if (yPos - yVel < 0) {
            yPos= 0;
        } else {
            yPos -= yVel;
        }
    }


    // Changes the position of the user, considering GamePanel bounds.
    public void moveDown() {
        if (yPos + yVel + height > yBound) {
            // The most down pixel of the user is at y = yPos + height - 1
            yPos = yBound - height;
        } else {
            yPos += yVel;
        }
    }


    public float getXPos() {
        return xPos;
    }


    public float getYPos() {
        return yPos;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public Color getColor() {
        return color;
    }

}
