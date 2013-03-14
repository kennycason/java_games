package minesweeper;

import java.awt.Font;

import minesweeper.gamestates.MainGameLoop;
import engine.Game;
import engine.GameFactory;
import engine.GameStateEnum;
import engine.sound.LoopingSound;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteSheet;
import game.zelda.usables.SwordLevel1;

public class MineSweeper extends Game {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		GameFactory.set(new MineSweeper());
		Game game = GameFactory.get();
		game.start();
		game.run();
	}
	
	private MineSweeper() {
		super(240, 240, 2);
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
		// Game.sprites.set("entities8x8", new SpriteSheet("sprites/entity/zelda/entities.png", 8, 8));
		// Game.sprites.set("title_screen", new SimpleSprite("sprites/entity/zelda/Oracle_of_Ages_logo.png"));
		
		// load fonts
		Game.fonts.set("small", new Font("Serif", Font.BOLD, 10));
		Game.fonts.set("large", new Font("Serif", Font.BOLD, 40));
		
		// Game.sounds.set("title_screen", new LoopingSound("sounds/bg/LoZ Oracle of Seasons Intro + Title Screen.WAV"));
		// Game.sounds.set("main_theme", new LoopingSound("sounds/bg/LoZ Oracle of Seasons Main Theme.WAV"));
	
		// Game.usables.set("sword1", new SwordLevel1());

		gameLoops.put(GameStateEnum.MAIN, new MainGameLoop());

		gameState = GameStateEnum.MAIN;
		gameLoops.get(GameStateEnum.MAIN).start();
		
	}
	
	public void run() {
		while(true) {
			switch(gameState) {
				case TITLE_SCREEN:
					break;
				case MAIN:
					gameLoops.get(GameStateEnum.MAIN).run();
					break;
				case ITEM_SCREEN:
					break;
				case PAUSED:
					break;		
				case DEAD:
					break;
				case END:
					System.exit(0);
					break;
			}
		}
	}
		
}
