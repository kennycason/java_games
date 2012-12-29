package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.font.FontBank;
import game.zelda.TopMenu;

public class PauseGameLoop extends AbstractGameLoop {

	private TopMenu menu;

	public PauseGameLoop(Game game) {
		super(game);
		menu = new TopMenu(game);
	}

	@Override
	public void run() {
		if (System.currentTimeMillis() - lastRefresh >= refreshInterval) {
			if (game.keyboard().isKeyPressed(KeyEvent.VK_SPACE)) {
				if(System.currentTimeMillis() - game.gameLoops().get(GameStateEnum.PAUSED).transitionTime() >= 1000) {
					game.gameLoops().get(GameStateEnum.MAIN).reset();
					game.gameState(GameStateEnum.MAIN);
				}
			}

			// paint everything
			draw(game.screen().bufferedImage());
			game.screenPanel().repaint();
			lastRefresh = System.currentTimeMillis();
		}
	}

	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if (game.zoom() > 1) {
			g.scale(game.zoom(), game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH * game.zoom(), Game.SCREEN_HEIGHT
				* game.zoom());
		g.setColor(Color.WHITE);
		g.setFont(FontBank.getInstance().get("menu_large"));
		g.drawString("Paused", 50, 50);

		menu.draw(g);
		g.dispose();
	}

}
