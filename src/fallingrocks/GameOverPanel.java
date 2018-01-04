package fallingrocks;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GameOverPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public GameOverPanel() {
		setMinimumSize(new Dimension(800, 800));
		setMaximumSize(new Dimension(800, 800));
		setBackground(Color.MAGENTA);
		
		Button playAgain = new Button("Play Again");
		playAgain.addActionListener(this);
		add(playAgain);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
