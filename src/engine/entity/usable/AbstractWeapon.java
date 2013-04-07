package engine.entity.usable;

import engine.entity.AbstractLivingEntity;


public abstract class AbstractWeapon extends AbstractUsableEntity {
	
	protected double damage;
	
	public AbstractWeapon(AbstractLivingEntity user) {
		super(user);
	}
	
	public double damage() {
		return damage;
	}
	
}
