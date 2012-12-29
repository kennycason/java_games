package engine.entity;

import engine.FaceDirection;
import engine.sprite.AnimatedSprite;

public abstract class AbstractSimpleEntity extends AbstractEntity {

	protected AnimatedSprite sprite;
	
	public AbstractSimpleEntity() {
		super();
	}

	@Override
	public void handle() {
		
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
	public void face(FaceDirection face) {
		this.face = face;
	}
	
}

