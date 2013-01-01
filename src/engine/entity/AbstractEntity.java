package engine.entity;

import engine.FaceDirection;
import engine.Game;
import engine.GameFactory;

import java.awt.Graphics2D;

public abstract class AbstractEntity extends AbstractCollidable {
	
	protected Game game; 

	protected FaceDirection face;
	
	/**
	 * used to make the "rectangle" in rectangle collision a bit smaller or larger
	 */

	public AbstractEntity() {
		game = GameFactory.get();
	}
	
	public abstract void draw(Graphics2D g);
	
	/**
	 * default render X and Y
	 * @return
	 */
	public int renderX() {
		return x() + (int)game.map().offset().x();
	}
	
	public int renderY() {
		return y() + (int)game.map().offset().y();
	}
	
	public abstract int width();
	
	public abstract int height();
	
	public abstract void handle();
	
	public void reset() {
		
	}
	
	public FaceDirection face() {
		return face;
	}
	
	public abstract void face(FaceDirection face);
	
	public int offsetX() {
		return (x + (width() /*- collisionOffset()*/) / 2) / game.map().tileWidth();
	}

	public int offsetY() {
		return (y + (height() /*- collisionOffset()*/) / 2) / game.map().tileHeight(); 
	}
}
