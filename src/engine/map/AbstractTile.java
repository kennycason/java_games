package engine.map;

import java.awt.Graphics2D;

import engine.entity.AbstractCollidable;

public abstract class AbstractTile extends AbstractCollidable {
	
	protected int value;
	
	protected AbstractTile(int value) {
		this.value = value;
		collisionOffset = 0;
	}
	
	public int value() {
		return value;
	}
	
	public void value(int value) {
		this.value = value;
	}
	
	public abstract void draw(Graphics2D g, int x, int y);

}
