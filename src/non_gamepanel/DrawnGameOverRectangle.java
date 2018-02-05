package non_gamepanel;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GameOverPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean playAgainPressed;

	public GameOverPanel() {
		setMinimumSize(new Dimension(800, 800));
		setMaximumSize(new Dimension(800, 800));
		setBackground(Color.BLACK);

		Button playAgainBtn = new Button("Play Again");
		playAgainBtn.addActionListener(this);
		add(playAgainBtn);

		playAgainPressed = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); // GetActionCommand for a button click returns the name of the button
											// Note: GetActionCommand() for a timer activated event is null
		if (command.compareTo("Play Again") == 0) {
			playAgainPressed = true;
		}
	}

	// Constantly called by a GameManager to know if the user wants to start a new game.
	public boolean isPlayAgainPressed() {
		return playAgainPressed;
	}

}
