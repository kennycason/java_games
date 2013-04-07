package game.zelda.item;

import game.zelda.player.Link;
import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;

public abstract class AbstractRupee extends AbstractItem {
	
	protected int value;
	
	protected AbstractSound sound;
	
	protected AbstractRupee(Link link, int value, int spriteNumber) {
		super(link);
		this.value = value;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(spriteNumber), 0);
		collisionOffset = 2;
	}
	
	@Override
	public void consume() {
		if(justDropped) {
			if(Game.clock.elapsedMillis() - droppedTime > 500) {
				justDropped = false;
			}
		}
		if(!justDropped) {
			((Link) owner).rupees(((Link) owner).rupees() + value);
			consumed = true;
			sound.play();
		} else {
			if(Game.clock.elapsedMillis() - droppedTime > 500) {
				justDropped = false;
			}
		}
	}

}
