package engine.entity.usable;


public abstract class AbstractWeapon extends AbstractUsableEntity {
	
	protected double damage;
	
	public AbstractWeapon() {
		super();
	}
	
	public double damage() {
		return damage;
	}
	
}
