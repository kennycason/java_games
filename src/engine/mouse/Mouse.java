package engine.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import engine.Game;

public class Mouse implements MouseListener {
	
	private boolean[] keys;

	private long[] keyPressedTime;
	
	private int x;
	
	private int y;
	
	public Mouse() {
		keys = new boolean[MouseEvent.MOUSE_LAST];
		keyPressedTime = new long[MouseEvent.MOUSE_LAST];
		for(int i = 0; i < keys.length; i++) {
			keyPressedTime[i] = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		keys[e.getButton()] = true;		
		keyPressedTime[e.getButton()] = Game.clock.elapsedMillis();
		x = e.getX();
		y = e.getY();
		// System.out.println("clicked: " + x + " " + y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		keys[e.getButton()] = false;	
		keyPressedTime[e.getButton()] = -1;
		x = e.getX();
		y = e.getY();
	}
	
	public long mouseClickedTime(int e) {
		return keyPressedTime[e];
	}
	
	public boolean isMouseClicked(int e) {
		return keys[e];
	}

//	public boolean isMouseReleased(int e) {
//		return keysReleased[e];
//	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
}
