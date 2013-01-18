package engine.opengl;


import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import engine.graphics.shape.GradientCircle;
import engine.graphics.shape.Circle;
import engine.graphics.shape.Polygon;
import engine.graphics.shape.Square;
import engine.graphics.shape.Triangle;
import engine.math.Vector2D;

class Screen implements GLEventListener {

	private Square square = new Square(20);

	private Triangle triangle = new Triangle(100, 100);
	
	private Polygon polygon = new Polygon(
			new Vector2D(10, 10),
			new Vector2D(40, 40),
			new Vector2D(200, 100),
			new Vector2D(100, 200),
			new Vector2D(30, 150),
			new Vector2D(10, 50)
		);
	
	private Circle circle = new Circle(50);
	
	private GradientCircle gCircle = new GradientCircle(70, Color.BLUE, Color.ORANGE);

	private int width;

	private int height;

	public Screen(int width, int height) {
		square.color(0xff0000);
		square.locate(100, 100);
		triangle.color(0x00ff00);
		triangle.locate(200, 50);
		polygon.color(0x0000bb);
		circle.color(0x445522);
		circle.locate(200,200);
		gCircle.locate(300, 300);
		this.width = width;
		this.height = height;
	}

	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		polygon.draw(gl);
		triangle.draw(gl);
		square.draw(gl);
		circle.draw(gl);
		gCircle.draw(gl);
		gl.glFlush();
	}

	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged,
			boolean deviceChanged) {
		System.out.println("displayChanged called");
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
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// This defines how to blend when a transparent graphics
		// is placed over another (here we have blended colors of
		// two consecutively overlapping graphic objects)
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_BLEND);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width,
			int height) {
		this.width = width;
		this.height = height;
		System.out.println("reshape() called: x = " + x + ", y = " + y
				+ ", width = " + width + ", height = " + height);
	}

	public void dispose(GLAutoDrawable arg0) {
		System.out.println("dispose() called");
	}
	
}
