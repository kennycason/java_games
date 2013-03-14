package engine.graphics.shape;

import javax.media.opengl.GL2;

public class Triangle extends AbstractShape {
	
	private int width;
	
	private int height;
	
	public Triangle(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(x(), y() + height, 0.0f);
		gl.glVertex3f(x() + width / 2.0f, y(), 0.0f);
		gl.glVertex3f(x() + width, y() + height, 0.0f);
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
		return width;
	}

	@Override
	public int height() {
		return height;
	}

}
