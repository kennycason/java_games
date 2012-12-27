package engine.entity;

import engine.Game;
import engine.sprite.AnimatedSprite;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteUtils;

import java.awt.Graphics2D;

public abstract class AbstractSingleFacingAnimatedEntity extends AbstractLivingEntity {

	protected AnimatedSprite sprite;
	
	public AbstractSingleFacingAnimatedEntity(Game game) {
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
			sprite.draw(g, x() + (int)game.map().offset().x(), y() + (int)game.map().offset().y());
		} else {
			if(flicker) {
				// @TODO find better way to do this without creating a new sprite each time
				SimpleSprite neg = SpriteUtils.negative(sprite.currentSprite());
				neg.draw(g, x(), y());
				neg = null;	
				flicker = false;
				flickerCount++;
			} else {
				sprite.draw(g, x(), y());
				if(flickerCount < maxFlickerCount) {
					flicker = true;
				}
			}	
		}
	}
	
}

