package game.zelda.item;

import engine.Game;
import engine.entity.item.AbstractItem;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.sound.AbstractSound;
import game.zelda.player.Link;

public class SmallKey extends AbstractItem {
	
	private AbstractSound sound;
	
	private Link link;
	
	public SmallKey(Link link) {
		this(link, 0, 0);
	}
	
	public SmallKey(Link link, int x, int y) {
		super(link);
		this.link = link;
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		sprite = new AnimatedSprite(sheet.range(358), 0);
		locate(x * sprite.width(), y * sprite.height());
		collisionOffset = 1;
		mustTouch = true;
		sound = Game.sounds.get("link_get_item");
	}
	
	@Override
	public void consume() {
		consumed = true;
		link.smallKeys(link.smallKeys() + 1);
		sound.play();
	}

}
