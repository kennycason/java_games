package game.zelda.usables;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.Game;
import engine.entity.usable.AbstractUsableEntity;
import engine.sound.AbstractSound;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteSheet;

public class Ocarina extends AbstractUsableEntity {
	
	private SimpleSprite sprite;
	
	private boolean using;

	private AbstractSound sound;
	
	private long playTime;
	
	public Ocarina() {
		super();
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		sprite = entities.get(361);
		
		using = false;
		sound = Game.sounds.get("tune_of_ages");
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
		sound.play();
		using = true;

		x = game.link().x();
		y = game.link().y();
		playTime = System.currentTimeMillis();

	}
	
	public boolean using() {
		return using;
	}
	
	public void handle() {
		if(!using) {
			return;
		}
		if(System.currentTimeMillis() - playTime > 1000) {
			using = false;
		}
	}
	
	public void menuDraw(Graphics2D g, int x, int y) {
		sprite.draw(g, x, y);
	}
	
	public String menuDisplayName() {
		return "";
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
