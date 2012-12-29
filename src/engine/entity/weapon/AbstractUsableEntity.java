package engine.entity.weapon;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.Game;
import engine.entity.AbstractEntity;

public abstract class AbstractUsableEntity extends AbstractEntity {
	
	protected AbstractUsableEntity(Game game) {
		super(game);
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void use();
	
	public abstract boolean using();
	
	public abstract void handle();
	
	public abstract void menuDraw(Graphics2D g, int x, int y);
	
	public abstract String menuDisplayName();
	
	public void face(FaceDirection face) {
		
	}
	
}
