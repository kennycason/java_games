package game.zelda.item;

import engine.Game;
import engine.entity.AbstractSimpleEntity;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

import java.awt.Graphics2D;

public class Heart extends AbstractSimpleEntity {
	
	public Heart(Game game) {
		super(game);
		SpriteBank rsrcs = SpriteBank.getInstance();
		SpriteSheet sheet = (SpriteSheet) rsrcs.get("entities");
		sprite = new SimpleSprite(sheet.get(0));

		locate(10 * 0, 10 * 0);
	}
	
	public void draw(Graphics2D g) {
		sprite.draw(g, 
				(int) (game.map().offset().x() + x()), 
				(int) (game.map().offset().y() + y()));
	}

}
