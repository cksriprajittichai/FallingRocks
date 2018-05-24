package non_gamepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameOverPanel extends JPanel {

    private PanelSwitchListener panelSwitchListener;

    private static final int FONT_INCREASE_SIZE = 3;
    private String lastPlayedMode;
    private DrawnLabel playAnotherLabel;
    private DrawnLabel mainMenuLabel;

    private DrawnLabel[] drawnLabels = new DrawnLabel[2];
    private int focusIndex = 0;


    public GameOverPanel(PanelSwitchListener panelSwitchListener, String lastPlayedMode) {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
        this.panelSwitchListener = panelSwitchListener;
        this.lastPlayedMode = lastPlayedMode;

        initInputMap();
        initActionMap();

        playAnotherLabel = new DrawnLabel(0, 300, "PLAY AGAIN: " + lastPlayedMode, new Font("Impact", Font.PLAIN, 60), Color.WHITE, FONT_INCREASE_SIZE);
        mainMenuLabel = new DrawnLabel(0, 420, "MAIN MENU", new Font("Impact", Font.PLAIN, 60), Color.WHITE, FONT_INCREASE_SIZE);

        drawnLabels[0] = playAnotherLabel;
        drawnLabels[1] = mainMenuLabel;

        // Initialize DrawnLabel with focus
        drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + FONT_INCREASE_SIZE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Display antialiased text using rendering hints
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        DrawnLabel drawnLabel;
        for (int i = 0; i < drawnLabels.length; i++) {
            drawnLabel = drawnLabels[i];

            g2d.setFont(drawnLabel.getFont());
            g2d.setColor(drawnLabel.getColor());
            g2d.drawString(drawnLabel.getText(), drawnLabel.getXPosCenteredStringInWidth(800), drawnLabel.getYPos());

            if (i == focusIndex) {
                // If the DrawnLabel at this index is in focus

                // Draw underline. Algebra to find correct x2.
                g2d.setColor(Color.BLACK);
                g2d.fillRect(drawnLabel.getXPosCenteredStringInWidth(800) - 1, drawnLabel.getYPos() + 4, drawnLabel.getTextWidthFocused() + 2, 4);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(drawnLabel.getXPosCenteredStringInWidth(800), drawnLabel.getYPos() + 5, drawnLabel.getTextWidthFocused(), 2);
            }
        }
    }


    private void initInputMap() {
        InputMap iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        iMap.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
        iMap.put(KeyStroke.getKeyStroke("UP"), "UP");
        iMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
    }


    private void initActionMap() {
        ActionMap aMap = getActionMap();

        aMap.put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawnLabels[focusIndex].setPressed(true);
                panelSwitchListener.onPanelSwitch(PanelSwitchListener.GAME_OVER_PANEL_EXIT);
            }
        });

        aMap.put("UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (focusIndex > 0) {
                    // Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
                    drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() - FONT_INCREASE_SIZE);
                    focusIndex--;
                    drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + FONT_INCREASE_SIZE);

                    repaint();
                }
            }
        });
        aMap.put("DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (focusIndex < drawnLabels.length - 1) {
                    // Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
                    drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() - FONT_INCREASE_SIZE);
                    focusIndex++;
                    drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + FONT_INCREASE_SIZE);

                    repaint();
                }
            }
        });
    }


    public boolean isPlayAnotherPressed() {
        return playAnotherLabel.isPressed();
    }


    public boolean isMainMenuPressed() {
        return mainMenuLabel.isPressed();
    }


    public String getLastPlayedMode() {
        return lastPlayedMode;
    }

}
