package game.zelda;

import java.awt.Font;

import engine.Game;
import engine.GameFactory;
import engine.GameStateEnum;
import engine.sound.LoopingSound;
import engine.sound.SoundEffect;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteSheet;
import game.zelda.gamestates.ItemScreenGameLoop;
import game.zelda.gamestates.MainGameLoop;
import game.zelda.gamestates.PausedScreenGameLoop;
import game.zelda.gamestates.TitleScreenGameLoop;
import game.zelda.usables.Boomerang;
import game.zelda.usables.BowAndArrow;
import game.zelda.usables.Bracelet;
import game.zelda.usables.MegatonHammer;
import game.zelda.usables.Ocarina;
import game.zelda.usables.SwordLevel1;
import game.zelda.usables.SwordLevel2;
import game.zelda.usables.SwordLevel3;

public class LegendOfZelda extends Game {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		GameFactory.set(new LegendOfZelda());
		Game game = GameFactory.get();
		game.start();
		game.run();
	}
	
	private LegendOfZelda() {
		super();
	}
	
	/**
	 * load global resources
	 */
	@Override
	public void start() {
		super.start();
		// load globals
		
		// load sprites
		Game.sprites.set("entities", new SpriteSheet("sprites/entity/zelda/entities.png", 16, 16));
		Game.sprites.set("entities8x8", new SpriteSheet("sprites/entity/zelda/entities.png", 8, 8));
		Game.sprites.set("title_screen", new SimpleSprite("sprites/entity/zelda/Oracle_of_Ages_logo.png"));
		
		// load fonts
		Game.fonts.set("menu_smaller", new Font("Serif", Font.BOLD, 10));
		Game.fonts.set("menu_small", new Font("Serif", Font.BOLD, 12));
		Game.fonts.set("menu_large", new Font("Serif", Font.PLAIN, 24));
		
		// Game.sounds.set("title_screen", new LoopingSound("sounds/bg/LoZ Oracle of Seasons Intro + Title Screen.WAV"));
		Game.sounds.set("main_theme", new LoopingSound("sounds/bg/LoZ Oracle of Seasons Main Theme.WAV"));
		Game.sounds.set("sword_slash1", new SoundEffect("sounds/effects/Oracle_Sword_Slash1.wav"));
		Game.sounds.set("boomerang", new LoopingSound("sounds/effects/Oracle_Boomerang.wav"));
		Game.sounds.set("enemy_hit", new SoundEffect("sounds/effects/Oracle_Enemy_Hit.wav"));
		Game.sounds.set("enemy_die", new SoundEffect("sounds/effects/Oracle_Enemy_Die.wav"));
		Game.sounds.set("link_hurt", new SoundEffect("sounds/effects/Oracle_Link_Hurt.wav"));
		Game.sounds.set("link_die", new SoundEffect("sounds/effects/Oracle_Link_Dying.wav"));
		Game.sounds.set("link_fall", new SoundEffect("sounds/effects/Oracle_Link_Fall.wav"));
		Game.sounds.set("link_low_life", new LoopingSound("sounds/effects/Oracle_LowHealth.wav"));
		Game.sounds.set("link_get_rupee", new SoundEffect("sounds/effects/Oracle_Get_Rupee.wav"));
		Game.sounds.set("link_get_rupee5", new SoundEffect("sounds/effects/Oracle_Get_Rupee5.wav"));
		Game.sounds.set("link_get_heart", new SoundEffect("sounds/effects/Oracle_Get_Heart.wav"));
		Game.sounds.set("link_get_item", new SoundEffect("sounds/effects/Oracle_Get_Item.wav"));
		Game.sounds.set("link_get_heart_container", new SoundEffect("sounds/effects/Oracle_HeartContainer.wav"));
		Game.sounds.set("tune_of_ages", new SoundEffect("sounds/effects/OOA_Harp_TuneOfAges.wav"));
		Game.sounds.set("open_chest", new SoundEffect("sounds/effects/Oracle_Chest.wav"));
		Game.sounds.set("secret", new SoundEffect("sounds/effects/Oracle_Secret.wav"));
		Game.sounds.set("menu_cursor", new SoundEffect("sounds/effects/Oracle_Menu_Cursor.wav"));
		Game.sounds.set("menu_select", new SoundEffect("sounds/effects/Oracle_Menu_Select.wav"));	
		Game.sounds.set("rock_shatter", new SoundEffect("sounds/effects/Oracle_Rock_Shatter.wav"));	
		Game.sounds.set("bush_cut", new SoundEffect("sounds/effects/Oracle_Bush_Cut.wav"));	
		
		Game.usables.set("sword1", new SwordLevel1());
		Game.usables.set("sword2", new SwordLevel2());
		Game.usables.set("sword3", new SwordLevel3());
		Game.usables.set("boomerang", new Boomerang());
		Game.usables.set("bow", new BowAndArrow());
		Game.usables.set("ocarina", new Ocarina());
		Game.usables.set("megaton", new MegatonHammer()); // still need to finess the graphics
		Game.usables.set("bracelet", new Bracelet());
		
		gameLoops.put(GameStateEnum.TITLE_SCREEN, new TitleScreenGameLoop());
		gameLoops.put(GameStateEnum.MAIN, new MainGameLoop());
		gameLoops.put(GameStateEnum.ITEM_SCREEN, new ItemScreenGameLoop());
		gameLoops.put(GameStateEnum.PAUSED, new PausedScreenGameLoop());
		

		gameState = GameStateEnum.TITLE_SCREEN;
		//gameLoops.get(GameStateEnum.TITLE_SCREEN).start();
		
	}
	
	public void run() {
		while(true) {
			switch(gameState) {
				case TITLE_SCREEN:
					//gameLoops.get(GameStateEnum.TITLE_SCREEN).run();
					((TitleScreenGameLoop)gameLoops.get(GameStateEnum.TITLE_SCREEN)).newGame();
					gameLoops.get(GameStateEnum.TITLE_SCREEN).start();
					gameState = GameStateEnum.MAIN;
					break;
				case MAIN:
					gameLoops.get(GameStateEnum.MAIN).run();
					break;
				case ITEM_SCREEN:
					gameLoops.get(GameStateEnum.ITEM_SCREEN).run();
					break;
				case PAUSED:
					gameLoops.get(GameStateEnum.PAUSED).run();
					break;		
				case DEAD:
					gameLoops.get(GameStateEnum.MAIN).end();
					gameState = GameStateEnum.TITLE_SCREEN;
					break;
				case END:
					System.exit(0);
					break;
			}
		}
	}
		
}
