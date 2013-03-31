package engine.graphics.shape;

import javax.media.opengl.GL2;

import engine.math.Vector2D;

public class Polygon extends AbstractShape {
	
	private int size;
	
	private double smallestX = Integer.MAX_VALUE;
	
	private double largestX = Integer.MIN_VALUE;
	
	private double smallestY = Integer.MAX_VALUE;
	
	private double largestY = Integer.MIN_VALUE;
	
	private Vector2D[] vectors;
	
	public Polygon(Vector2D ... vectors) {
		size = vectors.length;
		this.vectors = new Vector2D[size];
		for(int i = 0; i < size; i++) {
			this.vectors[i] = vectors[i].clone();
			if(vectors[i].x() < smallestX) {
				smallestX = vectors[i].x();
			}
			if(vectors[i].x() < largestX) {
				largestX = vectors[i].x();
			}
			if(vectors[i].y() < smallestY) {
				smallestY = vectors[i].y();
			}
			if(vectors[i].y() < largestY) {
				largestY = vectors[i].y();
			}	
		}
	}

	@Override
	public void draw(GL2 gl) {
		gl.glLoadIdentity();
		gl.glTranslated(x(), y(), 0);
		gl.glRotated(rot.x(), 1.0, 0, 0);
		gl.glRotated(rot.y(), 0, 1.0, 0);
		gl.glRotated(rot.z(), 0, 0, 1.0);
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		
		gl.glBegin(GL2.GL_POLYGON);
		for (Vector2D v : vectors) {
			gl.glVertex3d(v.x(), v.y(), 0);
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
		return (int)(largestX - smallestX);
	}

	@Override
	public int height() {
		return (int)(largestY - smallestY);
	}

}
