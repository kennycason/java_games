package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.event.EnemiesDeadItemAppearEvent;
import engine.event.EnemyDeployEvent;
import engine.event.TimedEnemyDeployEvent;
import engine.font.FontBank;
import engine.map.MapLoader;
import engine.sound.LoopingSound;
import engine.sound.SoundBank;
import engine.sound.SoundChannels;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteBank;
import game.zelda.enemy.LikeLike;
import game.zelda.enemy.Octorok;
import game.zelda.enemy.RedTurtle;
import game.zelda.item.FullHeart;
import game.zelda.item.RupeeGold;
import game.zelda.item.TreasureChest;
import game.zelda.player.Link;

public class TitleScreenGameLoop extends AbstractGameLoop {

	private LoopingSound sound;
	
	public TitleScreenGameLoop() {
		super();
		sound = (LoopingSound) SoundBank.getInstance().get("main_theme");
	}

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastRefresh >= refreshInterval) {
			if (game.keyboard().isKeyPressed(KeyEvent.VK_SPACE)) {
				if(currentTime - game.gameLoops().get(GameStateEnum.TITLE_SCREEN).transitionTime() >= 500) {
					end();
					newGame();
					game.gameLoops().get(GameStateEnum.MAIN).start();
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
		if(game.zoom() > 1) {
			g.scale(game.zoom(), game.zoom());
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		((SimpleSprite) SpriteBank.getInstance().get("title_screen")).draw(g, 0, 0);
		g.setColor(Color.BLACK);
		g.setFont(FontBank.getInstance().get("menu_large"));
		g.drawString("Press Space", 50, 200);
		g.dispose();
	}
	
	@Override
	public void start() {
		super.start();
		sound.play();
	}

	@Override
	public void end() {
		sound.stop();
	}
	
	public void newGame() { 
		MapLoader loader = new MapLoader();

		game.map(loader.load("maps/real.tmx"));
		game.map().offset().set(2 * game.map().tileWidth(), -4 *  game.map().tileHeight());

		game.map().events().add(new EnemyDeployEvent(new LikeLike(4, 7)));
		game.map().events().add(new EnemyDeployEvent(new Octorok(10, 10)));
		game.map().events().add(new EnemyDeployEvent(new LikeLike(13, 19)));
		game.map().events().add(new EnemyDeployEvent(new LikeLike(16, 9)));
		game.map().events().add(new EnemyDeployEvent(new RedTurtle(15, 10)));
		
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(2, 10), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(2, 11), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(2, 12), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(5, 14), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(7, 14), 10000));
		
		game.map().events().add(new EnemiesDeadItemAppearEvent(new TreasureChest(new RupeeGold(), 2, 10)));
		
		game.map().items().add(new FullHeart(4, 10));
		game.map().items().add(new TreasureChest(new FullHeart(), 2, 8));		
		
		game.link(new Link());
		
		// stop all looping sounds
		for(String soundId : SoundBank.getInstance().all().keySet()) {
			if(SoundBank.getInstance().get(soundId) instanceof LoopingSound) {
				((LoopingSound) SoundBank.getInstance().get(soundId)).stop();
			}
		}
		SoundChannels.getInstance().stopAll();
	}

}
