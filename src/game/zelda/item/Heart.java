package game.zelda.item;

import engine.entity.item.AbstractItem;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class Heart extends AbstractItem {
	
	public Heart() {
		super();
		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");
		sprite = new AnimatedSprite(sheet.getRange(257, 257), 0);
		collisionOffset = 8;
	}
	
	@Override
	public void consume() {
		game.link().life(game.link().life() + 1);
		consumed = true;
	}

}
