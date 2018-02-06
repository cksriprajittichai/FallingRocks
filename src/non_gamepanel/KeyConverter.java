package non_gamepanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyConverter implements KeyListener {

	private HashSet<Integer> onOffKeys; // Stores on/off keys that are on
	private HashSet<Integer> keysPressedNow;

	public KeyConverter() {
		onOffKeys = new HashSet<Integer>();
		keysPressedNow = new HashSet<Integer>();
	}

	public HashSet<Integer> getKeysPressedNow() { // Ensure that lightUpPanel uses the same HashSet
		return keysPressedNow;
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		// Make synchronized because keysPressedNow is being used by LightUpPanel and GamePanel.

		switch (e.getKeyCode()) {
			case KeyEvent.VK_P :
				if (onOffKeys.contains(KeyEvent.VK_P)) { // Was on, now turn off
					onOffKeys.remove(KeyEvent.VK_P);
					keysPressedNow.remove(KeyEvent.VK_P);
				} else { // Was off, now turn on
					onOffKeys.add(KeyEvent.VK_P);
					keysPressedNow.add(KeyEvent.VK_P);
				}
				break;
			case KeyEvent.VK_U :
				if (onOffKeys.contains(KeyEvent.VK_U)) {
					onOffKeys.remove(KeyEvent.VK_U);
					keysPressedNow.remove(KeyEvent.VK_U);
				} else {
					onOffKeys.add(KeyEvent.VK_U);
					keysPressedNow.add(KeyEvent.VK_U);
				}
				break;
			case KeyEvent.VK_SPACE :
				keysPressedNow.add(KeyEvent.VK_SPACE);
				break;
			case KeyEvent.VK_LEFT :
				keysPressedNow.add(KeyEvent.VK_LEFT);
				break;
			case KeyEvent.VK_RIGHT :
				keysPressedNow.add(KeyEvent.VK_RIGHT);
				break;
			case KeyEvent.VK_UP :
				keysPressedNow.add(KeyEvent.VK_UP);
				break;
			case KeyEvent.VK_DOWN :
				keysPressedNow.add(KeyEvent.VK_DOWN);
				break;
		}
		
		this.notifyAll();
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		// Make synchronized because keysPressedNow is being used by LightUpPanel and GamePanel.

		// These keys are keys that continue to react while being held down, unlike keys
		// that have one reaction each time they are pressed (e.g. Pause key [P]).
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE :
				keysPressedNow.remove(KeyEvent.VK_SPACE);
				break;
			case KeyEvent.VK_LEFT :
				keysPressedNow.remove(KeyEvent.VK_LEFT);
				break;
			case KeyEvent.VK_RIGHT :
				keysPressedNow.remove(KeyEvent.VK_RIGHT);
				break;
			case KeyEvent.VK_UP :
				keysPressedNow.remove(KeyEvent.VK_UP);
				break;
			case KeyEvent.VK_DOWN :
				keysPressedNow.remove(KeyEvent.VK_DOWN);
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
