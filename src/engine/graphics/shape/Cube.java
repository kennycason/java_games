package engine.graphics.shape;

import javax.media.opengl.GL2;

public class Cube extends AbstractShape {

	private int dim;
	
	public Cube(int dim) {
		this.dim = dim;
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glTranslated(x(), y(), 0);
		gl.glRotated(rot.x(), 1.0, 0, 0);
		gl.glRotated(rot.y(), 0, 1.0, 0);
		gl.glRotated(rot.z(), 0, 0, 1.0);
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		
		gl.glBegin(GL2.GL_POLYGON);/* f1: front */
			gl.glNormal3f(-dim, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, dim);
			gl.glVertex3f(dim, 0.0f, dim);
			gl.glVertex3f(dim, 0.0f, 0.0f);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);/* f2: bottom */
			gl.glNormal3f(0.0f, 0.0f, -dim);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(dim, 0.0f, 0.0f);
			gl.glVertex3f(dim, dim, 0.0f);
			gl.glVertex3f(0.0f, dim, 0.0f);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);/* f3:back */
			gl.glNormal3f(dim, 0.0f, 0.0f);
			gl.glVertex3f(dim, dim, 0.0f);
			gl.glVertex3f(dim, dim, dim);
			gl.glVertex3f(0.0f, dim, dim);
			gl.glVertex3f(0.0f, dim, 0.0f);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);/* f4: top */
			gl.glNormal3f(0.0f, 0.0f, dim);
			gl.glVertex3f(dim, dim, dim);
			gl.glVertex3f(dim, 0.0f, dim);
			gl.glVertex3f(0.0f, 0.0f, dim);
			gl.glVertex3f(0.0f, dim, dim);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);/* f5: left */
			gl.glNormal3f(0.0f, dim, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, dim, 0.0f);
			gl.glVertex3f(0.0f, dim, dim);
			gl.glVertex3f(0.0f, 0.0f, dim);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);/* f6: right */
			gl.glNormal3f(0.0f, -dim, 0.0f);
			gl.glVertex3f(dim, 0.0f, 0.0f);
			gl.glVertex3f(dim, 0.0f, dim);
			gl.glVertex3f(dim, dim, dim);
			gl.glVertex3f(dim, dim, 0.0f);
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
