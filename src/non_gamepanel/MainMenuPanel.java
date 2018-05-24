package non_gamepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class MainMenuPanel extends JPanel {

    private PanelSwitchListener panelSwitchListener;

    private static final int FONT_INCREASE_SIZE = 4;

    private ArrayList<ImageIcon> backgroundImageList;
    private ImageIcon currentBackground;

    // XPos of these DrawnLabels is arbitrary, because they are specifically aligned when painted.
    private DrawnLabel titleLabel = new DrawnLabel(0, 100, "FALLING ROCKS", new Font("Serif", Font.BOLD, 92), Color.WHITE, 0);
    private DrawnLabel highScoreLabel = new DrawnLabel(0, 214, "BEST: " + Settings.getInstance().getHighScore(), new Font("Arial_Black", Font.BOLD, 40), Color.WHITE, 0);

    private DrawnLabel rushLabel = new DrawnLabel(0, 356, "PLAY RUSH", new Font("Impact", Font.PLAIN, 70), Color.WHITE, FONT_INCREASE_SIZE);
    private DrawnLabel zenLabel = new DrawnLabel(0, 451, "PLAY ZEN", new Font("Impact", Font.PLAIN, 70), Color.WHITE, FONT_INCREASE_SIZE);
    private DrawnLabel optionsLabel = new DrawnLabel(0, 526, "OPTIONS", new Font("Impact", Font.PLAIN, 55), Color.WHITE, FONT_INCREASE_SIZE);
    private DrawnLabel exitLabel = new DrawnLabel(0, 596, "EXIT", new Font("Impact", Font.PLAIN, 55), Color.WHITE, FONT_INCREASE_SIZE);

    private DrawnLabel drawnLabels[] = new DrawnLabel[4];
    private int focusIndex = 0;


    public MainMenuPanel(PanelSwitchListener panelSwitchListener) {
        setPreferredSize(new Dimension(800, 800));
        this.panelSwitchListener = panelSwitchListener;

        initInputMap();
        initActionMap();

        initBackgroundImageList();
        currentBackground = backgroundImageList.get(new Random().nextInt(backgroundImageList.size()));

        drawnLabels[0] = rushLabel;
        drawnLabels[1] = zenLabel;
        drawnLabels[2] = optionsLabel;
        drawnLabels[3] = exitLabel;

        // Initialize DrawnLabel with focus
        drawnLabels[focusIndex].setFontSize(drawnLabels[focusIndex].getFontSize() + FONT_INCREASE_SIZE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Display antialiased text using rendering hints
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.drawImage(currentBackground.getImage(), 0, 0, this);

        g2d.setFont(titleLabel.getFont());
        titleLabel.drawTextLayers(g2d, 4, Color.BLACK);
        titleLabel.drawTextLayers(g2d, 3, Color.WHITE);
        titleLabel.drawTextLayers(g2d, 2, Color.BLACK);
        titleLabel.drawTextLayers(g2d, 1, Color.BLUE);
        g2d.setColor(titleLabel.getColor());
        g2d.drawString(titleLabel.getText(), titleLabel.getXPosCenteredStringInWidth(800), titleLabel.getYPos());

        g2d.setFont(highScoreLabel.getFont());
        highScoreLabel.drawTextLayers(g2d, 2, Color.BLACK);
        highScoreLabel.drawTextLayers(g2d, 1, Color.DARK_GRAY);
        g2d.setColor(highScoreLabel.getColor());
        g2d.drawString(highScoreLabel.getText(), highScoreLabel.getXPosCenteredStringInWidth(800), highScoreLabel.getYPos());

        DrawnLabel drawnLabel;
        for (int i = 0; i < drawnLabels.length; i++) {
            drawnLabel = drawnLabels[i];

            g2d.setFont(drawnLabel.getFont());
            drawnLabel.drawTextLayers(g2d, 1, Color.BLACK);
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
        iMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
        iMap.put(KeyStroke.getKeyStroke("UP"), "UP");
    }


    private void initActionMap() {
        ActionMap aMap = getActionMap();

        aMap.put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawnLabels[focusIndex].setPressed(true);
                panelSwitchListener.onPanelSwitch(PanelSwitchListener.MAIN_MENU_PANEL_EXIT);
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
    }


    private void initBackgroundImageList() {
        backgroundImageList = new ArrayList<>();

        // First filename is "mainMenu0.png", last is "mainMenu18.png"
        for (int count = 1; count <= 18; count++) {
            if (count < 10) {
                backgroundImageList.add(new ImageIcon("res/background_images/mainMenu0" + count + ".png"));
            } else {
                backgroundImageList.add(new ImageIcon("res/background_images/mainMenu" + count + ".png"));
            }
        }
    }


    public boolean isRushPressed() {
        return rushLabel.isPressed();
    }


    public boolean isZenPressed() {
        return zenLabel.isPressed();
    }


    public boolean isOptionsPressed() {
        return optionsLabel.isPressed();
    }


    public boolean isExitPressed() {
        return exitLabel.isPressed();
    }

}
