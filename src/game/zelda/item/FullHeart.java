package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;

public class FullHeart extends AbstractItem {
	
	private AbstractSound sound;
	
	public FullHeart() {
		this(0, 0);
	}
	
	public FullHeart(int x, int y) {
		super();
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(252, 252), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_heart_container");
	}
	
	@Override
	public void consume() {
		game.link().maxLife(game.link().maxLife() + 1);
		game.link().life(game.link().maxLife());
		consumed = true;
		sound.play();
	}

}
