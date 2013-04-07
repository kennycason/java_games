package game.zelda.usables;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.Game;
import engine.entity.usable.AbstractUsableEntity;
import engine.graphics.sprite.SimpleSprite;
import engine.graphics.sprite.SpriteSheet;
import game.zelda.player.Link;

public class Bracelet extends AbstractUsableEntity {
	
	private SimpleSprite sprite;
	
	public Bracelet(Link link) {
		super(link);
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		sprite = entities.get(408);
		
		using = false;
	}
	
	public void draw(Graphics2D g) {
		if(!using) {
			return;
		}
		//sprite.draw(g, x() + game.map().offset().x(), y() + game.map().offset().x());
	}
	
	public void use() {
		if(using) {
			return;
		}
		using = true;

		x = user.x();
		y = user.y();

	}
	
	public boolean using() {
		return using;
	}
	
	public void handle() {
		if(!using) {
			return;
		}
	}
	
	public void menuDraw(Graphics2D g, int x, int y) {
		sprite.draw(g, x, y);
	}
	
	public String menuDisplayName() {
		return "L-1";
	}
	
	public void face(FaceDirection face) {
		
	}

	@Override
	public int width() {
		return sprite.width();
	}

	@Override
	public int height() {
		return sprite.height();
	}
	
}
