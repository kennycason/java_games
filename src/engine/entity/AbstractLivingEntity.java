package engine.entity;

import engine.Game;
import engine.ai.IAIStrategy;
import engine.sound.ISound;

public abstract class AbstractLivingEntity extends AbstractEntity {

	/**
	 * an Artificial Intelligence strategy for non playable entities
	 */
	protected IAIStrategy strategy;
	
	/**
	 * amount of damage this entity does
	 */
	protected double damage = 0;
	
	/**
	 * set to true after getting hit, to prevent taking too much damage in a short amount of time
	 */
	protected boolean invincible = false;
	
	/**
	 * recovery time after getting hit
	 */
	protected long invincibleTime = 1000;
	
	/**
	 * flicker image after gettting hit
	 */
	protected boolean flicker = false;
	
	protected int flickerCount = 0;
	
	protected int maxFlickerCount = 2;
	 
	/**
	 * last time hit
	 * @param game
	 */
	protected long lastTimeHit = -1;
	
	/**
	 * life
	 */	
	protected double maxLife = 5;
	
	protected double life = maxLife;
	
	protected ISound hitSound;
	
	protected ISound deadSound;
	/**
	 * is entity dead?
	 */
	protected boolean dead = false;
	
	public AbstractLivingEntity(Game game) {
		super(game);
	}

	public double life() {
		return life;
	}
	
	public double maxLife() {
		return maxLife;
	}
	
	public double damage() {
		return damage;
	}
	
	public void hit(double damage) {
		if(!invincible) {
			hitSound.play();
			life -= damage;
			if(life <= 0) {
				dead = true;
				deadSound.play();
			}
			invincible = true;
			lastTimeHit = System.currentTimeMillis();
		}
	}
	
	public boolean dead() {
		return dead;
	}
	
	public boolean alive() {
		return !dead;
	}
	
	protected void setAIStrategy(IAIStrategy strategy) {
		this.strategy = strategy;
	}
	
}
