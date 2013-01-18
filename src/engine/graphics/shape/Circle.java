package engine.graphics.shape;

import javax.media.opengl.GL2;

public class Circle extends AbstractShape {

	private static double increment = 2 * Math.PI / 50;

	private int radius;
	
	private int centerX;
	
	private int centerY;

	public Circle(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		// Starting loop for drawing triangles
		centerX = x() + radius;
		centerY = y() + radius;
		for (double angle = 0; angle < 2 * Math.PI; angle += increment) {
			gl.glBegin(GL2.GL_POLYGON);
			// One vertex of each triangle is at center of circle
			gl.glVertex2d(centerX, centerY);
			// Other two vertices form the periphery of the circle
			gl.glVertex2d(centerX + Math.cos(angle) * radius, centerY + Math.sin(angle) * radius);
			gl.glVertex2d(centerX + Math.cos(angle + increment) * radius, centerY + Math.sin(angle + increment) * radius);
			gl.glEnd();
		}
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
		return radius * 2;
	}

	@Override
	public int height() {
		return radius * 2;
	}

	public int radius() {
		return radius;
	}

}
