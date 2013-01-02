package game.zelda.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.zelda.item.BossKey;
import game.zelda.item.SmallKey;
import engine.AbstractGameLoop;
import engine.Game;
import engine.GameStateEnum;
import engine.entity.usable.AbstractUsableEntity;
import engine.sound.AbstractSound;
import engine.sprite.SpriteSheet;
import game.zelda.Buttons;
import game.zelda.TopMenu;

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
	
	private BossKey bossKey = new BossKey();
	
	private SmallKey smallKey = new SmallKey();

	public ItemScreenGameLoop() {
		super();
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
	}

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastRefresh >= refreshInterval) {
			if (game.keyboard().isKeyPressed(Buttons.START)) {
				if (currentTime
						- game.gameLoops().get(GameStateEnum.ITEM_SCREEN)
								.transitionTime() >= 1000) {
					end();
					game.gameLoops().get(GameStateEnum.MAIN).start();
					game.gameState(GameStateEnum.MAIN);
				}
			}
			if (currentTime - cursorLastMoved > 150) {
				if (game.keyboard().isKeyPressed(Buttons.UP)) {
					cursorY--;
					if (cursorY < 0) {
						cursorY = 0;
					}
					mouseCursor.play();
					cursorLastMoved = System.currentTimeMillis();
				} else if (game.keyboard().isKeyPressed(Buttons.DOWN)) {
					cursorY++;
					if (cursorY > 3) {
						cursorY = 3;
					}
					mouseCursor.play();
					cursorLastMoved = System.currentTimeMillis();
				} else if (game.keyboard().isKeyPressed(Buttons.LEFT)) {
					cursorX--;
					if (cursorX < 0) {
						cursorX = 0;
					}
					mouseCursor.play();
					cursorLastMoved = System.currentTimeMillis();
				} else if (game.keyboard().isKeyPressed(Buttons.RIGHT)) {
					cursorX++;
					if (cursorX > 3) {
						cursorX = 3;
					}
					mouseCursor.play();
					cursorLastMoved = System.currentTimeMillis();
				}
			}
			if (currentTime - itemSelected > 450) {
				if (game.keyboard().isKeyPressed(Buttons.ITEM_B)) {
					int item = cursorX + cursorY * 4;
					AbstractUsableEntity old = game.link().itemB();
					game.link().itemB(game.link().items()[item]);
					game.link().items()[item] = old;
					itemSelected = System.currentTimeMillis();
					mouseSelect.play();
				}

				if (game.keyboard().isKeyPressed(Buttons.ITEM_A)) {
					int item = cursorX + cursorY * 4;
					AbstractUsableEntity old = game.link().itemA();
					game.link().itemA(game.link().items()[item]);
					game.link().items()[item] = old;
					itemSelected = System.currentTimeMillis();
					mouseSelect.play();
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
		if (game.zoom() > 1) {
			g.scale(game.zoom(), game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		g.setColor(lightBlue);
		g.fillRect(10, 34, Game.SCREEN_WIDTH - 20, Game.SCREEN_HEIGHT - 100);

		g.setColor(Color.BLACK);
		g.setFont(Game.fonts.get("menu_smaller"));
		int x = 30;
		int y = 45;
		for (int i = 0; i < game.link().items().length; i++) {
			x += 50;
			if (i % 4 == 0) {
				x = 30;
				if (i != 0) {
					y += 33;
				}
			}
			if (game.link().items()[i] != null) {
				game.link().items()[i].menuDraw(g, x, y);
				g.drawString(game.link().items()[i].menuDisplayName(), x + 16,
						y + 17);
			}
		}
		g.setFont(Game.fonts.get("menu_large"));
		g.drawString("[   ]", (cursorX * 50) + 28, (cursorY * 33) + 60);

		
		// lower menu.
		g.setColor(lightBlue);
		g.fillRect(10, 185, Game.SCREEN_WIDTH - 20, 30);
		heartPieces.get(game.link().heartPieces()).draw(g, 18, 192);
		if(game.link().bossKey()) {
			bossKey.sprite().draw(g, 43, 192);
		}
		smallKey.sprite().draw(g, 68, 191);
		g.setFont(Game.fonts.get("menu_smaller"));
		g.setColor(Color.BLACK);
		g.drawString("x" + game.link().smallKeys(), 83, 206);
		
		menu.draw(g);
		g.dispose();
	}

	@Override
	public void end() {
		// sound.stop();
	}

}
