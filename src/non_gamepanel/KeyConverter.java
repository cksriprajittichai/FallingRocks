package non_gamepanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;

public class KeyConverter implements KeyListener {

	private HashMap<Integer, Boolean> onOffKeys;
	private HashSet<Integer> keysPressedNow;

	public KeyConverter() {
		onOffKeys = new HashMap<Integer, Boolean>();
		onOffKeys.put(KeyEvent.VK_P, false);
		keysPressedNow = new HashSet<Integer>();
	}

	public HashSet<Integer> getKeysPressedNow() { // Ensure that lightUpPanel uses the same HashSet
		return keysPressedNow;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_SPACE:
			keysPressedNow.add(KeyEvent.VK_SPACE);
			break;
		case KeyEvent.VK_R:
			keysPressedNow.add(KeyEvent.VK_R);
			break;
		case KeyEvent.VK_P:
			onOffKeys.put(KeyEvent.VK_P, !onOffKeys.get(KeyEvent.VK_P)); // Flip boolean value
			if (onOffKeys.get(KeyEvent.VK_P) == true) { // Turned on
				keysPressedNow.add(KeyEvent.VK_P);
			} else {
				keysPressedNow.remove(KeyEvent.VK_P); // Turned off
			}
			break;
		case KeyEvent.VK_LEFT:
			keysPressedNow.add(KeyEvent.VK_LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			keysPressedNow.add(KeyEvent.VK_RIGHT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		// These keys are keys that continue to react while being held down, unlike keys
		// that have one reaction each time they are pressed (e.g. Pause key [P]).
		switch (key) {
		case KeyEvent.VK_SPACE:
			keysPressedNow.remove(KeyEvent.VK_SPACE);
			break;
		case KeyEvent.VK_R:
			keysPressedNow.remove(KeyEvent.VK_R);
			break;
		case KeyEvent.VK_LEFT:
			keysPressedNow.remove(KeyEvent.VK_LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			keysPressedNow.remove(KeyEvent.VK_RIGHT);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
