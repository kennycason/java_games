package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import engine.AbstractGameLoop;
import engine.Game;
import engine.entity.AbstractEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.sound.LoopingSound;
import game.zelda.TopMenu;

public class MainGameLoop extends AbstractGameLoop {

	private TopMenu menu;
	
	private LoopingSound sound;
	
	public MainGameLoop() {
		super();
		menu = new TopMenu();
		sound = (LoopingSound) Game.sounds.get("main_theme");
	}

	@Override
	public void run() {
	
		if(Game.clock.systemElapsedMillis() - lastRefresh >= refreshInterval) {
			// handle game logic
			if(game.link().canMove()) {
				game.link().keyBoard(game.keyboard());
			}
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
				if(item.consumed() && item.disappearAfterConsume()) {
					itemIter.remove();
				}
			}
			
			// handle map events
			game.map().events().handle();
			
			// paint everything
			draw(game.screen().bufferedImage());
			game.screenPanel().repaint();
			lastRefresh = Game.clock.systemElapsedMillis();
		}
	}

	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if(game.zoom() > 1) {
			g.scale(game.zoom(), game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		//game.map().drawBackground(g);
		game.map().drawBottomLayer(g);
		game.map().drawMiddleLater(g);
		
		for(AbstractItem item : game.map().items()) {
			item.draw(g);
		}
		
		for(AbstractEntity enemy : game.map().enemies()) {
			enemy.draw(g);
		}
		
		game.link().draw(g);
		game.map().drawTopLater(g);
		menu.draw(g);
		game.map().drawMetaLaters(g); // doesn't really draw, just resets the positions
		g.dispose();
	}
	
	@Override
	public void start() {
		super.start();
		if(!sound.playing()) {
			sound.play();
		}
	}
	
	@Override
	public void end() {
		super.end();
		sound.stop();
	}

}
