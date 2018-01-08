package gamepanel;

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

import non_gamepanel.KeyConverter;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private boolean gameOver;
	private User user;
	private ArrayList<Rock> rockList;
	private boolean slowDownActivated;
	private HashSet<Integer> keysPressedNow;
	
	public GamePanel(KeyConverter listener) {
		setBackground(Color.YELLOW);
		
		setPreferredSize(new Dimension(800, 600));
		
		gameOver = false;

		Random random = new Random();
		user = new User(random.nextInt(799 - 15), 569, 30, 30, 2);
		rockList = new ArrayList<Rock>();
		
		slowDownActivated = false;

		keysPressedNow = listener.getKeysPressedNow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (keysPressedNow.contains(KeyEvent.VK_P)) { // Pause
			return; // Ignore left and right keys. Don't repaint() (stop animation).
		}
		
		if (keysPressedNow.contains(KeyEvent.VK_R)) { // Add rock
			rockList.add(new Rock(new Random().nextInt(799 - 100), 0, 25, 25, new Random().nextInt(2)));
		}

		if (keysPressedNow.contains(KeyEvent.VK_SPACE)) { // Slow down user and rocks
			slowDownActivated = true;
		} else {
			slowDownActivated = false;
		}

		if (keysPressedNow.contains(KeyEvent.VK_LEFT)) { // Move user left
			user.move('L', slowDownActivated);
		}
		if (keysPressedNow.contains(KeyEvent.VK_RIGHT)) { // Move user right
			user.move('R', slowDownActivated);
		}

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.RED);
		g.drawLine(0, 599, 799, 599); // SHOW BASELINE OF GAMEPANEL
		
		g.setColor(user.getColor());
		g.fillRect((int) user.getxPos(), (int) user.getyPos(), user.getWidth(), user.getHeight());

		for (int index = 0; index < rockList.size(); index++) {
			if (checkGameOver(rockList.get(index)) == true) { // Check for user-rock collision
				gameOver = true; // Exit
				return;
			} else if (rockList.get(index).move('D', slowDownActivated) == false) { // Move() updates position and velocity of rock.
																		// Move() returns false if rock is out of bounds.
				rockList.remove(index);
				continue;
			}

			g.setColor(rockList.get(index).getColor());
			g.fillRect((int) rockList.get(index).getxPos(), (int) rockList.get(index).getyPos(),
					rockList.get(index).getWidth(), rockList.get(index).getHeight());
		}
	}

	// Return true if a rock is in contact with the user.
	public boolean checkGameOver(Rock rock) {
		if (user.getyPos() > rock.getyPos() + rock.getHeight() // Check if rock is too far above user
				|| user.getxPos() > rock.getxPos() + rock.getWidth() // Check if user is too far right of rock
				|| user.getxPos() + user.getWidth() < rock.getxPos()) { // Check if user is too far left of rock
			return false;
		}

		return true;
	}

	public boolean getGameOverStatus() {
		return gameOver;
	}

}
