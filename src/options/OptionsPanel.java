package options;

import non_gamepanel.DrawnLabel;
import non_gamepanel.PanelSwitchListener;
import non_gamepanel.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OptionsPanel extends JPanel {

    private PanelSwitchListener panelSwitchListener;

    private static final int FONT_INCREASE_SIZE = 3;

    private OptionsPanelLabelSet zenDifficultiesOPLS;
    private OptionsPanelLabelSet userSpeedsOPLS;
    private OptionsPanelLabelSet userColorsOPLS;
    private OptionsPanelLabelSet resetHighScoreOPLS;
    private OptionsPanelLabelSet backOPLS;

    private OptionsPanelLabelSet[] OPLSArr = new OptionsPanelLabelSet[5];
    private int focusIndex = 0;


    public OptionsPanel(PanelSwitchListener panelSwitchListener) {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
        this.panelSwitchListener = panelSwitchListener;

        initInputMap();
        initActionMap();

        DrawnLabel zenDifficultyLabel = new DrawnLabel(100, 200, "ZEN DIFFICULTY",
                new Font("Impact", Font.PLAIN, 50), Color.WHITE, FONT_INCREASE_SIZE);
        String[] possibleZenDifficulties = {"4 BABIES", "EASY", "CHALLENGING", "HARD", "R.I.P."};
        // Set possibleZenDifficultiesIndex to current difficulty. Zen difficulties range from [1, 5].
        int possibleZenDifficultiesIndex = Settings.getInstance().getZenDifficulty() - 1;
        zenDifficultiesOPLS = new OptionsPanelLabelSet(true, zenDifficultyLabel,
                possibleZenDifficulties, possibleZenDifficultiesIndex, false) {
            @Override
            public void updateSettings() {
                // Zen difficulties are represented by ints in range [1, 5]
                Settings.getInstance().setZenDifficulty(getPossibleValuesIndex() + 1);
            }
        };

        DrawnLabel userSpeedLabel = new DrawnLabel(100, 300, "USER SPEED",
                new Font("Impact", Font.PLAIN, 50), Color.WHITE, FONT_INCREASE_SIZE);
        String[] possibleUserSpeeds = {"1", "2", "3", "4", "5"};
        // Set possibleUserSpeedsIndex to current user speed. User speed is in range [1, 5]
        int possibleUserSpeedsIndex = Settings.getInstance().getUserSpeed() - 1;
        userSpeedsOPLS = new OptionsPanelLabelSet(true, userSpeedLabel,
                possibleUserSpeeds, possibleUserSpeedsIndex, false) {
            @Override
            public void updateSettings() {
                // User speeds are in range [1, 5]
                Settings.getInstance().setUserSpeed(getPossibleValuesIndex() + 1);
            }
        };

        DrawnLabel userColorLabel = new DrawnLabel(100, 400, "USER COLOR",
                new Font("Impact", Font.PLAIN, 50), Color.WHITE, FONT_INCREASE_SIZE);
        Color[] possibleUserColors = {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA};
        // Set possibleUserColorsIndex to current user color
        int possibleUserColorsIndex = 0;
        for (int i = 0; i < possibleUserColors.length; i++) {
            if (possibleUserColors[i].equals(Settings.getInstance().getUserColor())) {
                possibleUserColorsIndex = i;
            }
        }
        userColorsOPLS = new OptionsPanelLabelSet(true, userColorLabel,
                possibleUserColors, possibleUserColorsIndex, true) {
            @Override
            public void paintDLWT(Graphics2D g2d) {
                DrawnLabel reactiveDrawnLabel = getDLWT().getDrawnLabel();

                // Paint color box in between the two Triangles
                g2d.setColor((Color) getPossibleValues()[getPossibleValuesIndex()]);
                g2d.fillRect(reactiveDrawnLabel.getXPos(), reactiveDrawnLabel.getYPos() - 16, 35, 34);

                g2d.setColor(getDLWT().getLeftTriangleColor());
                g2d.fillPolygon(getDLWT().getLeftTriangle());
                g2d.setColor(getDLWT().getRightTriangleColor());
                g2d.fillPolygon(getDLWT().getRightTriangle());
            }

            @Override
            public void moveLeft() {
                // Has loopable values
                if (getPossibleValuesIndex() > 0) {
                    setPossibleValuesIndex(getPossibleValuesIndex() - 1);
                } else {
                    setPossibleValuesIndex(getPossibleValues().length - 1);
                }

                // Do not update text of (right) DrawnLabel
                updateSettings();
            }

            @Override
            public void moveRight() {
                // Has loopable values
                if (getPossibleValuesIndex() < getPossibleValues().length - 1) {
                    setPossibleValuesIndex(getPossibleValuesIndex() + 1);
                } else {
                    setPossibleValuesIndex(0);
                }

                // Do not update text of (right) DrawnLabel
                updateSettings();
            }

            @Override
            public void updateSettings() {
                Settings.getInstance().setUserColor((Color) getPossibleValues()[getPossibleValuesIndex()]);
            }
        };

        DrawnLabel resetHighScoreLabel = new DrawnLabel(100, 500, "RESET HIGH SCORE",
                new Font("Impact", Font.PLAIN, 50), Color.WHITE, FONT_INCREASE_SIZE);
        resetHighScoreOPLS = new OptionsPanelLabelSet(false, resetHighScoreLabel,
                null, 0, false);

        DrawnLabel backLabel = new DrawnLabel(100, 600, "BACK",
                new Font("Impact", Font.PLAIN, 50), Color.GRAY, FONT_INCREASE_SIZE);
        backOPLS = new OptionsPanelLabelSet(false, backLabel,
                null, 0, false);


        OPLSArr[0] = zenDifficultiesOPLS;
        OPLSArr[1] = userSpeedsOPLS;
        OPLSArr[2] = userColorsOPLS;
        OPLSArr[3] = resetHighScoreOPLS;
        OPLSArr[4] = backOPLS;

        // Initialize DrawnLabel that has focus
        OPLSArr[focusIndex].getDrawnLabel().setFontSize(OPLSArr[focusIndex].getDrawnLabel().getFontSize() + FONT_INCREASE_SIZE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Display antialiased text using rendering hints
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        DrawnLabel drawnLabel;
        for (int i = 0; i < OPLSArr.length; i++) {
            drawnLabel = OPLSArr[i].getDrawnLabel();

            if (i == focusIndex) {
                // If the DrawnLabel at this index is in focus

                // Draw underline. Algebra to find correct x2.
                g2d.setColor(Color.BLACK);
                g2d.fillRect(drawnLabel.getXPos() - 1, drawnLabel.getYPos() + 4, drawnLabel.getTextWidthFocused() + 2, 4);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(drawnLabel.getXPos(), drawnLabel.getYPos() + 5, drawnLabel.getTextWidthFocused(), 2);
            }

            // Paint (left) DrawnLabel
            g2d.setFont(drawnLabel.getFont());
            g2d.setColor(drawnLabel.getColor());
            g2d.drawString(drawnLabel.getText(), drawnLabel.getXPos(), drawnLabel.getYPos());

            // If the OPLS is reactive, paint the (right) DrawnLabel and it's arrows
            if (OPLSArr[i].isReactive()) {
                OPLSArr[i].paintDLWT(g2d);
            }
        }
    }


    private void initInputMap() {
        InputMap iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        iMap.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
        iMap.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        iMap.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        iMap.put(KeyStroke.getKeyStroke("UP"), "UP");
        iMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
        iMap.put(KeyStroke.getKeyStroke("released LEFT"), "released LEFT");
        iMap.put(KeyStroke.getKeyStroke("released RIGHT"), "released RIGHT");
    }


    private void initActionMap() {
        ActionMap aMap = getActionMap();

        aMap.put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OPLSArr[focusIndex].getDrawnLabel().setPressed(true);
                panelSwitchListener.onPanelSwitch(PanelSwitchListener.OPTIONS_PANEL_EXIT);
            }
        });
        aMap.put("LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OPLSArr[focusIndex].isReactive()) {
                    // Updating text of the (right) DrawnLabel (part of the DLWT) is handled within moveRight()
                    OPLSArr[focusIndex].moveLeft();

                    if (OPLSArr[focusIndex].isReactive()) {
                        // Ensure right Triangle isn't colored, color left Triangle
                        OPLSArr[focusIndex].getDLWT().setRightTriangleColor(Color.GRAY);
                        OPLSArr[focusIndex].getDLWT().setLeftTriangleColor(Color.WHITE);
                    }

                    repaint();
                }
            }
        });
        aMap.put("RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OPLSArr[focusIndex].isReactive()) {
                    // Updating text of the (right) DrawnLabel (part of the DLWT) is handled within moveRight()
                    OPLSArr[focusIndex].moveRight();

                    if (OPLSArr[focusIndex].isReactive()) {
                        // Ensure left Triangle isn't colored, color right Triangle
                        OPLSArr[focusIndex].getDLWT().setLeftTriangleColor(Color.GRAY);
                        OPLSArr[focusIndex].getDLWT().setRightTriangleColor(Color.WHITE);
                    }

                    repaint();
                }
            }
        });
        aMap.put("UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (focusIndex > 0) {
                    if (OPLSArr[focusIndex].isReactive()) {
                        // Turn off left or right Triangle coloring, if any
                        OPLSArr[focusIndex].getDLWT().setLeftTriangleColor(Color.GRAY);
                        OPLSArr[focusIndex].getDLWT().setRightTriangleColor(Color.GRAY);
                    }

                    // Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
                    OPLSArr[focusIndex].getDrawnLabel().setFontSize(OPLSArr[focusIndex].getDrawnLabel().getFontSize() - FONT_INCREASE_SIZE);
                    focusIndex--; // Decrement focusIndex
                    OPLSArr[focusIndex].getDrawnLabel().setFontSize(OPLSArr[focusIndex].getDrawnLabel().getFontSize() + FONT_INCREASE_SIZE);

                    repaint();
                }
            }
        });
        aMap.put("DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (focusIndex < OPLSArr.length - 1) {
                    if (OPLSArr[focusIndex].isReactive()) {
                        // Turn off left or right Triangle coloring, if any
                        OPLSArr[focusIndex].getDLWT().setLeftTriangleColor(Color.GRAY);
                        OPLSArr[focusIndex].getDLWT().setRightTriangleColor(Color.GRAY);
                    }

                    // Change the fontSize of DrawnLabel that lost focus and of DrawnLabel that now has focus
                    OPLSArr[focusIndex].getDrawnLabel().setFontSize(OPLSArr[focusIndex].getDrawnLabel().getFontSize() - FONT_INCREASE_SIZE);
                    focusIndex++; // Increment focusIndex
                    OPLSArr[focusIndex].getDrawnLabel().setFontSize(OPLSArr[focusIndex].getDrawnLabel().getFontSize() + FONT_INCREASE_SIZE);

                    repaint();
                }
            }
        });

        aMap.put("released LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OPLSArr[focusIndex].isReactive()) {
                    OPLSArr[focusIndex].getDLWT().setLeftTriangleColor(Color.GRAY);

                    repaint();
                }
            }
        });
        aMap.put("released RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OPLSArr[focusIndex].isReactive()) {
                    OPLSArr[focusIndex].getDLWT().setRightTriangleColor(Color.GRAY);

                    repaint();
                }
            }
        });
    }


    public boolean isBackPressed() {
        return backOPLS.getDrawnLabel().isPressed();
    }


    public boolean isResetHighscorePressed() {
        return resetHighScoreOPLS.getDrawnLabel().isPressed();
    }

}
