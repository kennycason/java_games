package game.zelda.item;

import engine.entity.item.AbstractItem;
import engine.sound.ISound;
import engine.sound.SoundBank;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class FullHeart extends AbstractItem {
	
	private ISound sound;
	
	public FullHeart() {
		super();
		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");
		sprite = new AnimatedSprite(sheet.range(252, 252), 0);
		collisionOffset = 8;
		mustTouch = true;
		sound = SoundBank.getInstance().get("link_get_heart_container");
	}
	
	@Override
	public void consume() {
		game.link().maxLife(game.link().maxLife() + 1);
		game.link().life(game.link().maxLife());
		consumed = true;
		sound.play();
	}

}
