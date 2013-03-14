package engine.graphics.sprite;

import java.awt.Graphics;

public interface ISprite {
	
	void draw(Graphics g, int x, int y);

	int width();
	
	int height();
	
}
