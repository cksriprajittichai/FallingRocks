package options;

import non_gamepanel.DrawnLabel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawnLabelWithTriangles {

    private final int DISTANCE_TRIANGLE_TIP_TO_DRAWNLABEL = 30;
    private final int DEFAULT_TRIANGLE_SCALE = 6;

    private DrawnLabel drawnLabel;
    private Triangle leftTriangle;
    private Triangle rightTriangle;
    private Color leftTriangleColor;
    private Color rightTriangleColor;
    private int centerXPos;


    public DrawnLabelWithTriangles(int centerXPos, int yPos, String text, Font font, Color labelColor, Color triangleColors) {
        this.centerXPos = centerXPos;

        // DrawnLabelWithTriangles cannot be in focus, so fontIncreaseWhenFocused = 0. The xPos
        // and yPos parameters that have been passed in are incorrect, but will be changed below.
        drawnLabel = new DrawnLabel(0, 0, text, font, labelColor, 0);

        // XPos: Center text at centerXPos. The left and right Triangles are equidistant from the
        // center of the text.
        drawnLabel.setXPos(drawnLabel.getXPosStringCenteredAt(this.centerXPos));

        // YPos: yPos of the Triangle tips. Adjust the yPos of the text so that the the arrow
        // tips are at the halfway height of the text.
        Graphics2D tempG2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        tempG2d.setFont(font);
        int textHeight = drawnLabel.getTextHeight(tempG2d); // Height of drawnLabel's font
        int correctYPos = yPos + (textHeight / 2);
        drawnLabel.setYPos(correctYPos);


        // YPos: yPos of the Triangle tips
        leftTriangle = new Triangle('L', this.centerXPos - (drawnLabel.getTextWidthUnfocused() / 2) - DISTANCE_TRIANGLE_TIP_TO_DRAWNLABEL,
                yPos, DEFAULT_TRIANGLE_SCALE);
        rightTriangle = new Triangle('R', this.centerXPos + (drawnLabel.getTextWidthUnfocused() / 2) + DISTANCE_TRIANGLE_TIP_TO_DRAWNLABEL,
                yPos, DEFAULT_TRIANGLE_SCALE);

        leftTriangleColor = triangleColors;
        rightTriangleColor = triangleColors;
    }


    /**
     * Changes the DrawnLabel's text and updates 1) the xPos of the text to remained centered
     * at it's original centerXPos, and 2) the Triangles locations to adjust to the new text.
     */
    public void setText(String text) {
        drawnLabel.setText(text);

        // Recenter text
        drawnLabel.setXPos(drawnLabel.getXPosStringCenteredAt(centerXPos));

        // Adjust xPos of Triangles based on new text
        int yPos = leftTriangle.getTipY(); // =rightTriangle.getTipY()
        leftTriangle = new Triangle('L', centerXPos - (drawnLabel.getTextWidthUnfocused() / 2) - DISTANCE_TRIANGLE_TIP_TO_DRAWNLABEL,
                yPos, DEFAULT_TRIANGLE_SCALE);
        rightTriangle = new Triangle('R', centerXPos + (drawnLabel.getTextWidthUnfocused() / 2) + DISTANCE_TRIANGLE_TIP_TO_DRAWNLABEL,
                yPos, DEFAULT_TRIANGLE_SCALE);
    }


    public DrawnLabel getDrawnLabel() {
        return drawnLabel;
    }


    public Triangle getLeftTriangle() {
        return leftTriangle;
    }


    public Triangle getRightTriangle() {
        return rightTriangle;
    }


    public Color getLeftTriangleColor() {
        return leftTriangleColor;
    }


    public void setLeftTriangleColor(Color leftTriangleColor) {
        this.leftTriangleColor = leftTriangleColor;
    }


    public Color getRightTriangleColor() {
        return rightTriangleColor;
    }


    public void setRightTriangleColor(Color rightTriangleColor) {
        this.rightTriangleColor = rightTriangleColor;
    }

}
