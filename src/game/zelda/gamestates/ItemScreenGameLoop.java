package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.entity.usable.AbstractUsableEntity;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;
import game.zelda.Buttons;
import game.zelda.TopMenu;
import game.zelda.item.BossKey;
import game.zelda.item.SmallKey;
import game.zelda.player.Link;

public class ItemScreenGameLoop extends AbstractGameLoop {

	private TopMenu menu;
	
	private Color lightBlue;

	private int cursorX;

	private int cursorY;

	private long cursorLastMoved;

	private long itemSelected;

	private AbstractSound mouseCursor;
	
	private AbstractSound mouseSelect;
	
	private SpriteSheet heartPieces;
	
	private BossKey bossKey;
	
	private SmallKey smallKey;
	
	private Link link;

	public ItemScreenGameLoop(Link link) {
		super();
		this.link = link;
		menu = new TopMenu();
		cursorX = 0;
		cursorY = 0;
		mouseSelect = Game.sounds.get("menu_select");
		mouseCursor = Game.sounds.get("menu_cursor");
		heartPieces = new SpriteSheet(4, 16, 16);
		SpriteSheet entities = (SpriteSheet)Game.sprites.get("entities");
		heartPieces.set(0, entities.get(15));
		heartPieces.set(1, entities.get(16));
		heartPieces.set(2, entities.get(17));
		heartPieces.set(3, entities.get(18));
		lightBlue = new Color(0xCCF2FF);
		
		bossKey = new BossKey(link);
		smallKey = new SmallKey(link);
	}

	@Override
	public void run() {
		long currentTime = Game.clock.systemElapsedMillis();
		if (currentTime - lastRefresh >= refreshInterval) {
			lastRefresh = Game.clock.systemElapsedMillis();
			if (Game.keyboard.isKeyPressed(Buttons.START)) {
				if (currentTime
						- game.gameLoops().get(GameStateEnum.ITEM_SCREEN)
								.transitionTime() >= 1000) {
					end();
					game.gameLoops().get(GameStateEnum.MAIN).start();
					game.gameState(GameStateEnum.MAIN);
				}
			}
			if (currentTime - cursorLastMoved > 150) {
				if (Game.keyboard.isKeyPressed(Buttons.UP)) {
					cursorY--;
					if (cursorY < 0) {
						cursorY = 0;
					}
					mouseCursor.play();
					cursorLastMoved = Game.clock.systemElapsedMillis();
				} else if (Game.keyboard.isKeyPressed(Buttons.DOWN)) {
					cursorY++;
					if (cursorY > 3) {
						cursorY = 3;
					}
					mouseCursor.play();
					cursorLastMoved = Game.clock.systemElapsedMillis();
				} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)) {
					cursorX--;
					if (cursorX < 0) {
						cursorX = 0;
					}
					mouseCursor.play();
					cursorLastMoved = Game.clock.systemElapsedMillis();
				} else if (Game.keyboard.isKeyPressed(Buttons.RIGHT)) {
					cursorX++;
					if (cursorX > 3) {
						cursorX = 3;
					}
					mouseCursor.play();
					cursorLastMoved = Game.clock.systemElapsedMillis();
				}
			}
			if (currentTime - itemSelected > 450) {
				if (Game.keyboard.isKeyPressed(Buttons.ITEM_B)) {
					int item = cursorX + cursorY * 4;
					AbstractUsableEntity old = link.itemB();
					link.itemB(link.items()[item]);
					link.items()[item] = old;
					itemSelected = Game.clock.systemElapsedMillis();
					mouseSelect.play();
				}

				if (Game.keyboard.isKeyPressed(Buttons.ITEM_A)) {
					int item = cursorX + cursorY * 4;
					AbstractUsableEntity old = link.itemA();
					link.itemA(link.items()[item]);
					link.items()[item] = old;
					itemSelected = Game.clock.systemElapsedMillis();
					mouseSelect.play();
				}
			}

			// paint everything
			draw(game.screen().bufferedImage());
			game.screenPanel().repaint();
		}
	}

	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if (Game.zoom() > 1) {
			g.scale(Game.zoom(), Game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		g.setColor(lightBlue);
		g.fillRect(10, 34, Game.SCREEN_WIDTH - 20, Game.SCREEN_HEIGHT - 100);

		g.setColor(Color.BLACK);
		g.setFont(Game.fonts.get("menu_smaller"));
		int x = 30;
		int y = 45;
		for (int i = 0; i < link.items().length; i++) {
			x += 50;
			if (i % 4 == 0) {
				x = 30;
				if (i != 0) {
					y += 33;
				}
			}
			if (link.items()[i] != null) {
				link.items()[i].menuDraw(g, x, y);
				g.drawString(link.items()[i].menuDisplayName(), x + 16,
						y + 17);
			}
		}
		g.setFont(Game.fonts.get("menu_large"));
		g.drawString("[   ]", (cursorX * 50) + 28, (cursorY * 33) + 60);

		
		// lower menu.
		g.setColor(lightBlue);
		g.fillRect(10, 185, Game.SCREEN_WIDTH - 20, 30);
		heartPieces.get(link.heartPieces()).draw(g, 18, 192);
		if(link.bossKey()) {
			bossKey.sprite().draw(g, 43, 192);
		}
		smallKey.sprite().draw(g, 68, 191);
		g.setFont(Game.fonts.get("menu_smaller"));
		g.setColor(Color.BLACK);
		g.drawString("x" + link.smallKeys(), 83, 206);
		
		menu.draw(g);
		g.dispose();
	}
	
	@Override 
	public void start() {
		lastRefresh = Game.clock.systemElapsedMillis() - refreshInterval;
		transitionTime = Game.clock.systemElapsedMillis();
		Game.clock.pause();
	}

	@Override
	public void end() {
		Game.clock.start();
		// sound.stop();
	}

}
