package game.zelda.usables;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.Game;
import engine.entity.usable.AbstractUsableEntity;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteSheet;

public class Bracelet extends AbstractUsableEntity {
	
	private SimpleSprite sprite;
	
	public Bracelet() {
		super();
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		sprite = entities.get(408);
		
		using = false;
	}
	
	public void draw(Graphics2D g) {
		if(!using) {
			return;
		}
		//sprite.draw(g, renderX() , renderY());
	}
	
	public void use() {
		if(using) {
			return;
		}
		using = true;

		x = game.link().x();
		y = game.link().y();

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
