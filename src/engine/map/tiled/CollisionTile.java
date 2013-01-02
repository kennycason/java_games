package engine.map.tiled;

import java.awt.Graphics2D;

public class CollisionTile extends AbstractTile {

	public CollisionTile(int value) {
		super(value);
		locate(-1, -1);
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
