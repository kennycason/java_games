package engine.entity;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.ai.IAIStrategy;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteUtils;

public abstract class AbstractLivingEntity extends AbstractEntity {

	/**
	 * an Artificial Intelligence strategy for non playable entities
	 */
	protected IAIStrategy strategy;
	
	protected AnimatedSprite spriteCurrent;
	
	protected AnimatedSprite spriteE;
	protected AnimatedSprite spriteW;
	protected AnimatedSprite spriteN;
	protected AnimatedSprite spriteS;
	
	protected FaceDirection lastFace;
	
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
	
	/**
	 * is entity dead?
	 */
	protected boolean dead = false;
	
	protected AbstractSound hitSound;
	
	protected AbstractSound deadSound;
	
	public AbstractLivingEntity() {
		super();
	}

	@Override
	public void draw(Graphics2D g) {
		if(!invincible) {
			spriteCurrent.draw(g, renderX(), renderY());
		} else {
			if(flicker) {
				// @TODO find better way to do this without creating a new sprite each time
				SimpleSprite neg = SpriteUtils.negative(spriteCurrent.currentSprite());
				neg.draw(g, renderX(), renderY());
				neg = null;	
				flicker = false;
				flickerCount++;
			} else {
				spriteCurrent.draw(g, renderX(), renderY());
				if(flickerCount < maxFlickerCount) {
					flicker = true;
				}
			}	
		}
	}
	
	
	public double life() {
		return life;
	}
	
	public void life(double life) {
		this.life = life;
	}
	
	public double maxLife() {
		return maxLife;
	}
	
	public void maxLife(double maxLife) {
		this.maxLife = maxLife;
	}
	
	public double damage() {
		return damage;
	}
	
	public FaceDirection lastFace() {
		return lastFace;
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
	
	@Override
	public int width() {
		return spriteCurrent.width();
	}
	
	@Override
	public int height() {
		return spriteCurrent.height();
	}
	
	@Override
	public void face(FaceDirection face) {
		lastFace = this.face;
		this.face = face;
		switch(face) {
			case NORTH:
				spriteCurrent = spriteN;
				break;
			case EAST:
				spriteCurrent = spriteE;
				break;
			case SOUTH:
				spriteCurrent = spriteS;
				break;
			case WEST:
				spriteCurrent = spriteW;
				break;
		}
	}
	
}
