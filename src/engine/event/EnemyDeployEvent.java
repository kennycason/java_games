package engine.event;

import engine.entity.enemy.AbstractEnemy;

public class EnemyDeployEvent extends AbstractEvent implements IEnemyEvent {
	
	protected AbstractEnemy enemy;
	
	public EnemyDeployEvent(AbstractEnemy enemy) {
		super();
		this.enemy = enemy;
	}

	@Override
	public void trigger() {
		game.map().enemies().add(enemy);
	}

	@Override
	public boolean ready() {
		return true;
	}

}
