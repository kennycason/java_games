package engine.map;

import java.awt.Graphics2D;

import engine.sprite.SimpleSprite;

public class BasicTile extends AbstractTile {

	private SimpleSprite sprite;
	
	public BasicTile(SimpleSprite sprite, int value) {
		super(value);
		this.sprite = sprite;
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
	public void draw(Graphics2D g, int x, int y) {
		locate(x, y);
		sprite.draw(g, x, y);
	}

}
