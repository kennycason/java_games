package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;

public class TreasureChest extends AbstractItem {
	
	private AbstractItem contents;
	
	private AbstractSound openSound;
	
	private boolean open = false;
	
	public TreasureChest(AbstractItem contents) {
		this(contents, 0, 0);
	}
	
	public TreasureChest(AbstractItem contents, int x, int y) {
		super();
		mustTouch = true;
		walkable = false;
		disappearAfterConsume = false;
		this.contents = contents;
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
			if(game.link().offsetX() == offsetX() && game.link().offsetY() == offsetY() + 1) {
				consumed = true;
				openSound.play();
				open = true;
				contents.consume();
			}
		}
	}

}
