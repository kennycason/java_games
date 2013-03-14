package engine.graphics.shape;

import javax.media.opengl.GL2;

public class Square extends AbstractShape {
	
	private int dim;
	
	public Square(int dim) {
		this.dim = dim;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(x(), y(), 0);
			gl.glVertex3f(x() + dim, y(), 0);
			gl.glVertex3f(x() + dim, y() + dim, 0);
			gl.glVertex3f(x(), y() + dim, 0);
		gl.glEnd();
	}

	@Override
	public int mapX() {
		return 0;
	}

	@Override
	public int mapY() {
		return 0;
	}

	@Override
	public int width() {
		return dim;
	}

	@Override
	public int height() {
		return dim;
	}

}
