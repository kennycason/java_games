package engine.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import engine.Game;

public class Mouse implements MouseListener {
	
	private boolean[] keys;
	
	private long[] keyPressedTime;
	
	private long[] keyReleasedTime;
	
	private long clickedTimeThreshold = 100;
	
	private int x1;
	
	private int y1;
	
	private int x2;
	
	private int y2;
	
	public Mouse() {
		keys = new boolean[MouseEvent.MOUSE_LAST];
		keyPressedTime = new long[MouseEvent.MOUSE_LAST];
		Arrays.fill(keyPressedTime, -100);
		keyReleasedTime = new long[MouseEvent.MOUSE_LAST];
		Arrays.fill(keyReleasedTime, -100);
		for(int i = 0; i < keys.length; i++) {
			keyPressedTime[i] = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		keys[e.getButton()] = true;		
		keyPressedTime[e.getButton()] = Game.clock.elapsedMillis();
		x1 = e.getX() / Game.zoom();
		y1 = e.getY() / Game.zoom();
	//	System.out.println("pressed: " + x + " " + y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		keys[e.getButton()] = false;	
		keyReleasedTime[e.getButton()] = Game.clock.elapsedMillis();
		x2 = e.getX() / Game.zoom();
		y2 = e.getY() / Game.zoom();
		System.out.println("released: " + x2 + " " + y2);
	}
	
	public long mouseClickedTime(int e) {
		return keyPressedTime[e];
	}
	
	public boolean isMousePressed(int e) {
		return keys[e];
	}
	
	public boolean isMouseClicked(int e) {
		return Game.clock.elapsedMillis() - keyReleasedTime[e] < clickedTimeThreshold;
	}
	
	public int x1() {
		return x1;
	}
	
	public int y1() {
		return y1;
	}
	
	public int x2() {
		return x2;
	}
	
	public int y2() {
		return y2;
	}
	
}
