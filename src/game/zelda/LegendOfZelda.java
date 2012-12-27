package game.zelda;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import engine.Game;
import engine.entity.AbstractEntity;
import engine.entity.AbstractLivingEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.weapon.WeaponResources;
import engine.font.FontResources;
import engine.map.Map;
import engine.sound.Sound;
import engine.sprite.SpriteResources;
import engine.sprite.SpriteSheet;
import game.zelda.enemy.LikeLike;
import game.zelda.enemy.Octorok;
import game.zelda.player.Link;
import game.zelda.weapon.Boomerang;
import game.zelda.weapon.SwordLevel1;
import game.zelda.weapon.SwordLevel2;
import game.zelda.weapon.SwordLevel3;

public class LegendOfZelda extends Game {

	private static final long serialVersionUID = 1L;

	private TopMenu menu;
	
	public static void main(String[] args) {
		LegendOfZelda zelda = new LegendOfZelda();
		zelda.start();
		zelda.run();
	}
	
	public LegendOfZelda() {
		
	}
	
	public void init() {
		// load globals
		// @TODO use resource loader LegendOfZelda.class.getResourceAsStream(spritename)
		SpriteResources.getInstance().set("entities", new SpriteSheet("sprites/entity/zelda/entities.png", 16, 16));
		// @TODO make a util that grabs specific rectangles instead of parsing entities.png as 8x8...
		SpriteResources.getInstance().set("entities8x8", new SpriteSheet("sprites/entity/zelda/entities.png", 8, 8));
		
		FontResources.getInstance().set("menu_smaller", new Font("Serif", Font.BOLD, 10));
		FontResources.getInstance().set("menu_small", new Font("Serif", Font.BOLD, 12));
		FontResources.getInstance().set("menu_large", new Font("Serif", Font.PLAIN, 24));
		
		WeaponResources.getInstance().set("sword1", new SwordLevel1(this));
		WeaponResources.getInstance().set("sword2", new SwordLevel2(this));
		WeaponResources.getInstance().set("sword3", new SwordLevel3(this));
		WeaponResources.getInstance().set("boomerang", new Boomerang(this));
		
		Sound bg = new Sound("sound/bg/LoZ Oracle of Seasons Main Theme.WAV", true);
		bg.play();
		
		
		map = new Map(); 
		map.load("maps/small.tmx");
		map.offset().set(2 * map.tileWidth(), -4 *  map.tileHeight());
		lastRefresh = System.currentTimeMillis() - refreshInterval;
		
		link = new Link(this);
		
		enemies = new LinkedList<AbstractEnemy>();
		enemies.add(new LikeLike(this, 5, 5));
		enemies.add(new Octorok(this, 10, 10));
		enemies.add(new LikeLike(this, 12, 19));
		enemies.add(new LikeLike(this, 16, 9));
		
		menu = new TopMenu(this);
	}
	
	public void mainLoop() {
		if(System.currentTimeMillis() - lastRefresh >= refreshInterval) {
			// handle game logic
			link.keyBoard(keyboard);
			link.handle();
			
			Iterator<AbstractEnemy> iter = enemies.iterator();
			while(iter.hasNext()) {
				AbstractLivingEntity entity = iter.next();
				entity.handle();
				if(entity.dead()) {
					iter.remove();
				}
			}
			
			// paint everything
			draw(screen.bufferedImage());
			screenPanel.repaint();
			lastRefresh = System.currentTimeMillis();
		}
	}

	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if(zoom > 1) {
			g.scale(zoom, zoom);
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCREEN_WIDTH * zoom, SCREEN_HEIGHT * zoom);
		map.drawBackground(g);
		map.drawBottomLayer(g);

		link.draw(g);
		
		for(AbstractEntity enemy : enemies) {
			enemy.handle();
			enemy.draw(g);
		}
		
		// map.drawTopLayer(g);
		menu.draw(g);
		g.dispose();
	}
	

	
}
