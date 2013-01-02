package engine.entity;


public abstract class AbstractCollidable {
	
	/**
	 * represents last position drew to screen
	 */
	public int x;
	
	public int y;
	
	/**
	 * used to make the "rectangle" in rectangle collision a bit smaller or larger
	 */
	protected int collisionOffset = 1;
	
	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
	
	public void x(int x) {
		this.x = x;
	}
	
	public void y(int y) {
		this.y = y;
	}

	public void locate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * x position in tiled map array
	 * @return
	 */
	public abstract int mapX();
	
	/**
	 * y position in tiled map array
	 * @return
	 */
	public abstract int mapY();
	
	public abstract int width();
	
	public abstract int height();
	
	public int collisionOffset() {
		return collisionOffset;
	}
	
	public void collisionOffset(int collisionOffset) {
		this.collisionOffset = collisionOffset;
	}
	
	public boolean rectangleCollide(AbstractCollidable entity) {
		if(entity.x() + entity.width() - entity.collisionOffset() < x() + collisionOffset() || 
				x() + width() - collisionOffset() < entity.x() + entity.collisionOffset()) {
			return false;
		}
		if(entity.y() + entity.height() - entity.collisionOffset() < y() + collisionOffset() || 
				y() + height() - collisionOffset() < entity.y() + entity.collisionOffset()) {
			return false;
		}	
		return true;		
	}

}
