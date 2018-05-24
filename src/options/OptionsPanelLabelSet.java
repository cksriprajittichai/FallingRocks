package options;

import non_gamepanel.DrawnLabel;
import non_gamepanel.SettingsUpdater;

import java.awt.*;

public class OptionsPanelLabelSet implements SettingsUpdater {

    private DrawnLabelWithTriangles DLWT;
    private DrawnLabel drawnLabel;

    // Reactive OPLS should react to the left and right arrow keys being pressed. A reactive OPLS
    // is composed of a DrawnLabel on the left (title of the OPLS), and two Triangles to the
    // immediate left and right of the DrawnLabel on the right. The DrawnLabel on the right doesn't
    // have to be a DrawnLabel, but the Triangles are aligned in respect to a DrawnLabel being
    // there - DrawnLabels with whitespace as their text can act as placeholders for the Triangles,
    // if a DrawnLabel with non-whitespace characters is not appropriate. The user color option is
    // an example of a reactive OPLS that uses a DrawnLabel showing whitespace as placeholders for
    // the Triangles. A non reactive OPLS only contains a DrawnLabel on the left (title of the
    // OPLS). An example of a non reactive OPLS is the back button in the OptionsPanel. A non
    // reactive OPLS is simply a DrawnLabel - I am forcing it into being a OPLS so that I can
    // move through each option (OPLS) easily in the OptionsPanel.
    private boolean isReactive;

    private Object[] possibleValues;
    private int possibleValuesIndex;
    private boolean hasLoopableValues;


    public OptionsPanelLabelSet(boolean isReactive, DrawnLabel drawnLabel, Object[] possibleValues, int initPossibleValuesIndex, boolean hasLoopableValues) {
        this.isReactive = isReactive;
        this.drawnLabel = drawnLabel;

        if (isReactive) {
            this.possibleValues = possibleValues;
            this.possibleValuesIndex = initPossibleValuesIndex;
            this.hasLoopableValues = hasLoopableValues;

            boolean possibleValuesAreStrings = true;
            for (int i = 0; i < possibleValues.length; i++) {
                if (!(possibleValues[i] instanceof String)) {
                    possibleValuesAreStrings = false;
                }
            }

            String text;
            if (possibleValuesAreStrings) {
                text = (String) possibleValues[possibleValuesIndex];
            } else {
                // If possible values aren't Strings, set (right) DrawnLabel's text to empty String.
                // This specific String of whitespace is a good width for the square shown for the user
                // color option.
                text = "       ";
            }

            DLWT = new DrawnLabelWithTriangles(650, drawnLabel.getYPos() - 20, text,
                    new Font("Impact", Font.PLAIN, 30), new Color(240, 240, 240), Color.GRAY);
        }
    }


    public void paintDLWT(Graphics2D g2d) {
        if (isReactive()) {
            DrawnLabel reactiveDrawnLabel = DLWT.getDrawnLabel();

            // Paint DrawnLabel ON THE RIGHT (in between arrows)
            g2d.setFont(reactiveDrawnLabel.getFont());
            g2d.setColor(reactiveDrawnLabel.getColor());
            g2d.drawString(reactiveDrawnLabel.getText(), reactiveDrawnLabel.getXPos(), reactiveDrawnLabel.getYPos());

            g2d.setColor(DLWT.getLeftTriangleColor());
            g2d.fillPolygon(DLWT.getLeftTriangle());
            g2d.setColor(DLWT.getRightTriangleColor());
            g2d.fillPolygon(DLWT.getRightTriangle());
        }
    }


    public void moveLeft() {
        if (isReactive) {
            if (!hasLoopableValues) {
                if (possibleValuesIndex > 0) {
                    possibleValuesIndex--;

                    DLWT.setText((String) possibleValues[possibleValuesIndex]);
                    updateSettings();
                }
            } else {
                if (possibleValuesIndex > 0) {
                    possibleValuesIndex--;
                } else {
                    possibleValuesIndex = possibleValues.length - 1;
                }

                DLWT.setText((String) possibleValues[possibleValuesIndex]);
                updateSettings();
            }
        }
    }


    public void moveRight() {
        if (isReactive) {
            if (!hasLoopableValues) {
                if (possibleValuesIndex < possibleValues.length - 1) {
                    possibleValuesIndex++;

                    DLWT.setText((String) possibleValues[possibleValuesIndex]);
                    updateSettings();
                }
            } else {
                if (possibleValuesIndex < possibleValues.length - 1) {
                    possibleValuesIndex++;
                } else {
                    possibleValuesIndex = 0;
                }

                DLWT.setText((String) possibleValues[possibleValuesIndex]);
                updateSettings();
            }
        }
    }


    @Override
    public void updateSettings() {
        // Default does nothing. Instances that update Settings must override this.
    }


    public DrawnLabelWithTriangles getDLWT() {
        return DLWT;
    }


    public DrawnLabel getDrawnLabel() {
        return drawnLabel;
    }


    public Object[] getPossibleValues() {
        return possibleValues;
    }


    public int getPossibleValuesIndex() {
        return possibleValuesIndex;
    }


    public void setPossibleValuesIndex(int possibleValuesIndex) {
        this.possibleValuesIndex = possibleValuesIndex;
    }


    public boolean isReactive() {
        return isReactive;
    }

}
