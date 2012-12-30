package engine;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.keyboard.DefaultKeyEventDispatcher;
import engine.keyboard.KeyBoard;
import engine.map.Map;
import engine.map.MapLoader;
import engine.sprite.SimpleSprite;
import game.zelda.player.Link;

public abstract class Game extends JPanel {

	protected static final long serialVersionUID = 1L;

	public static final int SCREEN_WIDTH = 240;

	public static final int SCREEN_HEIGHT = 240;
	
	protected int zoom = 2;

	protected final SimpleSprite screen;

	protected final JLabel screenPanel = new JLabel();

	protected final KeyBoard keyboard;
	
	// @TODO create Interface for Player to decouple Game and Link
	protected Link link;
	
	protected Map map;
	
	protected MapLoader loader;
	
	protected GameStateEnum gameState;
	
	protected HashMap<GameStateEnum, AbstractGameLoop> gameLoops;

	protected Game() {
		super(true);
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.ddscale", "true");
		System.setProperty("sun.java2d.translaccel", "true");
		this.setLayout(new GridLayout());
		this.setPreferredSize(new Dimension(SCREEN_WIDTH * zoom, SCREEN_HEIGHT * zoom));
		this.setIgnoreRepaint(true);
		screen = new SimpleSprite(new BufferedImage(SCREEN_WIDTH * zoom, SCREEN_HEIGHT * zoom, BufferedImage.TYPE_INT_ARGB));
		screenPanel.setIcon(new ImageIcon(screen.bufferedImage()));
		screenPanel.setDoubleBuffered(true);
		this.add(screenPanel);
		
		keyboard = new KeyBoard();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new DefaultKeyEventDispatcher(keyboard));
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		loader = new MapLoader();
		gameState = GameStateEnum.TITLE_SCREEN;
		gameLoops = new HashMap<GameStateEnum, AbstractGameLoop>();
	}
	
	public void start() {
		JFrame frame = new JFrame();
		frame.setTitle("Legend of Zelda");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public abstract void run();

	public Link link() {
		return link;
	}
	
	public void link(Link link) {
		this.link = link;
	}

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

	public KeyBoard keyboard() {
		return keyboard;
	}

	public int zoom() {
		return zoom;
	}
	
	public HashMap<GameStateEnum, AbstractGameLoop> gameLoops() {
		return gameLoops;
	}
	
}
