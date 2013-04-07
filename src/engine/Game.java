package engine;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.clock.Clock;
import engine.config.ConfigBank;
import engine.entity.usable.UsableBank;
import engine.font.FontBank;
import engine.graphics.sprite.SimpleSprite;
import engine.graphics.sprite.SpriteBank;
import engine.il8n.StringBank;
import engine.keyboard.DefaultKeyEventDispatcher;
import engine.keyboard.KeyBoard;
import engine.map.tiled.Map;
import engine.map.tiled.TiledMapLoader;
import engine.mouse.Mouse;
import engine.sound.SoundBank;
import game.cubes.GLScreen;
import game.zelda.player.Link;

public abstract class Game extends JPanel {

	protected static final long serialVersionUID = 1L;

	public static int SCREEN_WIDTH = 240;

	public static int SCREEN_HEIGHT = 240;
	
	public static Locale locale;
	
	public static StringBank strings;
	
	public static SoundBank sounds;
	
	public static SpriteBank sprites;
	
	public static FontBank fonts;
	
	public static UsableBank usables;
	
	public static ConfigBank config;
	
	public static Clock clock;
	
	public static KeyBoard keyboard;
	
	public static Mouse mouse;
	
	protected static int ZOOM = 2;

	protected final SimpleSprite screen;
	
	protected GLCanvas glcanvas; // will be moving to only use OpenGL in the future;
	
	protected JFrame frame;

	protected final JLabel screenPanel = new JLabel();

	protected Map map;
	
	protected TiledMapLoader loader;
	
	protected GameStateEnum gameState;
	
	protected HashMap<GameStateEnum, AbstractGameLoop> gameLoops;

	protected Game() {
		this(SCREEN_WIDTH, SCREEN_HEIGHT, 2);
	}
	
	protected Game(int width, int height, int zoom) {
		super(true);
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.ddscale", "true");
		System.setProperty("sun.java2d.translaccel", "true");
		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;
		ZOOM = zoom;
		this.setLayout(new GridLayout());
		this.setPreferredSize(new Dimension(SCREEN_WIDTH * zoom, SCREEN_HEIGHT * zoom));
		this.setIgnoreRepaint(true);
		
		screen = new SimpleSprite(new BufferedImage(SCREEN_WIDTH * zoom, SCREEN_HEIGHT * zoom, BufferedImage.TYPE_INT_ARGB));
		screenPanel.setIcon(new ImageIcon(screen.bufferedImage()));
		screenPanel.setDoubleBuffered(true);
		this.add(screenPanel);
		
		config = new ConfigBank();
		if(config.contains("locale")) {
			locale = new Locale(config.getString("locale"));
		} else {
			locale = new Locale("en");
		}
		strings = new StringBank(locale);
		sprites = new SpriteBank();
		sounds = new SoundBank();
		fonts = new FontBank();
		usables = new UsableBank();
		clock = Clock.getInstance();
		keyboard = new KeyBoard();
		mouse = new Mouse();
		
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new DefaultKeyEventDispatcher(keyboard));
        this.addMouseListener(mouse);
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		loader = new TiledMapLoader();
		gameState = GameStateEnum.TITLE_SCREEN;
		gameLoops = new HashMap<GameStateEnum, AbstractGameLoop>();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		
//    	GLProfile profile = GLProfile.get(GLProfile.GL2);
//    	GLCapabilities capabilities = new GLCapabilities(profile);
//    	
//        frame = new JFrame( "Hello World" );
// 
//    	// The canvas is the widget that's drawn in the JFrame
//    	glcanvas = new GLCanvas(capabilities);
//    	glcanvas.addGLEventListener(new GLScreen(460, 350));
//    	glcanvas.setSize(640, 480);
//        frame.getContentPane().add( glcanvas);
//        
//        // shutdown the program on windows close event
//        frame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent ev) {
//               // System.exit(0);
//            }
//        });
//        frame.setSize( frame.getContentPane().getPreferredSize() );
//        frame.setVisible( true );
	}
	
	public void start() {
		frame.setVisible(true);
	}
	
	public abstract void run();

	public Map map() {
		return map;
	}
	
	public void map(Map map) {
		this.map = map;
	}

	public void gameState(GameStateEnum gameState) {
		this.gameState = gameState;
	}
	
	public JLabel screenPanel() {
		return screenPanel;
	}
	
	public SimpleSprite screen() {
		return screen;
	}

	public static int zoom() {
		return ZOOM;
	}
	
	public HashMap<GameStateEnum, AbstractGameLoop> gameLoops() {
		return gameLoops;
	}

	public void sleep(int time) {
		try {
			clock.pause();
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			clock.start();
		}
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public GLCanvas glCanvas() {
		return glcanvas;
	}
	
}
