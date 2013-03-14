package test.engine.opengl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;


public class OpenGLTest {
	
	public static void main(String[] args) {
		new OpenGLTest();
	}

	public OpenGLTest() {
    	// setup OpenGL Version 2
//    	GLProfile profile = GLProfile.get(GLProfile.GL2);
//    	GLCapabilities capabilities = new GLCapabilities(profile);
// 
//    	// The canvas is the widget that's drawn in the JFrame
//    	GLCanvas glcanvas = new GLCanvas(capabilities);
//    	glcanvas.addGLEventListener(new Screen(640, 480));
//    	glcanvas.setSize( 640, 480 );
//    	
//        JFrame frame = new JFrame( "Hello World" );
//        frame.getContentPane().add( glcanvas);
// 
//        // shutdown the program on windows close event
//        frame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent ev) {
//               // System.exit(0);
//            }
//        });
// 
//        frame.setSize( frame.getContentPane().getPreferredSize() );
//        frame.setVisible( true );
//    	
	}

}
