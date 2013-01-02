package engine.event;

import engine.Game;
import engine.GameFactory;

public abstract class AbstractEvent implements IEvent {

	protected Game game;
	
	protected boolean happened;
	
	public AbstractEvent() {
		happened = false;
		game = GameFactory.get();
	}
	
	public boolean happened() {
		return happened;
	}
	
}
