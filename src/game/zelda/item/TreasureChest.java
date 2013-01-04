package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.event.AbstractEvent;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;

public class TreasureChest extends AbstractItem {
	
	private AbstractEvent[] events;
	
	private AbstractSound openSound;
	
	private boolean open = false;
	
	public TreasureChest(int x, int y, AbstractEvent ... events) {
		super();
		mustTouch = true;
		walkable = false;
		disappearAfterConsume = false;
		this.events = events;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(377), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 0;
		open = false;
		openSound = Game.sounds.get("open_chest");
	}
	
	@Override
	public void consume() {
		if(!open) {
			if(game.link().mapX() == mapX() && game.link().mapY() == mapY() + 1) {
				consumed = true;
				openSound.play();
				open = true;
				for(AbstractEvent event : events) {
					game.map().events().add(event);
				}
			}
		}
	}

}
