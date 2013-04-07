package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.entity.usable.AbstractUsableEntity;
import engine.event.ConsumeItemEvent;
import engine.event.EnemiesDeadItemAppearEvent;
import engine.event.EnemyDeployEvent;
import engine.event.TimedDialogDeployEvent;
import engine.event.TimedEnemyDeployEvent;
import engine.graphics.sprite.SimpleSprite;
import engine.map.tiled.TiledMapLoader;
import engine.math.PositionVector;
import engine.sound.LoopingSound;
import game.zelda.Buttons;
import game.zelda.dialog.ZeldaDialog;
import game.zelda.enemy.LikeLike;
import game.zelda.enemy.Octorok;
import game.zelda.enemy.RedTurtle;
import game.zelda.item.FullHeart;
import game.zelda.item.HeartPiece;
import game.zelda.item.RupeeGold;
import game.zelda.item.TreasureChest;
import game.zelda.player.Link;

public class TitleScreenGameLoop extends AbstractGameLoop {

	private LoopingSound sound;
	
	private Link link;

	public TitleScreenGameLoop(Link link) {
		super();
		this.link = link;
		sound = (LoopingSound) Game.sounds.get("main_theme");
	}

	@Override
	public void run() {
		long currentTime = Game.clock.systemElapsedMillis();
		if (currentTime - lastRefresh >= refreshInterval) {
			if (Game.keyboard.isKeyPressed(Buttons.START)) {
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
			lastRefresh = Game.clock.systemElapsedMillis();
		}
	}

	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if(Game.zoom() > 1) {
			g.scale(Game.zoom(), Game.zoom());
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		((SimpleSprite) Game.sprites.get("title_screen")).draw(g, 0, 0);
		g.setColor(Color.BLACK);
		g.setFont(Game.fonts.get("menu_large"));
		g.drawString("Press Space", 50, 200);
		g.dispose();
	}
	
	public void newGame() { 
		TiledMapLoader loader = new TiledMapLoader();

		game.map(loader.load("maps/real.tmx"));
		
		game.map().events().add(new TimedDialogDeployEvent(new ZeldaDialog("Welcome to my Zelda clone.\nIt's still rough so be patient. :)"), 500));

		game.map().events().add(new EnemyDeployEvent(new LikeLike(link, 4, 7)));
		game.map().events().add(new EnemyDeployEvent(new Octorok(link, 9, 10)));
		game.map().events().add(new EnemyDeployEvent(new LikeLike(link, 12, 19)));
		game.map().events().add(new EnemyDeployEvent(new LikeLike(link, 19, 9)));
		game.map().events().add(new EnemyDeployEvent(new RedTurtle(link, 15, 10)));
		
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(link, 2, 10), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(link, 5, 14), 10000));
		game.map().events().add(new TimedEnemyDeployEvent(new RedTurtle(link, 7, 14), 10000));
	
		game.map()
				.events()
				.add(new EnemiesDeadItemAppearEvent(new TreasureChest(link, 25, 11,
						new ZeldaDialog("Found A Gold Rupee! Joy!"),
						new ConsumeItemEvent(new RupeeGold(link)))));
		game.map()
				.events()
				.add(new EnemiesDeadItemAppearEvent(new TreasureChest(link, 26, 11,
						new ZeldaDialog("Found A HeartPiece!\nCollect 4 pieces to gain a heart"),
						new ConsumeItemEvent(new HeartPiece(link)))));

		game.map().items().add(new FullHeart(link, 4, 10));
		game.map().items().add(new HeartPiece(link, 9, 3));
		game.map().items().add(new HeartPiece(link, 10, 3));
		game.map().items().add(new HeartPiece(link, 11, 3));
		game.map().items().add(new TreasureChest(link, 2, 8, 
				new ZeldaDialog("Found A Full Heart!\nLife increased by one!"),
				new ConsumeItemEvent(new FullHeart(link))
		));	
		
		// stop all looping sounds
		Game.sounds.stopAll();
		
		for(String key : Game.usables.all().keySet()) {
			AbstractUsableEntity entity = Game.usables.get(key);
			entity.reset();
		}
		link.setLocation(new PositionVector(10, 12));
		
	}
	
	@Override 
	public void start() {
		lastRefresh = Game.clock.systemElapsedMillis() - refreshInterval;
		transitionTime = Game.clock.systemElapsedMillis();
		sound.play();
	}
		
	@Override
	public void end() {
		sound.stop();
	}

}
