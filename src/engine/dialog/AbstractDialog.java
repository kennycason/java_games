package engine.dialog;

import java.awt.Graphics2D;

import engine.Game;
import engine.GameFactory;

public abstract class AbstractDialog {
	protected Game game; 
	
	public AbstractDialog() {
		game = GameFactory.get();
	}
	
	public void handle() {

	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract int width();
	
	public abstract int height();
	
}
