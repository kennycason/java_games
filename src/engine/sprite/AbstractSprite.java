package engine.sprite;

import engine.entity.AbstractCollidable;

public abstract class AbstractSprite extends AbstractCollidable implements ISprite, ISpriteResource {

	protected int spriteWidth;

	protected int spriteHeight;

	@Override
	public int width() {
		return spriteWidth;
	}

	@Override
	public int height() {
		return spriteHeight;
	}
	
}
