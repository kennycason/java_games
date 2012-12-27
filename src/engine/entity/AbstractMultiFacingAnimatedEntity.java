package engine.entity;

import engine.FaceDirection;
import engine.Game;
import engine.sprite.AnimatedSprite;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteUtils;

import java.awt.Graphics2D;

public abstract class AbstractMultiFacingAnimatedEntity extends AbstractLivingEntity {

	protected AnimatedSprite sprite;
	
	protected AnimatedSprite spriteE;
	protected AnimatedSprite spriteW;
	protected AnimatedSprite spriteN;
	protected AnimatedSprite spriteS;
	
	protected FaceDirection face;

	public AbstractMultiFacingAnimatedEntity(Game game) {
		super(game);
	}
	
	@Override
	public int width() {
		return sprite.width();
	}

	@Override
	public int height() {
		return sprite.height();
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(!invincible) {
			sprite.draw(g, renderX(), renderY());
		} else {
			if(flicker) {
				// @TODO find better way to do this without creating a new sprite each time
				SimpleSprite neg = SpriteUtils.negative(sprite.currentSprite());
				neg.draw(g, renderX(), renderY());
				neg = null;	
				flicker = false;
				flickerCount++;
			} else {
				sprite.draw(g, renderX(), renderY());
				if(flickerCount < maxFlickerCount) {
					flicker = true;
				}
			}	
		}
	}
	
}