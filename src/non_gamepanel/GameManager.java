package non_gamepanel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gamepanel.GamePanel;

public class GameManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean gameRunning;
	private JPanel topPanel;
	private GamePanel gamePanel;
	private LightUpPanel lightUpPanel;
	private GameOverPanel gameOverPanel;
	private Timer timer;
	
	public static int leftXBound;
	public static int rightXBound;
	public static int topYBound;
	public static int bottomYBound;

	public GameManager() {
		Insets insets = getInsets();
		
		leftXBound = insets.left;
		rightXBound = insets.left + 800;
		topYBound = insets.top;
		bottomYBound = insets.top + 600;
		System.out.printf("leftX, rightX, topY, bottomY: %d, %d, %d, %d\n", leftXBound, rightXBound, topYBound, bottomYBound);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setPreferredSize(new Dimension(800, 800));
		pack(); // Vital
		
		setResizable(false);
		setLocationRelativeTo(null);

		gameRunning = false;

		topPanel = new JPanel();
		topPanel.setMinimumSize(new Dimension(getInsets().left + getInsets().right + 800, getInsets().bottom + getInsets().top + 800));
		topPanel.setMaximumSize(new Dimension(getInsets().left + getInsets().right + 800, getInsets().bottom + getInsets().top + 800));

		gamePanel = null;
		lightUpPanel = null;
		gameOverPanel = null;

		timer = null;
		
		this.setTitle("window is "+ this.getWidth() + " by "+ this.getHeight());
	}

	// Initializes gamePanel and lightUpPanel, and adds them to top-level
	// container (topPanel), and starts game.
	public void startNewGame() {
		gameRunning = true;

		KeyConverter listener = new KeyConverter();
		gamePanel = new GamePanel(listener);
		lightUpPanel = new LightUpPanel(listener);

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // GPanel on top of bPanel
		topPanel.add(gamePanel);
		topPanel.add(lightUpPanel);
		topPanel.revalidate();
		topPanel.addKeyListener(listener);

		getContentPane().add(topPanel);
		setVisible(true);

		topPanel.requestFocusInWindow(); // Not required if keyListener is added to frame instead of topPanel

		timer = new Timer(5, null);
		timer.addActionListener(gamePanel);
		timer.addActionListener(lightUpPanel);
		timer.addActionListener(this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameRunning == true && gamePanel.getGameOverStatus() == true) { // If the game is running but
																			// should be stopped
			gameRunning = false;

			timer.removeActionListener(gamePanel); // Delete gamePanel and lightUpPanel objects
			timer.removeActionListener(lightUpPanel);
			gamePanel = null;
			lightUpPanel = null;

			topPanel.removeAll();
			gameOverPanel = new GameOverPanel();
			topPanel.add(gameOverPanel);
			topPanel.revalidate();

		} else if (gameRunning == false) {
			if (gameOverPanel.isPlayAgainPressed() == true) {
				topPanel.removeAll();
				gameOverPanel = null;
				startNewGame();
			}
		}

	}

}
