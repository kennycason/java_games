package engine.event;


public abstract class AbstractEnemiesDeadEvent extends AbstractEvent {
	
	public AbstractEnemiesDeadEvent() {
		super();
	}

	@Override
	public boolean ready() {
		// still more enemies to come!
		for(AbstractEvent event : game.map().events().all()) {
			if(event instanceof IEnemyEvent) {
				return false;
			}
		}
		return game.map().enemies().size() == 0;
	}

}
