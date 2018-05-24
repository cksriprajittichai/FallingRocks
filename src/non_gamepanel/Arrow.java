package non_gamepanel;

import java.awt.Polygon;

public class Arrow extends Polygon {

    /*
     * This class is for the movement arrows in the LightUpPanel. Each arrow in this class is
     * preset, and was made through experimentation. The arrows made are filled arrows.
     */

    // Parameters are the direction (orientation) of the arrow, the location of the tip
    // (point) of the arrow, and the scale value. The scale value determines the size of
    // the arrow. The arrow size is directly proportional to the scale value.
    public Arrow(char direction, int tipX, int tipY, int scale) {
        switch (direction) {

            case 'L':// Points are listed in clockwise order, following the tip.
                addPoint(tipX, tipY);
                addPoint(tipX + (2 * scale), tipY + (2 * scale));
                addPoint(tipX + (2 * scale), tipY + scale);
                addPoint(tipX + (5 * scale), tipY + scale);
                addPoint(tipX + (5 * scale), tipY - scale);
                addPoint(tipX + (2 * scale), tipY - scale);
                addPoint(tipX + (2 * scale), tipY - (2 * scale));
                break;
            case 'R':
                addPoint(tipX, tipY);
                addPoint(tipX - (2 * scale), tipY - (2 * scale));
                addPoint(tipX - (2 * scale), tipY - scale);
                addPoint(tipX - (5 * scale), tipY - scale);
                addPoint(tipX - (5 * scale), tipY + scale);
                addPoint(tipX - (2 * scale), tipY + scale);
                addPoint(tipX - (2 * scale), tipY + (2 * scale));
                break;
            case 'U':
                addPoint(tipX, tipY);
                addPoint(tipX + (2 * scale), tipY + (2 * scale));
                addPoint(tipX + scale, tipY + (2 * scale));
                addPoint(tipX + scale, tipY + (5 * scale));
                addPoint(tipX - scale, tipY + (5 * scale));
                addPoint(tipX - scale, tipY + (2 * scale));
                addPoint(tipX - (2 * scale), tipY + (2 * scale));
                break;
            case 'D':
                addPoint(tipX, tipY);
                addPoint(tipX - (2 * scale), tipY - (2 * scale));
                addPoint(tipX - scale, tipY - (2 * scale));
                addPoint(tipX - scale, tipY - (5 * scale));
                addPoint(tipX + scale, tipY - (5 * scale));
                addPoint(tipX + scale, tipY - (2 * scale));
                addPoint(tipX + (2 * scale), tipY - (2 * scale));
                break;
        }
    }

}
