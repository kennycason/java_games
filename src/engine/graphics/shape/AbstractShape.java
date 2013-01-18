package engine.graphics.shape;

import java.awt.Color;

import javax.media.opengl.GL2;

import engine.entity.AbstractCollidable;

public abstract class AbstractShape extends AbstractCollidable {
	
	protected Color color;
	
	public abstract void draw(final GL2 gl);
	
	public Color color() {
		return color;
	}

	public void color(int rgb) {
		color = new Color(rgb);
	}
	
	public void color(Color color) {
		this.color = color;
	}
	
}
