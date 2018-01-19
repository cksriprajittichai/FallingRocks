package non_gamepanel;

import java.awt.Color;
import java.awt.Font;

public class DrawnLabel {

	private float xPos;
	private float yPos; // Baseline of the text
	private String text;
	private Color color;
	private Font font;
	private boolean pressed;

	public DrawnLabel(float xPos_arg, float yPos_arg, String text_arg, Font font_arg, Color color_arg) {
		xPos = xPos_arg;
		yPos = yPos_arg;
		text = text_arg;
		setFont(font_arg);
		color = color_arg;

		setPressed(false);
	}

	public float getXPos() {
		return xPos;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public float getYPos() {
		return yPos;
	}

	public void setYPos(float yPos) {
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

}
