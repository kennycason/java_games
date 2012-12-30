package engine.event;

public abstract class AbstractTimedDeployableEvent extends AbstractEvent {
	
	protected long createdTime;
	
	protected long deployTime;
	
	public AbstractTimedDeployableEvent(long deployTime) {
		this.deployTime = deployTime;
		createdTime = System.currentTimeMillis();
	}

	@Override
	public boolean ready() {
		return System.currentTimeMillis() - createdTime > deployTime;
	}

}
