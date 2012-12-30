package engine.entity.item;

import java.awt.Graphics2D;

import engine.entity.AbstractSimpleEntity;
import engine.map.MetaTilesNumber;

public abstract class AbstractItem extends AbstractSimpleEntity {

	protected boolean consumed;
	
	protected boolean mustTouch; // if true link must physically touch the item to obtain. i.e. can't use boomerang
	
	protected boolean disappearAfterConsume;
	
	protected boolean walkable;
	
	public AbstractItem() {
		super();
		consumed = false;
		mustTouch = false;
		walkable = true;
		disappearAfterConsume = true;
		locate(0, 0);
	}
	
	@Override
	public void handle() {
		if(walkable) {
			if(rectangleCollide(game.link())) {
				consume();
			}
		} else {
			game.map().meta()[offsetX()][offsetY()].value(MetaTilesNumber.COLLISION);
		}
	}
	
	public abstract void consume();
	
	public boolean consumed() {
		if(!disappearAfterConsume) {
			return false;
		}
		return consumed;
	}
	
	public boolean walkable() {
		return walkable;
	}
	
	public boolean mustTouch() {
		return mustTouch;
	}
	
	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g, renderX(), renderY());
	}

}
