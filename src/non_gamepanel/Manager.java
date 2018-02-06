package non_gamepanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gamepanel.GamePanel;

public class Manager implements ActionListener {

	public static Settings settings;
	private boolean inGamePanel;
	private JFrame frame;
	private JPanel topPanel;
	private MainMenuPanel mainMenuPanel;
	private OptionsPanel optionsPanel;
	private GamePanel gamePanel;
	private LightUpPanel lightUpPanel;
	private KeyConverter keyConverter;
	private Timer timer;

	public Manager() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("FALLING ROCKS");
		frame.getContentPane().setPreferredSize(new Dimension(800, 800));
		frame.pack(); // Vital
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		inGamePanel = false;

		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(800, 800));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // GPanel on top of bPanel
		frame.add(topPanel);

		topPanel.requestFocusInWindow(); // Vital

		keyConverter = new KeyConverter();
		topPanel.addKeyListener(keyConverter);

		settings = new Settings(); // Tries to read settings from local file

		// Timer fires an ActionEvent to its ActionListeners every 15 milliseconds, this means there are ~67
		// ActionEvents fired every second. The timer never permanently stops, because the GameManager always needs it
		// to listen to player commands.
		timer = new Timer(15, null);
		timer.addActionListener(this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGamePanel == true && gamePanel.isGameOver() == true) { // If the game is running but should stop

			if (gamePanel.getMode().equals("RUSH") && gamePanel.getScore() > settings.getHighScore()) {
				settings.setHighScore(gamePanel.getScore());
			}

			timer.addActionListener(gamePanel.getDrawnGameOverRectangle());

			/** !!! */
			if (gamePanel.getDrawnGameOverRectangle().isPlayAgainPressed()) {
				timer.removeActionListener(lightUpPanel);
				timer.removeActionListener(gamePanel.getDrawnGameOverRectangle());
				timer.removeActionListener(gamePanel);
				topPanel.removeAll();
				String lastPlayedMode = gamePanel.getMode();
				lightUpPanel = null; // Make null first, because lightUpPanel has a reference to gamePanel
				gamePanel = null;
				inGamePanel = false;
				startNewGame(lastPlayedMode);
			} else if (gamePanel.getDrawnGameOverRectangle().isMainMenuPressed()) {
				timer.removeActionListener(lightUpPanel);
				timer.removeActionListener(gamePanel.getDrawnGameOverRectangle());
				timer.removeActionListener(gamePanel);
				topPanel.removeAll();
				lightUpPanel = null;
				gamePanel = null;
				inGamePanel = false;
				goToMainMenu();
			}
			/** !!! */

		} else if (inGamePanel == false) { // If in the gamePanel
			if (mainMenuPanel != null) { // If player is in the mainMenuPanel
				if (mainMenuPanel.isRushPressed()) {
					timer.stop();
					timer.removeActionListener(mainMenuPanel);
					topPanel.removeAll();
					mainMenuPanel = null;
					startNewGame("RUSH");
				} else if (mainMenuPanel.isZenPressed()) {
					timer.stop();
					timer.removeActionListener(mainMenuPanel);
					topPanel.removeAll();
					mainMenuPanel = null;
					startNewGame("ZEN");
				} else if (mainMenuPanel.isOptionsPressed()) {
					timer.removeActionListener(mainMenuPanel);
					topPanel.removeAll();
					mainMenuPanel = null;
					goToOptionsPanel();
				} else if (mainMenuPanel.isHelpPressed()) {
					/** Go to help panel */
				}
			}
		}
	}

	// This is the first function called
	public void goToMainMenu() {
		mainMenuPanel = new MainMenuPanel(keyConverter);
		timer.addActionListener(mainMenuPanel);

		topPanel.add(mainMenuPanel);
		topPanel.revalidate();

		frame.setVisible(true);
	}

	public void goToOptionsPanel() {
		optionsPanel = new OptionsPanel(keyConverter);
		timer.addActionListener(optionsPanel);

		topPanel.add(optionsPanel);
		topPanel.revalidate();
	}

	// Initializes gamePanel and lightUpPanel, and adds them to top-level container (topPanel), and starts game.
	public void startNewGame(String mode) {
		inGamePanel = true;

		gamePanel = new GamePanel(keyConverter, mode); /** ENTER MODE HERE */
		lightUpPanel = new LightUpPanel(keyConverter, gamePanel);

		topPanel.add(gamePanel);
		topPanel.add(lightUpPanel);
		topPanel.revalidate();
		topPanel.addKeyListener(keyConverter);
		topPanel.requestFocusInWindow();

		timer.addActionListener(gamePanel);
		timer.addActionListener(lightUpPanel);
		timer.restart(); // Clean start
	}

}
