package non_gamepanel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

public class DrawnLabel {

    private int xPos;
    private int yPos; // Baseline of the text
    private int textWidthUnfocused;
    private int textWidthFocused;
    private int fontIncreaseWhenFocused;
    private String text;
    private Color color;
    private Font font;
    private boolean pressed = false;


    /**
     * Change the DrawnLabel constructor to encourage constructing DrawnLabels to be centered at an x position?
     */
    public DrawnLabel(int xPos, int yPos, String text, Font font, Color color, int fontIncreaseWhenFocused) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.font = font;
        this.color = color;
        this.fontIncreaseWhenFocused = fontIncreaseWhenFocused;

        Graphics2D tempG2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();

        // Set text widths
        textWidthUnfocused = tempG2d.getFontMetrics(getFont()).stringWidth(getText());
        Font focusedFont = new Font(getFont().getFontName(), getFont().getStyle(), getFont().getSize() + this.fontIncreaseWhenFocused);
        textWidthFocused = tempG2d.getFontMetrics(focusedFont).stringWidth(getText());
    }


    // Calling this on a DrawnLabel, then painting the DrawnLabel, will make the most visible
    // DrawnLabel appear to be outlined by the color colored text numLayers that extend numLayers
    // pixels outside of the most visible DrawnLabel.
    public void drawTextLayers(Graphics2D g2d, int numLayers, Color color) {
        /**
         * Change to be independent of 800.
         */
        g2d.setFont(getFont());
        g2d.setColor(color);
        g2d.drawString(getText(), getXPosCenteredStringInWidth(800) - numLayers, getYPos() - numLayers);
        g2d.drawString(getText(), getXPosCenteredStringInWidth(800) - numLayers, getYPos() + numLayers);
        g2d.drawString(getText(), getXPosCenteredStringInWidth(800) + numLayers, getYPos() - numLayers);
        g2d.drawString(getText(), getXPosCenteredStringInWidth(800) + numLayers, getYPos() + numLayers);
    }


    // The drawString() method makes it difficult to determine where a string will show up in the
    // panel. The int returned by this function is the x parameter for drawString() method, and
    // will align the text to be centered at a desired x position.
    public int getXPosCenteredStringInWidth(int width) {
        Graphics2D tempG2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        /**
         * Does this need to adjust whether in focus or not in focus? - Yes
         */
        FontMetrics metrics = tempG2d.getFontMetrics(font);
        return (width - metrics.stringWidth(getText())) / 2;
    }


    public int getXPosStringCenteredAt(int centerXPos) {
        Graphics2D tempG2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        FontMetrics metrics = tempG2d.getFontMetrics(font);
        return centerXPos - getTextWidthUnfocused() / 2;
    }


    public int getTextHeight(Graphics2D g2) {
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        GlyphVector glyphVector = g2.getFont().createGlyphVector(fontRenderContext, text);
        // X and y parameters are offsets, which are applicable in a getBounds() function, but
        // unimportant to a getHeight() function.
        return glyphVector.getPixelBounds(null, 0,0).height;
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


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;

        // Update textWidth values
        Graphics2D tempG2d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        textWidthUnfocused = tempG2d.getFontMetrics(getFont()).stringWidth(getText());
        Font tempFont = new Font(getFont().getFontName(), getFont().getStyle(), getFont().getSize() + fontIncreaseWhenFocused);
        textWidthFocused = tempG2d.getFontMetrics(tempFont).stringWidth(getText());
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public boolean isPressed() {
        return pressed;
    }


    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }


    public Font getFont() {
        return font;
    }


    public int getFontSize() {
        return font.getSize();
    }


    public void setFontSize(int fontSize) {
        this.font = new Font(font.getFontName(), font.getStyle(), fontSize);
    }


    public int getTextWidthUnfocused() {
        return textWidthUnfocused;
    }


    public int getTextWidthFocused() {
        return textWidthFocused;
    }

}
