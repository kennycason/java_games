package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;

public class Heart extends AbstractItem {
	
	private AbstractSound sound;
	
	public Heart() {
		super();
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(257), 0);
		collisionOffset = 4;
		sound = Game.sounds.get("link_get_heart");
	}
	
	@Override
	public void consume() {
		if(!justDropped) {
			game.link().life(game.link().life() + 1);
			consumed = true;
			sound.play();
		} else {
			if(Game.clock.elapsedMillis() - droppedTime > 500) {
				justDropped = false;
			}
		}
	}

}
