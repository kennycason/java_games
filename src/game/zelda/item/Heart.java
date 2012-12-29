package game.zelda.item;

import java.awt.Graphics2D;

import engine.Game;
import engine.entity.AbstractSimpleEntity;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class Heart extends AbstractSimpleEntity {
	
	public Heart(Game game) {
		super(game);
		SpriteBank rsrcs = SpriteBank.getInstance();
		SpriteSheet sheet = (SpriteSheet) rsrcs.get("entities");
		sprite = new AnimatedSprite(sheet.getRange(0, 0), -1);

		locate(10 * 0, 10 * 0);
	}
	
	public void draw(Graphics2D g) {
		sprite.draw(g, 
				(int) (game.map().offset().x() + x()), 
				(int) (game.map().offset().y() + y()));
	}

}
