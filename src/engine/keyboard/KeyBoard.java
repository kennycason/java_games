package engine.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
	
	private boolean[] keys;
	
	private long[] keyPressedTime;
	
	public KeyBoard() {
		keys = new boolean[KeyEvent.KEY_LAST];
		keyPressedTime = new long[KeyEvent.KEY_LAST];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		keyPressedTime[e.getKeyCode()] = System.currentTimeMillis();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;	
		keyPressedTime[e.getKeyCode()] = -1;
	}
	
	public long keyPressedTime(int k) {
		return keyPressedTime[k];
	}
	
	public boolean isKeyPressed(int k) {
		return keys[k];
	}
	
	public boolean isKeyCombo(int[] k) {
		boolean ret = true;
		for(int i = 0;i < k.length; i++) {
			ret &= keys[k[i]];
		}
		return ret;
	}
	
}
