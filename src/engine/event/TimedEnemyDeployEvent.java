package engine.event;

import engine.entity.enemy.AbstractEnemy;

public class TimedEnemyDeployEvent extends AbstractTimedDeployableEvent implements IEnemyEvent {
	
	protected AbstractEnemy enemy;
	
	public TimedEnemyDeployEvent(AbstractEnemy enemy, long deployTime) {
		super(deployTime);
		this.enemy = enemy;
	}

	@Override
	public void deploy() {
		game.map().enemies().add(enemy);
	}

}
