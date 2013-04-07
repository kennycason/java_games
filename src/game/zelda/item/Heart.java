package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;
import game.zelda.player.Link;

public class Heart extends AbstractItem {
	
	private AbstractSound sound;
	
	private Link link;
	
	public Heart(Link link) {
		super(link);
		this.link = link;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(257), 0);
		collisionOffset = 4;
		sound = Game.sounds.get("link_get_heart");
	}
	
	@Override
	public void consume() {
		if(!justDropped) {
			link.life(link.life() + 1);
			consumed = true;
			sound.play();
		} else {
			if(Game.clock.elapsedMillis() - droppedTime > 500) {
				justDropped = false;
			}
		}
	}

}
