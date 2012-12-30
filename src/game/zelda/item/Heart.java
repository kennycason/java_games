package game.zelda.item;

import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sound.SoundBank;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class Heart extends AbstractItem {
	
	private AbstractSound sound;
	
	public Heart() {
		super();
		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");
		sprite = new AnimatedSprite(sheet.range(257, 257), 0);
		collisionOffset = 8;
		sound = SoundBank.getInstance().get("link_get_heart");
	}
	
	@Override
	public void consume() {
		game.link().life(game.link().life() + 1);
		consumed = true;
		sound.play();
	}

}
