package game;

import java.awt.Color;
import java.awt.Rectangle;

public class Rock {

    private float xPos;
    private float yPos;
    private int width;
    private int height;
    private float currentVel;
    private final float downAccel;
    private boolean inBounds;
    private Color color;


    public Rock(int xPos_arg, int yPos_arg, int width_arg, int height_arg, float currentVel_arg, Color color_arg) {
        xPos = xPos_arg;
        yPos = yPos_arg;
        width = width_arg;
        height = height_arg;

        currentVel = currentVel_arg;
        downAccel = 0.001f;

        inBounds = true; // Assume in bounds initialization

        color = color_arg;
    }


    // Updates the position of the rock. Sets inBounds to false if rock exits bounds. Accounts for
    // slowDown effect.
    public void move(boolean slowDownActivated) {
        if (slowDownActivated == true) {
            yPos += (currentVel * 0.5f); // Slow down
        } else {
            yPos += currentVel; // Positive y position is farther down the panel
            currentVel += downAccel;
        }

        if (yPos > 599) { // When the whole thing exits the screen
            inBounds = false;
        }
    }


    // Use Java's intersects (Class Rectangle) method. Returns true if this rock is touching the user.
    public boolean isTouchingUser(User user) {
        return new Rectangle((int) user.getXPos(), (int) user.getYPos(), user.getWidth(),
                user.getHeight()).intersects(new Rectangle((int) getxPos(), (int) getyPos(), getWidth(), getHeight()));
    }


    public float getxPos() {
        return xPos;
    }


    public float getyPos() {
        return yPos;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public boolean isInBounds() {
        return inBounds;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
