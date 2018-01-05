package fallingrocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private boolean gameOver;
	private UserObject user;
	private ArrayList<RockObject> rockList;
	private HashSet<Integer> keysPressedNow;

	public GamePanel(KeyConverter listener) {
		setBackground(Color.YELLOW);
		setMinimumSize(new Dimension(800, 600)); // Minimum size vital when using BoxLayout
		setMaximumSize(new Dimension(800, 600));

		gameOver = false;

		Random random = new Random();
		user = new UserObject(random.nextInt(800 - 31), 549, 3, 3, 30, 30);

		rockList = new ArrayList<RockObject>();

		keysPressedNow = listener.getKeysPressedNow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (keysPressedNow.contains(KeyEvent.VK_P)) { // Ignore left and right keys. Stop repainting() (stop animation).
			return;
		}

		if (keysPressedNow.contains(KeyEvent.VK_R)) {
			rockList.add(new RockObject(new Random().nextInt(800 - 26), 0, 25, 25, new Random().nextInt(3)));
		}

		if (keysPressedNow.contains(KeyEvent.VK_LEFT)) {
			user.move('L');
		}
		if (keysPressedNow.contains(KeyEvent.VK_RIGHT)) {
			user.move('R');
		}

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int index = 0; index < rockList.size(); index++) {
			if (checkGameOver(rockList.get(index)) == true) {
				gameOver = true; // Exit
				return;
			} else if (rockList.get(index).move('D') == false) { // Updates position and velocity of rock.
				rockList.remove(index); // Move() returns false if rock is out of bounds
				continue;
			}

			g.setColor(rockList.get(index).getColor());
			g.fillRect(rockList.get(index).getXPos(), rockList.get(index).getYPos(), rockList.get(index).getWidth(),
					rockList.get(index).getHeight());
		}

		g.setColor(user.getColor());
		g.fillRect(user.getXPos(), user.getYPos(), user.getWidth(), user.getHeight());
	}

	// Return true if a rock is in contact with the user.
	public boolean checkGameOver(RockObject rock) {
		if (user.getYPos() > rock.getYPos() + rock.getHeight() // Check if rock is too far above user
				|| user.getXPos() > rock.getXPos() + rock.getWidth() // Check if user is too far right of rock
				|| user.getXPos() + user.getWidth() < rock.getXPos()) { // Check if user is too far left of rock
			return false;
		}

		return true;
	}

	public boolean getGameOverStatus() {
		return gameOver;
	}

}
