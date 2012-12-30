package game.zelda.item;

import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public abstract class AbstractRupee extends AbstractItem {
	
	protected int value;
	
	protected AbstractSound sound;
	
	protected AbstractRupee(int value, int spriteNumber) {
		super();
		this.value = value;
		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");
		sprite = new AnimatedSprite(sheet.range(spriteNumber, spriteNumber), 0);
		collisionOffset = 2;
	}
	
	@Override
	public void consume() {
		if(!justDropped) {
			game.link().rupees(game.link().rupees() + value);
			consumed = true;
			sound.play();
		} else {
			if(System.currentTimeMillis() - droppedTime > 500) {
				justDropped = false;
			}
		}
	}

}
