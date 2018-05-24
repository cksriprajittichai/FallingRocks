package non_gamepanel;

import game.GamePanel;
import options.OptionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Manager implements PanelSwitchListener {

    private JFrame frame;
    private JPanel topPanel;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private LightUpPanel lightUpPanel;
    private GameOverPanel gameOverPanel;
    private OptionsPanel optionsPanel;
    private Timer timer;


    public Manager() {
        frame = new JFrame("FALLING ROCKS");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(800, 800));
        frame.pack(); // Vital
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Hide cursor
        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        frame.setCursor(blankCursor);

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 800));
        // BoxLayout along the y-axis (GamePanel on top of the LightUpPanel)
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        frame.add(topPanel);

        topPanel.requestFocusInWindow(); // Vital

        // Timer fires an ActionEvent to its ActionListeners every 15 milliseconds, this means
        // there are ~67 ActionEvents fired every second. The timer never permanently stops,
        // because the GameManager always needs it to listen to player commands.
        timer = new Timer(15, null);
    }


    @Override
    public void onPanelSwitch(int panelSwitchStatus) {
        switch (panelSwitchStatus) {
            case PanelSwitchListener.MAIN_MENU_PANEL_EXIT:
                if (mainMenuPanel.isRushPressed()) {
                    topPanel.removeAll();
                    mainMenuPanel = null;
                    startNewGame("RUSH");
                } else if (mainMenuPanel.isZenPressed()) {
                    topPanel.removeAll();
                    mainMenuPanel = null;
                    startNewGame("ZEN");
                } else if (mainMenuPanel.isOptionsPressed()) {
                    topPanel.removeAll();
                    mainMenuPanel = null;
                    goToOptionsPanel();
                } else if (mainMenuPanel.isExitPressed()) {
                    exitApplication();
                }
                break;

            case PanelSwitchListener.OPTIONS_PANEL_EXIT:
                if (optionsPanel.isBackPressed()) {
                    topPanel.removeAll();
                    optionsPanel = null;
                    goToMainMenu();
                } else if (optionsPanel.isResetHighscorePressed()) {
                    Settings.getInstance().setHighScore(0);
                }
                break;

            case PanelSwitchListener.GAME_PANEL_EXIT: {
                topPanel.removeAll();
                timer.removeActionListener(lightUpPanel);
                timer.removeActionListener(gamePanel);

                String lastPlayedMode = gamePanel.getMode();

                // Make lightUpPanel null first, because lightUpPanel has a reference to gamePanel.
                lightUpPanel = null;
                gamePanel = null;

                goToGameOverPanel(lastPlayedMode);
                break;
            }

            case PanelSwitchListener.GAME_OVER_PANEL_EXIT: {
                if (gameOverPanel.isPlayAnotherPressed()) {
                    topPanel.removeAll();
                    String lastPlayedMode = gameOverPanel.getLastPlayedMode();
                    gameOverPanel = null;
                    startNewGame(lastPlayedMode);
                } else if (gameOverPanel.isMainMenuPressed()) {
                    topPanel.removeAll();
                    gameOverPanel = null;
                    goToMainMenu();
                }
                break;
            }
        }
    }


    private void goToMainMenu() {
        mainMenuPanel = new MainMenuPanel(this);

        topPanel.add(mainMenuPanel);
        topPanel.revalidate();
    }


    // Initializes gamePanel and lightUpPanel, and adds them to the topPanel.
    private void startNewGame(String mode) {
        gamePanel = new GamePanel(this, mode);
        lightUpPanel = new LightUpPanel(gamePanel);

        topPanel.add(gamePanel);
        topPanel.add(lightUpPanel);
        topPanel.revalidate();

        timer.addActionListener(gamePanel);
        timer.addActionListener(lightUpPanel);
        timer.restart(); // Clean start
    }


    private void goToGameOverPanel(String lastPlayedMode) {
        gameOverPanel = new GameOverPanel(this, lastPlayedMode);

        topPanel.add(gameOverPanel);
        topPanel.revalidate();
    }


    private void goToOptionsPanel() {
        optionsPanel = new OptionsPanel(this);

        topPanel.add(optionsPanel);
        topPanel.revalidate();
    }


    public void startApplication() {
        goToMainMenu();
        frame.setVisible(true);
    }


    private void exitApplication() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}
