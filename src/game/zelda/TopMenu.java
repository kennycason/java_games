package game.zelda;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Game;
import engine.GameFactory;
import engine.font.FontBank;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;
import game.zelda.item.EntityItemSpriteResourceNumber;

public class TopMenu {
	
	private Game game;

	/**
	 * quick references to commonly used sprite resources
	 */
	private SpriteSheet entities8x8;
	
	public TopMenu() {
		game = GameFactory.get();
		entities8x8 = (SpriteSheet) SpriteBank.getInstance().get("entities8x8");
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(0xE0F3FF));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, 24);
		
		g.setColor(Color.BLACK);
		g.setFont(FontBank.getInstance().get("menu_small"));
	    g.drawString("B", 0, 10);
		g.setFont(FontBank.getInstance().get("menu_large"));
	    g.drawString("[   ]",10, 19);
	    if(game.link().itemB() != null) {
		    game.link().itemB().menuDraw(g, 13, 3);
		    g.setFont(FontBank.getInstance().get("menu_smaller"));
		    g.drawString(game.link().itemB().menuDisplayName(), 28, 20);
	    }
	    
		g.setFont(FontBank.getInstance().get("menu_small"));
	    g.drawString("A", 55, 10);
		g.setFont(FontBank.getInstance().get("menu_large"));
	    g.drawString("[   ]", 65, 19);
	    if(game.link().itemA() != null) {
		    game.link().itemA().menuDraw(g, 69, 3);
		    g.setFont(FontBank.getInstance().get("menu_smaller"));
		    g.drawString(game.link().itemA().menuDisplayName(), 85, 20);
	    }
	    
	    drawRupees(g);
	    drawHearts(g);
	}
	
	private void drawRupees(Graphics2D g) {
		entities8x8.get(EntityItemSpriteResourceNumber.MENU_RUPEE).draw(g, 110, 2);
		g.setFont(FontBank.getInstance().get("menu_small"));
	    g.drawString(String.valueOf(game.link().rupees()), 110, 22);
	}

	private void drawHearts(Graphics2D g) {
		for(int i = 0, x = 0, y = 2; i < game.link().maxLife(); i++, x += 9) {
			if(i == 10) {
				x = 0;
			}
			if(i >= 10) {
				y = 11;
			}
			if(game.link().life() >= i + 1) {
				entities8x8.get(EntityItemSpriteResourceNumber.MENU_HEART_FULL).draw(g, 142 + x, y);
			} else if(game.link().life() + 0.5 == i + 1) {
				entities8x8.get(EntityItemSpriteResourceNumber.MENU_HEART_HALF).draw(g, 142 + x, y);
			} else {
				entities8x8.get(EntityItemSpriteResourceNumber.MENU_HEART_EMPTY).draw(g, 142 + x, y);
			}
		}
	}
	
}
