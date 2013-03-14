package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.event.AbstractEvent;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.map.tiled.MetaTilesNumber;
import engine.sound.AbstractSound;

public class TreasureChest extends AbstractItem {
	
	private AbstractEvent[] events;
	
	private AbstractSound openSound;
	
	private boolean open = false;
	
	public TreasureChest(int x, int y, AbstractEvent ... events) {
		super();
		mustTouch = true;
		walkable = false;
		disappearAfterConsume = true;
		this.events = events;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(377), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 0;
		open = false;
		openSound = Game.sounds.get("open_chest");
		game.map().collisionLayer()[x][y].value(MetaTilesNumber.COLLISION);
	}
	
	@Override
	public void consume() {
		if(!open) {
			if(game.link().mapX() == mapX() && game.link().mapY() == mapY() + 1) {
				game.map().collisionLayer()[mapX()][mapY()].value(0);
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
