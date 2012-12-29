package engine.entity.weapon;


public abstract class AbstractWeapon extends AbstractUsableEntity {
	
	protected double damage;
	
	public AbstractWeapon() {
		super();
	}
	
	public double damage() {
		return damage;
	}
	
}
