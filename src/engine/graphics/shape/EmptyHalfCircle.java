package engine.graphics.shape;

import javax.media.opengl.GL2;

public class EmptyHalfCircle extends AbstractShape {
		
	private int radius;
	
	public EmptyHalfCircle(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		gl.glLineWidth(1f);
		gl.glBegin(GL2.GL_LINE_LOOP); 
		   for (int i = 0; i <= 100; i++) {
		      float angle = (float) (i * Math.PI / 100.0);
		      gl.glVertex2d(x() + (Math.cos(angle) * radius), y() + (Math.sin(angle) * radius));
		   }
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
