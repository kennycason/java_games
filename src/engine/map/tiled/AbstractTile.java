package engine.map.tiled;

import java.awt.Graphics2D;

import engine.entity.AbstractCollidable;

public abstract class AbstractTile extends AbstractCollidable implements ITile {
	
	protected int value;
	
	protected AbstractTile(int value) {
		this.value = value;
		collisionOffset = 0;
	}
	
	@Override
	public int value() {
		return value;
	}
	
	@Override
	public void value(int value) {
		this.value = value;
	}
	
	@Override
	public int mapX() {
		return 0;
	}

	@Override
	public int mapY() {
		return 0;
	}
	
	public abstract void draw(Graphics2D g, int x, int y);

}
