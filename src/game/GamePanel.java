package game;

import non_gamepanel.PanelSwitchListener;
import non_gamepanel.Settings;
import non_gamepanel.SettingsUpdater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class GamePanel extends JPanel implements ActionListener, SettingsUpdater {

    private PanelSwitchListener panelSwitchListener;

    private String mode; // Zen or rush
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private boolean slowDownActivated = false;
    private boolean userIsDead = false; // Do not exit the GamePanel immediately after the use dies

    // Incrementing the score at the same speed as the Timer causes the score to increment too quickly
    private int scoreModeratorCounter = 1;
    private int score = 0;

    private int slowDownCharge; // Scaled to 1000 for both modes
    private int slowDownChargeDischarge;
    private int slowDownChargeRecharge;

    // The timerModeratorCounter will be used as a marker to have better control over the frequency
    // (speed) that things happen. The timerModeratorCounter will be incremented every fired
    // actionEvent. To increase frequency of actions (e.g. rocks added), do actions per every fewer
    // number of timerModeratorCounter increments (decrease timerModeratorLimit, resetting
    // timerModeratorCounter = 1 when timerModeratorCounter equals timerModeratorLimit). To decrease
    // frequency of actions, do actions per every greater number of timerModeratorCounter increments
    // (increase timerModeratorLimit).
    private int timerModeratorCounter = 1;
    private int timerModeratorLimit;

    // When the update amount (timerModeratorUpdateLimit) is reached, the timerModeratorLimit
    // decreases. This controls how quickly the frequency of rocks being added changes. Difficulty
    // increases during the game only in rush mode.
    private int timerModeratorUpdateCounter = 1;
    private int timerModeratorUpdateLimit;

    private User user = new User(400 - (31 / 2), 500, 31, 31, 1.35f + Settings.getInstance().getUserSpeed(), WIDTH, HEIGHT, Settings.getInstance().getUserColor());
    private LinkedList<Rock> rockList = new LinkedList<>();
    private RockFactory rockFactory;

    private HashSet<Integer> keysPressedNow = new HashSet<>();

    private String[] deathSlideFilenames = {
            "res/explosion_images/explosion010.png",
            "res/explosion_images/explosion010.png",
            "res/explosion_images/explosion020.png",
            "res/explosion_images/explosion020.png",
            "res/explosion_images/explosion020.png",
            "res/explosion_images/explosion030.png",
            "res/explosion_images/explosion030.png",
            "res/explosion_images/explosion030.png",
            "res/explosion_images/explosion040.png",
            "res/explosion_images/explosion040.png",
            "res/explosion_images/explosion040.png",
            "res/explosion_images/explosion050.png",
            "res/explosion_images/explosion050.png",
            "res/explosion_images/explosion050.png",
            "res/explosion_images/explosion060.png",
            "res/explosion_images/explosion060.png",
            "res/explosion_images/explosion070.png",
            "res/explosion_images/explosion070.png",
            "res/explosion_images/explosion080.png",
            "res/explosion_images/explosion080.png",
            "res/explosion_images/explosion080.png",
            "res/explosion_images/explosion090.png",
            "res/explosion_images/explosion090.png",
            "res/explosion_images/explosion090.png",
            "res/explosion_images/explosion100.png",
            "res/explosion_images/explosion100.png",
            "res/explosion_images/explosion100.png",
            "res/explosion_images/explosion110.png",
            "res/explosion_images/explosion110.png",
            "res/explosion_images/explosion110.png",
            "res/explosion_images/explosion120.png",
            "res/explosion_images/explosion120.png",
            "res/explosion_images/explosion130.png",
            "res/explosion_images/explosion130.png",
            "res/explosion_images/explosion140.png",
            "res/explosion_images/explosion140.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/explosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion150.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion140.png",
            "res/explosion_images/endExplosion130.png",
            "res/explosion_images/endExplosion130.png",
            "res/explosion_images/endExplosion130.png",
            "res/explosion_images/endExplosion130.png",
            "res/explosion_images/endExplosion130.png",
    };
    private ImageIcon[] deathSlides = new ImageIcon[deathSlideFilenames.length];
    private int[] deathSlideWidths = new int[deathSlideFilenames.length];
    private int deathSlidesNdx = 0;

    private ImageIcon starsBackground = new ImageIcon("res/background_images/stars.jpg");


    public GamePanel(PanelSwitchListener panelSwitchListener, String mode) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.panelSwitchListener = panelSwitchListener;
        this.mode = mode;

        // Init deathSlides and deathSlideWidths
        String tempFilename;
        for (int i = 0; i < deathSlideFilenames.length; i++) {
            deathSlides[i] = new ImageIcon(deathSlideFilenames[i]);
            tempFilename = substringBefore(substringAfterLast(deathSlideFilenames[i], "xplosion"), ".png");
            while (tempFilename.startsWith("0")) {
                tempFilename = tempFilename.substring(1);
            }
            deathSlideWidths[i] = Integer.parseInt(tempFilename);
        }

        initInputMap();
        initActionMap();

        rockFactory = new RockFactory(getMode());

        slowDownCharge = 1000;
        if (mode.equals("ZEN")) {
            int difficulty = Settings.getInstance().getZenDifficulty();

            // Found through experimentation. Determine equations by picking desired min and max.
            slowDownChargeDischarge = 5 + (6 * difficulty); // level1 = 40, level5 = 3
            slowDownChargeRecharge = 40 + (-7 * difficulty); // level1 = 40, level5 = 5
            timerModeratorLimit = 14 + (5 * difficulty); // level1 = 14, level5 = 34

            slowDownChargeDischarge = 10;
            slowDownChargeRecharge = 30;

            timerModeratorLimit = 20; // Start frequency
        } else if (mode.equals("RUSH")) {
            slowDownChargeDischarge = 25;
            slowDownChargeRecharge = 5;

            timerModeratorLimit = 9;
            timerModeratorUpdateLimit = 1600;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (userIsDead) {
            if (!rockList.isEmpty()) {
                // Move existing rocks to exit. Don't add new rocks.
                ListIterator<Rock> iterator = rockList.listIterator();
                Rock tempRock;
                while (iterator.hasNext()) {
                    tempRock = iterator.next();

                    tempRock.move(false); // SlowDown cannot be activated
                    if (!tempRock.isInBounds()) {
                        iterator.remove();
                    }
                }
            } else {
                // Exit gamePanel after user has died and all rocks have fallen through
                panelSwitchListener.onPanelSwitch(PanelSwitchListener.GAME_PANEL_EXIT);
            }

            repaint();
            return;
        }

        if (keysPressedNow.contains(KeyEvent.VK_P)) {
            // Ignore left and right keys. Don't repaint() (stop animation).
            return;
        }

        if (scoreModeratorCounter++ == 20) {
            score++;
            scoreModeratorCounter = 0;
        }

        if (keysPressedNow.contains(KeyEvent.VK_SPACE)) {
            if (slowDownCharge - slowDownChargeDischarge < 0) {
                // No charge left for slow down
                slowDownActivated = false;
                slowDownCharge = 0;
            } else {
                slowDownActivated = true;
                slowDownCharge -= slowDownChargeDischarge;
            }
        } else {
            slowDownActivated = false;
            if (slowDownCharge + slowDownChargeRecharge > 1000) {
                // Max slowDownCharge is 1000
                slowDownCharge = 1000;
            } else {
                slowDownCharge += slowDownChargeRecharge;
            }
        }

        if (keysPressedNow.contains(KeyEvent.VK_LEFT) && !keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
            user.moveLeft();
        } else if (keysPressedNow.contains(KeyEvent.VK_RIGHT) && !keysPressedNow.contains(KeyEvent.VK_LEFT)) {
            user.moveRight();
        }
        if (keysPressedNow.contains(KeyEvent.VK_UP) && !keysPressedNow.contains(KeyEvent.VK_DOWN)) {
            user.moveUp();
        } else if (keysPressedNow.contains(KeyEvent.VK_DOWN) && !keysPressedNow.contains(KeyEvent.VK_UP)) {
            user.moveDown();
        }

        ListIterator<Rock> iterator = rockList.listIterator();
        Rock tempRock;
        while (iterator.hasNext()) {
            tempRock = iterator.next();

            if (tempRock.isTouchingUser(user)) {
                userIsDead = true;
                updateSettings();
                repaint(); // Don't miss a repaint
                return;
            } else {
                tempRock.move(slowDownActivated);
                if (!tempRock.isInBounds()) {
                    iterator.remove();
                }
            }
        }
        // New rocks don't need to be moved, so put after rock-moving code above new rock creation.

        // Moderate the frequency that rocks are added
        if (timerModeratorCounter++ >= timerModeratorLimit) {
            timerModeratorCounter = 1;

            rockList.add(rockFactory.next());
        }

        if (mode.equals("RUSH")) {
            // Gradually speed up rocks and increase amount of time in "rock speed level" only in rush mode.

            if (timerModeratorUpdateCounter++ >= timerModeratorUpdateLimit) {
                timerModeratorUpdateCounter = 1;

                // Make the levels longer as you get farther in.
                timerModeratorUpdateLimit *= 1.2;

                timerModeratorLimit--;
            }
        }

        repaint();
    }


    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(starsBackground.getImage(), 0, 0, this);

        // Everything else is always painted on top of rocks. Rocks are still painted when the user dies.
        for (Rock r : rockList) {
            g.setColor(r.getColor());
            g.fillRect((int) r.getxPos(), (int) r.getyPos(), r.getWidth(), r.getHeight());
        }

        if (!userIsDead) {
            g.setColor(user.getColor());
            g.fillRect((int) user.getXPos(), (int) user.getYPos(), user.getWidth(), user.getHeight());
        } else if (deathSlidesNdx < deathSlides.length) {
            g.drawImage(deathSlides[deathSlidesNdx].getImage(), (int) user.getXPos() - deathSlideWidths[deathSlidesNdx] / 2,
                    (int) user.getYPos() - deathSlideWidths[deathSlidesNdx] / 2, this);

            deathSlidesNdx++;
        }
    }


    @Override
    public void updateSettings() {
        if (score > Settings.getInstance().getHighScore()) {
            Settings.getInstance().setHighScore(score);
        }
    }


    private void initInputMap() {
        InputMap iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        iMap.put(KeyStroke.getKeyStroke("P"), "P");

        iMap.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
        iMap.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        iMap.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        iMap.put(KeyStroke.getKeyStroke("UP"), "UP");
        iMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");

        iMap.put(KeyStroke.getKeyStroke("released SPACE"), "released SPACE");
        iMap.put(KeyStroke.getKeyStroke("released LEFT"), "released LEFT");
        iMap.put(KeyStroke.getKeyStroke("released RIGHT"), "released RIGHT");
        iMap.put(KeyStroke.getKeyStroke("released UP"), "released UP");
        iMap.put(KeyStroke.getKeyStroke("released DOWN"), "released DOWN");
    }


    private void initActionMap() {
        ActionMap aMap = getActionMap();

        aMap.put("P", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keysPressedNow.contains(KeyEvent.VK_P)) {
                    keysPressedNow.remove(KeyEvent.VK_P);
                } else {
                    keysPressedNow.add(KeyEvent.VK_P);
                }
            }
        });

        aMap.put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.add(KeyEvent.VK_SPACE);
            }
        });
        aMap.put("LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.add(KeyEvent.VK_LEFT);
            }
        });
        aMap.put("RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.add(KeyEvent.VK_RIGHT);
            }
        });
        aMap.put("UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.add(KeyEvent.VK_UP);
            }
        });
        aMap.put("DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.add(KeyEvent.VK_DOWN);
            }
        });

        aMap.put("released SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.remove(KeyEvent.VK_SPACE);
            }
        });
        aMap.put("released LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.remove(KeyEvent.VK_LEFT);
            }
        });
        aMap.put("released RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.remove(KeyEvent.VK_RIGHT);
            }
        });
        aMap.put("released UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.remove(KeyEvent.VK_UP);
            }
        });
        aMap.put("released DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keysPressedNow.remove(KeyEvent.VK_DOWN);
            }
        });
    }


    public HashSet<Integer> getKeysPressedNow() {
        return keysPressedNow;
    }


    public boolean isUserDead() {
        return userIsDead;
    }


    public int getSlowDownCharge() {
        return slowDownCharge;
    }


    public int getScore() {
        return score;
    }


    public String getMode() {
        return mode;
    }

}
