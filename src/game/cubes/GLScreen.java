package game.cubes;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import engine.graphics.shape.Polygon;
import engine.math.Vector2D;

public class GLScreen implements GLEventListener {

	private int width;

	private int height;
	
	private Polygon polygon = new Polygon(
			new Vector2D(10, 10),
			new Vector2D(40, 40),
			new Vector2D(200, 100),
			new Vector2D(100, 200),
			new Vector2D(30, 150),
			new Vector2D(10, 50)
		);
	

	public GLScreen(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		polygon.draw(gl);
		gl.glFlush();
	}


	public void init(GLAutoDrawable gLDrawable) {
		System.out.println("init() called");
		GL2 gl = gLDrawable.getGL().getGL2();
		// Projection mode is for setting camera
		gl.glMatrixMode(GL2.GL_PROJECTION);
		// This will set the camera for orthographic projection and allow 2D
		// view
		// Our projection will be on 400 X 400 screen
		gl.glLoadIdentity();
		gl.glOrtho(0, width, height, 0, 0, 1);
		// Modelview is for drawing
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		// Depth is disabled because we are drawing in 2D
		gl.glDisable(GL.GL_DEPTH_TEST);
		// Setting the clear color (in this case black)
		// and clearing the buffer with this set clear color
		// gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		//gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// This defines how to blend when a transparent graphics
		// is placed over another (here we have blended colors of
		// two consecutively overlapping graphic objects)
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_BLEND);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		System.out.println("reshape() called: x = " + x + ", y = " + y + ", width = " + width + ", height = " + height);
	}

	public void dispose(GLAutoDrawable arg0) {
		System.out.println("dispose() called");
	}
	
	
}
