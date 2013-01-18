package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;

public class HeartPiece extends AbstractItem {
	
	private AbstractSound sound;
	
	public HeartPiece() {
		this(0, 0);
	}
	
	public HeartPiece(int x, int y) {
		super();
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(157), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_heart_container");
	}
	
	@Override
	public void consume() {
		game.link().heartPieces(game.link().heartPieces() + 1);
		game.link().life(game.link().life() + 5);
		consumed = true;
		sound.play();
	}

}
