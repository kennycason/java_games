package engine.dialog;

import engine.event.AbstractEvent;
import engine.sound.AbstractSound;
import engine.Game;
import engine.GameFactory;

public abstract class AbstractDialog extends AbstractEvent {
	
	protected Game game; 
	
	protected int textSpeed;
	
	protected boolean finished;
	
	protected long lastDraw;
	
	protected int x;
	
	protected int y; 
	
	protected int width;
	
	protected int height;
	
	protected int currentChar;
	
	//protected int yOffs
	
	protected int page;
	
	protected int phase;
	
	protected String[] pages;
	
	protected AbstractSound textTyped;
	
	protected AbstractSound pageFinished;
	
	/**
	 * Create a Single Paged Dialog
	 * @param text
	 * @param x
	 * @param y
	 */
	public AbstractDialog(String text, int x, int y, int width, int height, int textSpeed) {
		this(new String[] {text}, x, y, width, height, textSpeed);
	}
	
	public AbstractDialog(String[] text, int x, int y, int width, int height, int textSpeed) {
		game = GameFactory.get();
		pages = text;
		finished = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textSpeed = textSpeed;
		currentChar = 1;
		page = 0;
		phase = 1; // 1 draw text, 2, wait for user to press enter
		textTyped = Game.sounds.get("dialog_typing");
		pageFinished = Game.sounds.get("dialog_finished");
	}
	
}
