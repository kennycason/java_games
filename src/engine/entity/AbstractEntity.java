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
	
	public abstract void handle();
	
	public void reset() {
		
	}
	
	public FaceDirection face() {
		return face;
	}
	
	public abstract void face(FaceDirection face);
	
	public int mapX() {
		return (x + (width() /*- collisionOffset()*/) / 2) / game.map().tileWidth();
	}

	public int mapY() {
		return (y + (height() /*- collisionOffset()*/) / 2) / game.map().tileHeight(); 
	}
}
