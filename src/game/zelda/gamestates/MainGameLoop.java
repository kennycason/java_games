package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.entity.AbstractEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import game.zelda.TopMenu;

public class MainGameLoop extends AbstractGameLoop {

	private TopMenu menu;
	
	public MainGameLoop() {
		super();
		menu = new TopMenu();
	}

	@Override
	public void run() {
		if(game.link().dead()){ 
			game.gameState(GameStateEnum.DEAD);
		}
		
		if(System.currentTimeMillis() - lastRefresh >= refreshInterval) {
			// handle game logic
			game.link().keyBoard(game.keyboard());
			game.link().handle();
			
			// enemies
			Iterator<AbstractEnemy> enemyIter = game.map().enemies().iterator();
			while(enemyIter.hasNext()) {
				AbstractEnemy entity = enemyIter.next();
				entity.handle();
				if(entity.dead()) {
					enemyIter.remove();
				}
			}
			// items
			Iterator<AbstractItem> itemIter = game.map().items().iterator();
			while(itemIter.hasNext()) {
				AbstractItem item = itemIter.next();
				item.handle();
				if(item.consumed()) {
					itemIter.remove();
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
		if(game.zoom() > 1) {
			g.scale(game.zoom(), game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH * game.zoom(), Game.SCREEN_HEIGHT * game.zoom());
		game.map().drawBackground(g);
		game.map().drawBottomLayer(g);
		
		for(AbstractItem item : game.map().items()) {
			item.draw(g);
		}
		
		for(AbstractEntity enemy : game.map().enemies()) {
			enemy.draw(g);
		}
		
		game.link().draw(g);

		// map.drawTopLayer(g);
		menu.draw(g);
		game.map().drawMetaLater(g); // doesn't really draw, just resets the positions
		g.dispose();
	}

}
