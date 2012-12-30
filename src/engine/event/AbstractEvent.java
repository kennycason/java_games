package engine.event;

import engine.Game;
import engine.GameFactory;

public abstract class AbstractEvent {

	protected Game game;
	
	protected boolean happened;
	
	public AbstractEvent() {
		happened = false;
		game = GameFactory.get();
	}
	
	public abstract void deploy();
	
	public abstract boolean ready();
	
	public boolean happened() {
		return happened;
	}
	
}
