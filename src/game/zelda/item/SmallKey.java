package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;

public class SmallKey extends AbstractItem {
	
	private AbstractSound sound;
	
	public SmallKey() {
		this(0, 0);
	}
	
	public SmallKey(int x, int y) {
		super();
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(358), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_item");
	}
	
	@Override
	public void consume() {
		consumed = true;
		game.link().smallKeys(game.link().smallKeys() + 1);
		sound.play();
	}

}
