package engine.event;

import engine.Game;


public abstract class AbstractTimedDeployableEvent extends AbstractEvent {
	
	protected long createdTime;
	
	protected long deployTime;
	
	public AbstractTimedDeployableEvent(long deployTime) {
		this.deployTime = deployTime;
		createdTime = Game.clock.elapsedMillis();
	}

	@Override
	public boolean ready() {
		return Game.clock.elapsedMillis() - createdTime > deployTime;
	}

}
