package engine;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.entity.enemy.AbstractEnemy;
import engine.keyboard.DefaultKeyEventDispatcher;
import engine.keyboard.KeyBoard;
import engine.map.Map;
import engine.sprite.SimpleSprite;
import game.zelda.player.Link;

public abstract class Game extends JPanel {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	public static final int SCREEN_WIDTH = 240;

	public static final int SCREEN_HEIGHT = 240;
	
	protected int zoom = 2;

	protected final SimpleSprite screen;

	protected final JLabel screenPanel = new JLabel();

	protected final KeyBoard keyboard;
	
	protected int volume = 50;
	
	protected long lastRefresh;
	
	protected final long refreshInterval = 30;
	
	protected GameState gameState;
	
	// @TODO create Interface for Player to decouple Game and Link
	protected Link link;
	
	protected Map map;
		
	// @TODO move enemies/events/etc to Map Class
	protected List<AbstractEnemy> enemies;

	public Game() {
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
		
		gameState = GameState.TITLE_SCREEN;
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
	
	public abstract void init();
	
	public void run() {
		while(true) {
			switch(gameState) {
				case TITLE_SCREEN:
					init();
					gameState = GameState.MAIN_RUNNING;
					break;
				case MAIN_RUNNING:
					mainLoop();
					break;
				case DEAD:
				case END:
					System.exit(0);
					break;
			}
		}
	}

	public abstract void mainLoop();

	public abstract void draw(BufferedImage bi);
	
	
	public Link link() {
		return link;
	}

	public Map map() {
		return map;
	}
	
	public List<AbstractEnemy> enemies() {
		return enemies;
	}
	
	public void gameState(GameState gameState) {
		this.gameState = gameState;
	}

}
