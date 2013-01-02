package engine.map.tiled;

import java.awt.Graphics2D;


public class MetaTile extends AbstractTile {

	public MetaTile(int value) {
		super(value);
		collisionOffset = 0;
	}

	@Override
	public int width() {
		return 16;
	}

	@Override
	public int height() {
		return 16;
	}

	@Override
	public void draw(Graphics2D g, int x, int y) {
		locate(x, y);		
	}

}
