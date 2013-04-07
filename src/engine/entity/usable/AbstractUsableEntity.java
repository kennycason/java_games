package engine.entity.usable;

import java.awt.Graphics2D;

import engine.entity.AbstractLivingEntity;
import engine.FaceDirection;
import engine.entity.AbstractEntity;

public abstract class AbstractUsableEntity extends AbstractEntity {
	
	protected boolean using;
	
	protected AbstractLivingEntity user;
	
	public AbstractUsableEntity(AbstractLivingEntity user) {
		super();
		this.user = user;
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
