package engine.entity.weapon;

import engine.Game;

public abstract class AbstractWeapon extends AbstractUsableEntity {
	
	protected double damage;
	
	protected AbstractWeapon(Game game) {
		super(game);
	}
	
	public double damage() {
		return damage;
	}
	
}
