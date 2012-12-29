package engine.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
	
	private boolean[] keys;
	
	private long[] keyPressedTime;
	
	public KeyBoard() {
		keys = new boolean[KeyEvent.KEY_LAST];
		keyPressedTime = new long[KeyEvent.KEY_LAST];
		for(int i = 0; i < keys.length; i++) {
			keyPressedTime[i] = -1;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(keyPressedTime(e.getKeyCode()) == -1) { // only want to store the time when the key is first pressed
			keyPressedTime[e.getKeyCode()] = System.currentTimeMillis();
		}
		keys[e.getKeyCode()] = true;
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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < keys.length; i++) {
			if(isKeyPressed(i)) {
				sb.append("[" + i + "]\t = " + keyPressedTime(i) + "\n");
			}
		}
		return sb.toString();
	}
}
