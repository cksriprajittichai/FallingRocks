package fallingrocks;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JFrame {
	
	/**
	 * TESTING GIT
	 */

	private static final long serialVersionUID = 1L;

	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	// Creates a new GamePanel and a new LightUpPanel, adds them to a top-level
	// container, and starts game.
	public void startGame() {
		KeyConverter listener = new KeyConverter();
		GamePanel gPanel = new GamePanel(listener);
		LightUpPanel bPanel = new LightUpPanel(listener);

		JPanel topPanel = new JPanel(); // Top-level panel that is the same size as the frame
		topPanel.setMinimumSize(new Dimension(800, 800));
		topPanel.setMaximumSize(new Dimension(800, 800));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // GPanel on top of bPanel
		topPanel.add(gPanel);
		topPanel.add(bPanel);

		topPanel.addKeyListener(listener); // Must have

		add(topPanel);
		setVisible(true);

		topPanel.requestFocusInWindow(); // Not required if keyListener is added to frame instead of topPanel

		int delay = 5;
		Timer timer = new Timer(delay, bPanel); // Timer for both ActionListeners
		timer.addActionListener(gPanel);
		timer.start();
	}

	public static void main(String args[]) {
		GameFrame gameFrame = new GameFrame();
		gameFrame.startGame();
	}

}
