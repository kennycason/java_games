package engine.sprite;

import engine.entity.AbstractCollidable;

public abstract class AbstractSprite extends AbstractCollidable implements ISprite, ISpriteBankResource {

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
	
	@Override
	public int mapX() {
		return 0;
	}

	@Override
	public int mapY() {
		return 0;
	}
}
