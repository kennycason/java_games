package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;
import game.zelda.player.Link;

public class HeartPiece extends AbstractItem {
	
	private AbstractSound sound;
	
	private Link link;
	
	public HeartPiece(Link link) {
		this(link, 0, 0);
	}
	
	public HeartPiece(Link link, int x, int y) {
		super(link);
		this.link = link;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(157), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_heart_container");
	}
	
	@Override
	public void consume() {
		link.heartPieces(link.heartPieces() + 1);
		link.life(link.life() + 5);
		consumed = true;
		sound.play();
	}

}
