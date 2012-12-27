package engine.keyboard;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class DefaultKeyEventDispatcher implements KeyEventDispatcher {
	
	private KeyBoard keyboard;
	
	public DefaultKeyEventDispatcher(KeyBoard keyboard) {
		this.keyboard = keyboard;
	}
	
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            keyboard.keyPressed(e);
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            keyboard.keyReleased(e);
        } else if (e.getID() == KeyEvent.KEY_TYPED) {
            keyboard.keyTyped(e);
        }
        return false;
    }
    
}