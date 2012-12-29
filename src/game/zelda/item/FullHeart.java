package game.zelda.item;

import engine.entity.item.AbstractItem;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class FullHeart extends AbstractItem {
	
	public FullHeart() {
		super();
		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");
		sprite = new AnimatedSprite(sheet.getRange(252, 252), 0);
		collisionOffset = 8;
		mustTouch = true;
	}
	
	@Override
	public void consume() {
		game.link().maxLife(game.link().maxLife() + 1);
		game.link().life(game.link().maxLife());
		consumed = true;
	}

}
