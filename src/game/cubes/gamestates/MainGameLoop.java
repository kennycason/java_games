package game.cubes.gamestates;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.graphics.shape.Polygon;
import engine.math.Vector2D;

public class MainGameLoop extends AbstractGameLoop {
	
	private Random random;

	private Polygon polygon = new Polygon(
			new Vector2D(10, 10),
			new Vector2D(40, 40),
			new Vector2D(200, 100),
			new Vector2D(100, 200),
			new Vector2D(30, 150),
			new Vector2D(10, 50)
		);
	
	public MainGameLoop() {
		super();

		random = new Random();
	}
	
	@Override
	public void run() {
		handleInput();
		if(Game.clock.systemElapsedMillis() - lastRefresh >= refreshInterval) {
			// paint everything
		//	draw(game.screen().bufferedImage());
		//	game.screenPanel().repaint();
			game.glCanvas().getGraphics();
			
			
			
			lastRefresh = Game.clock.systemElapsedMillis();
		}
	}

	public void handleInput() {

		if(Game.keyboard.isKeyPressed(KeyEvent.VK_C)) {
			
		}
		if(Game.keyboard.isKeyPressed(KeyEvent.VK_R)) {
		
		}
		if(Game.keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			game.gameState(GameStateEnum.END);
		}
	}
	
	
	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		
		if(Game.zoom() > 1) {
			g.scale(Game.zoom(), Game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		
		g.dispose();
	}

	@Override
	public void start() {
		lastRefresh = Game.clock.systemElapsedMillis() - refreshInterval;
		transitionTime = Game.clock.systemElapsedMillis();
		Game.clock.start();
	}
	
	@Override
	public void end() {
		Game.clock.stop();
	}
	

}
