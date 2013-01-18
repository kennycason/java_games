package engine.graphics.shape;

import javax.media.opengl.GL2;

public class EmptyCircle extends AbstractShape {
	
	private final static float DEG2RAD = (float) (Math.PI / 180);
			
	private int radius;
	
	public EmptyCircle(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		gl.glLineWidth(1f);
		gl.glBegin(GL2.GL_LINE_LOOP); 
		   for (int i = 0; i <= 360; i++) {
		      float degInRad = i * DEG2RAD;
		      gl.glVertex2d(x() + Math.cos(degInRad) * radius, y() + Math.sin(degInRad) * radius);
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
