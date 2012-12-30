package game.zelda;

import java.awt.Font;

import engine.Game;
import engine.GameFactory;
import engine.GameStateEnum;
import engine.entity.weapon.WeaponBank;
import engine.font.FontBank;
import engine.sound.LoopingSound;
import engine.sound.Sound;
import engine.sound.SoundBank;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;
import game.zelda.enemy.LikeLike;
import game.zelda.enemy.Octorok;
import game.zelda.gamestates.MainGameLoop;
import game.zelda.gamestates.PauseGameLoop;
import game.zelda.item.FullHeart;
import game.zelda.player.Link;
import game.zelda.weapon.Boomerang;
import game.zelda.weapon.BowAndArrow;
import game.zelda.weapon.SwordLevel1;
import game.zelda.weapon.SwordLevel2;
import game.zelda.weapon.SwordLevel3;

public class LegendOfZelda extends Game {

	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		GameFactory.set(new LegendOfZelda());
		Game game = GameFactory.get();
		game.init();
		game.start();
		game.run();
	}
	
	private LegendOfZelda() {
		super();
	}
	
	/**
	 * load global resources
	 */
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
		
		WeaponBank.getInstance().set("sword1", new SwordLevel1());
		WeaponBank.getInstance().set("sword2", new SwordLevel2());
		WeaponBank.getInstance().set("sword3", new SwordLevel3());
		WeaponBank.getInstance().set("boomerang", new Boomerang());
		WeaponBank.getInstance().set("bow", new BowAndArrow());
		
		SoundBank.getInstance().get("main_theme").play();
		
		gameLoops.put(GameStateEnum.MAIN, new MainGameLoop());
		gameLoops.put(GameStateEnum.PAUSED, new PauseGameLoop());
	}
	
	public void run() {
		while(true) {
			switch(gameState) {
				case TITLE_SCREEN:
					loadNewGame();
					gameState = GameStateEnum.MAIN;
					break;
				case MAIN:
					gameLoops.get(GameStateEnum.MAIN).run();
					break;
				case PAUSED:
					gameLoops.get(GameStateEnum.PAUSED).run();
					break;
				case DEAD:
					gameState = GameStateEnum.TITLE_SCREEN;
					break;
				case END:
					System.exit(0);
					break;
			}
		}
	}
	
	public void loadNewGame() { 
		map = loader.load("maps/small.tmx");
		map.offset().set(2 * map.tileWidth(), -4 *  map.tileHeight());

		map.enemies().add(new LikeLike(5, 5));
		map.enemies().add(new Octorok(10, 10));
		map.enemies().add(new LikeLike(12, 19));
		map.enemies().add(new LikeLike(16, 9));
		
		FullHeart heart = new FullHeart();
		heart.locate(16 * 4, 16 * 10);
		map.items().add(heart);
		
		link = new Link();		
		gameState = GameStateEnum.MAIN;
	}
	
}
