package non_gamepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.JPanel;

public class LightUpPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int score;
	private int fontSize; // Can delete, if not using
	private Color pauseColor;
	private Color leftColor;
	private Color rightColor;
	private HashSet<Integer> keysPressedNow; // From Listener, same object. Initialized by Listener constructor.

	public LightUpPanel(KeyConverter listener) {
		setBackground(Color.BLACK);
		
		setPreferredSize(new Dimension(800, 200));

		score = 0;

		fontSize = 50;
		pauseColor = Color.GRAY;
		leftColor = Color.GRAY;
		rightColor = Color.GRAY;

		keysPressedNow = listener.getKeysPressedNow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint(); // Everything is handled in repaint().
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.RED);
		g.drawLine(0, 199, 799, 199); // SHOW BASELINE

		g.setFont(new Font("Serif", Font.BOLD, 15));
		if (keysPressedNow.contains(KeyEvent.VK_P)) {
			pauseColor = Color.GREEN;
			keysPressedNow.remove(KeyEvent.VK_LEFT); // Ignore left and right keys by
			keysPressedNow.remove(KeyEvent.VK_RIGHT); // removing from keysPressedNow.
			score--; // Cancel out future increment of score
		} else {
			pauseColor = Color.GRAY;
		}

		g.setColor(pauseColor);
		g.drawString("[P]", 775, 20);

		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.GRAY);
		g.drawString("Score: " + ++score / 100, 25, 25); // Increment score

		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		if (keysPressedNow.contains(KeyEvent.VK_LEFT)) {
			leftColor = Color.CYAN;
		} else {
			leftColor = Color.GRAY;
		}
		if (keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
			rightColor = Color.CYAN;
		} else {
			rightColor = Color.GRAY;
		}

		g.setColor(leftColor);
		g.drawString("LEFT", 135, 100);
		g.setColor(rightColor);
		g.drawString("RIGHT", 500, 100);
	}

}
