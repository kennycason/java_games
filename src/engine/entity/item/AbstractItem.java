package engine.entity.item;

import java.awt.Graphics2D;

import engine.entity.AbstractSimpleEntity;

public abstract class AbstractItem extends AbstractSimpleEntity {

	protected boolean consumed;
	
	protected boolean mustTouch; // if true link must physically touch the item to obtain. i.e. can't use boomerang
	
	public AbstractItem() {
		super();
		consumed = false;
		mustTouch = false;
		locate(0, 0);
	}
	
	@Override
	public void handle() {
		if(rectangleCollide(game.link())) {
			consume();
		}
	}
	
	public abstract void consume();
	
	public boolean consumed() {
		return consumed;
	}
	
	public boolean mustTouch() {
		return mustTouch;
	}
	
	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g, renderX(), renderY());
	}

}
