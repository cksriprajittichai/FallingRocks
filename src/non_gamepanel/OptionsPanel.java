package non_gamepanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JPanel;

public class OptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private DrawnLabel zenDifficultyLabel;
	private DrawnLabel userSpeedLabel;
	private DrawnLabel userColorLabel;
	private DrawnLabel resetHighScoreLabel;
	private DrawnLabel drawnLabels[];
	private HashSet<Integer> keysPressedNow;

	public OptionsPanel(KeyConverter keyConverter_arg) {
		keysPressedNow = keyConverter_arg.getKeysPressedNow();
		keysPressedNow.clear();

		this.setBackground(Color.GREEN);

		zenDifficultyLabel = new DrawnLabel(0, 200, "ZEN DIFFICULTY", new Font("Impact", Font.PLAIN, 50), Color.WHITE);
		userSpeedLabel = new DrawnLabel(0, 300, "USER SPEED", new Font("Impact", Font.PLAIN, 50), Color.WHITE);
		userColorLabel = new DrawnLabel(0, 400, "USER COLOR", new Font("Impact", Font.PLAIN, 50), Color.WHITE);
		resetHighScoreLabel = new DrawnLabel(0, 500, "RESET HIGH SCORE", new Font("Impact", Font.PLAIN, 50), Color.WHITE);
		
		drawnLabels = new DrawnLabel[4];
		drawnLabels[0] = zenDifficultyLabel;
		drawnLabels[1] = userSpeedLabel;
		drawnLabels[2] = userColorLabel;
		drawnLabels[3] = resetHighScoreLabel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
