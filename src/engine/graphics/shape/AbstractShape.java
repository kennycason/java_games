package engine.graphics.shape;

import java.awt.Color;

import javax.media.opengl.GL2;

import engine.math.Vector3D;
import engine.entity.AbstractCollidable;

public abstract class AbstractShape extends AbstractCollidable {
	
	protected Color color;
	
	protected Vector3D rot;
	
	protected AbstractShape() {
		super();
		rot = new Vector3D();
		color = Color.WHITE;
	}
	
	public abstract void draw(final GL2 gl);
	
	public void rotate(double x, double y, double z) {
		rot.x(x);
		rot.y(y);
		rot.z(z);
	}
	
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
