package engine.opengl;


import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;

import engine.graphics.shape.Cube;
import engine.graphics.shape.Circle;
import engine.graphics.shape.GradientCircle;
import engine.graphics.shape.Polygon;
import engine.graphics.shape.Square;
import engine.graphics.shape.Triangle;
import engine.graphics.sprite.SimpleSprite;
import engine.graphics.sprite.SpriteBank;
import engine.graphics.sprite.SpriteSheet;
import engine.math.Vector2D;

class Screen implements GLEventListener {

	private int rot = 0;
	
	private Square square = new Square(20);
	
	private Square square2 = new Square(20);

	private Triangle triangle = new Triangle(100, 100);
	
	private SimpleSprite sprite;
	
	private Cube cube = new Cube(100);
	
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
		SpriteBank sprites = new SpriteBank();
		sprites.set("entities", new SpriteSheet("sprites/entity/zelda/entities.png", 16, 16));
		SpriteSheet sheet = (SpriteSheet) sprites.get("entities");
		
		sprite = sheet.get(1);
		
		cube.locate(250, 250);
		cube.color(Color.RED);

		rot = 0;
		
		square.color(Color.RED);
		square.locate(100, 100);
		square.rotate(0, 0, rot);
		
		square2.color(Color.RED);
		square2.locate(150, 100);
		
		triangle.color(Color.GREEN);
		triangle.locate(200, 50);
		
		polygon.color(Color.BLUE);
		
		circle.color(0x445522);
		circle.locate(200,200);
		
		gCircle.locate(300, 300);
		
		this.width = width;
		this.height = height;
	}

	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		// light shiz
//		float[] lightPos = new float[4];
//		lightPos[0] = 50005;
//	    lightPos[1] = 30000;
//	    lightPos[2] = 50000;
//	    lightPos[3] = 1;
//	    gl.glEnable(GLLightingFunc.GL_LIGHTING);
//	    gl.glEnable(GLLightingFunc.GL_LIGHT0);
//	    float[] noAmbient ={ 0.1f, 0.1f, 0.1f, 1f }; // low ambient light
//	    float[] spec =    { 1f, 0.6f, 0f, 1f }; // low ambient light
//	    float[] diffuse ={ 1f, 1f, 1f, 1f };
//	    // properties of the light
//	    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, noAmbient, 0);
//	    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, spec, 0);
//	    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
//	    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPos, 0);
//		
		polygon.draw(gl);
		triangle.draw(gl);
		square.draw(gl);
			
		square2.draw(gl);
		circle.draw(gl);
		gCircle.draw(gl);
		sprite.draw(gl, 230, 230);
		sprite.draw(gl, 430, 230);
		cube.rotate(0, 0, 30);
		cube.draw(gl);
		
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
