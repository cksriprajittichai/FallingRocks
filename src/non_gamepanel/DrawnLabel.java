package non_gamepanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class DrawnLabel {

	private int xPos;
	private int yPos; // Baseline of the text
	private String text;
	private Color color;
	private Font font;
	private boolean pressed;

	public DrawnLabel(int xPos_arg, int yPos_arg, String text_arg, Font font_arg, Color color_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		text = text_arg;
		font = font_arg;
		color = color_arg;

		setPressed(false);
	}

	// Calling this on a DrawnLabel, then painting the DrawnLabel, will make the most visible DrawnLabel appear to be
	// outlined by the @param color colored text layers that extend @param layersOut pixels outside of the most visible
	// DrawnLabel.
	public void drawTextLayers(Graphics2D g2d, int layersOut, Color color) {
		g2d.setColor(color);
		g2d.drawString(getText(), getXPosCenteredString(g2d) - layersOut, getYPos() - layersOut);
		g2d.drawString(getText(), getXPosCenteredString(g2d) - layersOut, getYPos() + layersOut);
		g2d.drawString(getText(), getXPosCenteredString(g2d) + layersOut, getYPos() - layersOut);
		g2d.drawString(getText(), getXPosCenteredString(g2d) + layersOut, getYPos() + layersOut);
	}

	/*
	 * The drawString() method makes it difficult to determine where a string will show up in the panel. The int
	 * returned by this function is the x parameter for drawString() method, and will align the text in the middle of
	 * the x-axis.
	 */
	public int getXPosCenteredString(Graphics2D g2d) {
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = (800 - metrics.stringWidth(text)) / 2; // Determine the X coordinate for the text
		return x;
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

	public void setFont(Font font) {
		this.font = font;
	}

	public int getFontSize() {
		return font.getSize();
	}

	public void setFontSize(int fontSize) {
		this.font = new Font(font.getFontName(), font.getStyle(), fontSize);
	}

}
