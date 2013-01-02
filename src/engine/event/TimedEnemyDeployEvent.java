package engine.event;

import engine.entity.enemy.AbstractEnemy;

public class TimedEnemyDeployEvent extends AbstractTimedDeployableEvent {
	
	protected AbstractEnemy enemy;
	
	public TimedEnemyDeployEvent(AbstractEnemy enemy, long deployTime) {
		super(deployTime);
		this.enemy = enemy;
	}

	@Override
	public void trigger() {
		game.map().enemies().add(enemy);
	}

}
