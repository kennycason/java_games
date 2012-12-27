package engine.entity.weapon;

import engine.FaceDirection;
import engine.Game;
import engine.entity.AbstractEntity;
import engine.sprite.SpriteResources;
import engine.sprite.SpriteSheet;

import java.awt.Graphics2D;

public abstract class AbstractWeapon extends AbstractEntity {
	
	protected SpriteSheet entities;
	
	protected double damage;
	
	protected AbstractWeapon(Game game) {
		super(game);
		entities = (SpriteSheet) SpriteResources.getInstance().get("entities");
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void use();
	
	public abstract boolean using();
	
	public abstract void handle();

	public double damage() {
		return damage;
	}
	
	public abstract void menuDraw(Graphics2D g, int x, int y);
	
	public abstract String menuDisplayName();
	
	public void face(FaceDirection face) {
		
	}
	
}
