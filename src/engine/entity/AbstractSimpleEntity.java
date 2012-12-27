package engine.entity;

import engine.FaceDirection;
import engine.Game;
import engine.sprite.SimpleSprite;

public abstract class AbstractSimpleEntity extends AbstractEntity {

	protected SimpleSprite sprite;
	
	public AbstractSimpleEntity(Game game) {
		super(game);
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
	public void locate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void face(FaceDirection face) {
		this.face = face;
	}
	
}

