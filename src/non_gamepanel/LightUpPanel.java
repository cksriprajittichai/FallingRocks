package non_gamepanel;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class LightUpPanel extends JPanel implements ActionListener {

    private Color pauseColor = Color.DARK_GRAY;
    private Color slowDownColor = Color.GREEN;
    private Color leftColor = Color.DARK_GRAY;
    private Color rightColor = Color.DARK_GRAY;
    private Color upColor = Color.DARK_GRAY;
    private Color downColor = Color.DARK_GRAY;

    private Color boundaryColor = Settings.getInstance().getUserColor();
    private String modeString; // Includes scoreString if in rush mode
    private GamePanel gamePanel; // To interact between panels

    private HashSet<Integer> keysPressedNow;


    public LightUpPanel(GamePanel gamePanel) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 200));

        // The LightUpPanel has a reference to the GamePanel's keysPressedNow HashSet
        this.gamePanel = gamePanel;
        this.keysPressedNow = gamePanel.getKeysPressedNow();

        if (gamePanel.getMode().equals("ZEN")) {
            modeString = "ZEN";
        } else if (gamePanel.getMode().equals("RUSH")) {
            modeString = "RUSH: " + gamePanel.getScore(); // Must be updated
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (gamePanel.isUserDead() == true) {
            // If the user is dead, react to absolutely nothing (don't make pause button green)
            pauseColor = Color.DARK_GRAY;
            slowDownColor = Color.DARK_GRAY;
            leftColor = Color.DARK_GRAY;
            rightColor = Color.DARK_GRAY;
            upColor = Color.DARK_GRAY;
            downColor = Color.DARK_GRAY;
            System.out.println("TEST");
            repaint();
            return;
        }

        if (keysPressedNow.contains(KeyEvent.VK_P)) {
            // React to nothing, repaint everything in deactivated states
            pauseColor = Color.GREEN;
            slowDownColor = Color.GREEN;
            leftColor = Color.DARK_GRAY;
            rightColor = Color.DARK_GRAY;
            upColor = Color.DARK_GRAY;
            downColor = Color.DARK_GRAY;

            repaint();
            return;
        } else {
            pauseColor = Color.DARK_GRAY;
            if (gamePanel.getMode().equals("RUSH")) {
                modeString = "SCORE: " + gamePanel.getScore(); // Update
            }
        }

        if (keysPressedNow.contains(KeyEvent.VK_SPACE)) {
            slowDownColor = Color.RED;
        } else {
            slowDownColor = Color.GREEN;
        }

        leftColor = Color.DARK_GRAY;
        rightColor = Color.DARK_GRAY;
        upColor = Color.DARK_GRAY;
        downColor = Color.DARK_GRAY;

        if (keysPressedNow.contains(KeyEvent.VK_LEFT) && !keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
            leftColor = Color.WHITE;
        } else if (keysPressedNow.contains(KeyEvent.VK_RIGHT) && !keysPressedNow.contains(KeyEvent.VK_LEFT)) {
            rightColor = Color.WHITE;
        }
        if (keysPressedNow.contains(KeyEvent.VK_UP) && !keysPressedNow.contains(KeyEvent.VK_DOWN)) {
            upColor = Color.WHITE;
        } else if (keysPressedNow.contains(KeyEvent.VK_DOWN) && !keysPressedNow.contains(KeyEvent.VK_UP)) {
            downColor = Color.WHITE;
        }

        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Display antialiased text using rendering hints
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(boundaryColor);
        g2d.drawLine(0, 0, 799, 0); // Show top and bottom of panel
        g2d.drawLine(0, 199, 799, 199);

        g2d.setFont(new Font("Serif", Font.BOLD, 15)); // Pause
        g2d.setColor(pauseColor);
        g2d.drawString("[P]", 775, 20);

        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString(modeString, 25, 25); // Increment score

        g2d.setColor(Color.WHITE);
        g2d.fillOval(391, 92, 15, 15);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(25, 85, 101, 31); // Draw slowDown ability bar

        // Rescale so that full bar has width = 100. The slowDownCharge value for all modes
        // is on a scale of 1000.
        g2d.setColor(slowDownColor);
        g2d.fillRect(26, 86, gamePanel.getSlowDownCharge() / 10, 30);

        // Leave 20x20 square in between all four arrows
        g2d.setColor(leftColor);
        g2d.fillPolygon(new Arrow('L', 339, 100, 10));
        g2d.setColor(rightColor);
        g2d.fillPolygon(new Arrow('R', 459, 100, 10));
        g2d.setColor(upColor);
        g2d.fillPolygon(new Arrow('U', 399, 40, 10));
        g2d.setColor(downColor);
        g2d.fillPolygon(new Arrow('D', 399, 160, 10));
    }

}
