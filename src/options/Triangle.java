package options;

import java.awt.*;

public class Triangle extends Polygon {

    /*
     * This class is for the left and right arrows in the OptionsPanel. Each arrow in this class is
     * preset, and was made through experimentation. These are isosceles triangles. Look at Arrow
     * for more information.
     *
     * Dimension example:
     * If you were looking at a Triangle with direction 'U' (up), and scale 1:
     * If tip = (0, 0), then the other two points are at (-1, -3) and (1, -3).
     */

    private char direction;
    private int tipX;
    private int tipY;
    private int scale;


    // Parameters are the direction (orientation) of the triangle, the location of the tip (point)
    // of the arrow, and the scale value. The scale value determines the size of the arrow. The
    // arrow size is directly proportional to the scale value.
    public Triangle(char direction, int tipX, int tipY, int scale) {
        this.direction = direction;
        this.tipX = tipX;
        this.tipY = tipY;
        this.scale = scale;

        switch (direction) {
            // Points are listed in clockwise order, following the tip.
            case 'L':
                addPoint(tipX, tipY);
                addPoint(tipX + (3 * scale), tipY + scale);
                addPoint(tipX + (3 * scale), tipY - scale);
                break;
            case 'R':
                addPoint(tipX, tipY);
                addPoint(tipX - (3 * scale), tipY - scale);
                addPoint(tipX - (3 * scale), tipY + scale);
                break;
        }
    }


    public int[] getXPoints() {
        return xpoints;
    }


    public void setXPoints() {

    }


    public int[] getYPoints() {
        return ypoints;
    }


    public char getDirection() {
        return direction;
    }


    public int getTipY() {
        return tipY;
    }


    public void setTipY(int tipY) {
        this.tipY = tipY;
    }


    public int getScale() {
        return scale;
    }

}
