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
import engine.entity.weapon.WeaponBank;
import engine.font.FontBank;
import engine.map.Map;
import engine.sound.LoopingSound;
import engine.sound.Sound;
import engine.sound.SoundBank;
import engine.sprite.SpriteBank;
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
		SpriteBank.getInstance().set("entities", new SpriteSheet("sprites/entity/zelda/entities.png", 16, 16));
		// @TODO make a util that grabs specific rectangles instead of parsing entities.png as 8x8...
		SpriteBank.getInstance().set("entities8x8", new SpriteSheet("sprites/entity/zelda/entities.png", 8, 8));
		
		FontBank.getInstance().set("menu_smaller", new Font("Serif", Font.BOLD, 10));
		FontBank.getInstance().set("menu_small", new Font("Serif", Font.BOLD, 12));
		FontBank.getInstance().set("menu_large", new Font("Serif", Font.PLAIN, 24));
		
		SoundBank.getInstance().set("main_theme", new LoopingSound("sound/bg/LoZ Oracle of Seasons Main Theme.WAV"));
		SoundBank.getInstance().set("sword_slash1", new Sound("sound/effects/Oracle_Sword_Slash1.wav"));
		SoundBank.getInstance().set("boomerang", new LoopingSound("sound/effects/Oracle_Boomerang.wav"));
		SoundBank.getInstance().set("enemy_hit", new Sound("sound/effects/Oracle_Enemy_Hit.wav"));
		SoundBank.getInstance().set("enemy_die", new Sound("sound/effects/Oracle_Enemy_Die.wav"));
		SoundBank.getInstance().set("link_hurt", new Sound("sound/effects/Oracle_Link_Hurt.wav"));
		SoundBank.getInstance().set("link_die", new Sound("sound/effects/Oracle_Link_Dying.wav"));
		SoundBank.getInstance().set("link_low_life", new LoopingSound("sound/effects/Oracle_LowHealth.wav"));
		
		WeaponBank.getInstance().set("sword1", new SwordLevel1(this));
		WeaponBank.getInstance().set("sword2", new SwordLevel2(this));
		WeaponBank.getInstance().set("sword3", new SwordLevel3(this));
		WeaponBank.getInstance().set("boomerang", new Boomerang(this));

		
		SoundBank.getInstance().get("main_theme").play();
		
		loadNewGame();
	}
	
	public void loadNewGame() { 
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
		// #TODO: is this the best way? Probably should send to main menu once it exists
		if(this.link.dead()){ loadNewGame(); }
		
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
		map.drawMetaLater(g); // doesn't really draw, just resets the positions
		g.dispose();
	}
	

	
}
