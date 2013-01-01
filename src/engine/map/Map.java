package engine.map;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.entity.AbstractCollidable;
import engine.entity.AbstractEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.event.EventQueue;
import engine.math.PositionVector;
import engine.sprite.SimpleSprite;

/**
 * A simplified Map
 * @author destructo
 *
 */
public class Map {
	
	//private int[][][] layers;
	private BasicTile[][][] layers;
	
	private MetaTile[][] meta;

	private int width;

	private int height;

	private int tileWidth;

	private int tileHeight;
	
	private PositionVector offset; // on screen
	
	private int entityOffset = 8;

	private TiledSpriteSheet spriteSheet;

    private SimpleSprite bg = new SimpleSprite("sprites/map/Sand.bmp");
    
    private List<AbstractEnemy> enemies;
    
    private List<AbstractItem> items;
	
    private EventQueue events;
    
	public Map(int width, int height, int tileWidth, int tileHeight) {
		offset = new PositionVector();
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		layers = new BasicTile[3][width][height];
		meta = new MetaTile[width][height];
		enemies = new LinkedList<AbstractEnemy>();
		items = new LinkedList<AbstractItem>();
		events = new EventQueue();
	}
	
	public void drawBackground(Graphics2D g) {
		for (int y = -bg.height(); y <= Game.SCREEN_HEIGHT / bg.height() + bg.height(); y++) {
			for (int x = -bg.width(); x <= Game.SCREEN_WIDTH / bg.width() + bg.width(); x++) {
				bg.draw(g, 
						x * bg.width() + offset.x() % bg.width(), 
						y * bg.height() + offset.y() % bg.height());
			}
		}
	}

	public void drawBottomLayer(Graphics2D g) {
		draw(g, 0);
	}
	
	public void drawMiddleLater(Graphics2D g) {
		draw(g, 1);
	}
	
	public void drawTopLater(Graphics2D g) {
		draw(g, 2);
	}

	public void drawMetaLater(Graphics2D g) {
		draw(g, 3);
	}

	private void draw(Graphics2D g, int l) {
		int xOff = 0;
		int yOff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				xOff = x * tileWidth + offset.x();
				yOff = y * tileHeight + offset.y();
				if(xOff > -tileWidth && xOff < width * tileWidth && 
						yOff > -tileHeight && yOff < height * tileHeight) {
					if(l < 3) {
						if(layers[l][x][y].value() != 0) {
							layers[l][x][y].draw(g, 
									x * tileWidth + offset.x(), 
									y * tileHeight + offset.y());
						}
					} else {
						meta[x][y].draw(g, 
								x * tileWidth + offset.x(), 
								y * tileHeight + offset.y());
					}
				}
			}
		}
	}
	
	public boolean collide(AbstractCollidable collidable, int offX, int offY, PositionVector move) {
		if(offX < 0 || offY < 0 || offX >= width || offY >= height) {
			return true;
		}
		if(meta[offX][offY].value() == MetaTilesNumber.COLLISION) {
			meta[offX][offY].locate(meta[offX][offY].x() - (int)move.x() - (int)offset().x(), meta[offX][offY].y() - (int)move.y() - (int)offset().y());
			boolean collide = meta[offX][offY].rectangleCollide(collidable);
			meta[offX][offY].locate(meta[offX][offY].x() + (int)move.x() + (int)offset().x(), meta[offX][offY].y() + (int)move.y() + (int)offset().y());
			return collide;
		} else {
			return false;
		}
	}
	
	public PositionVector move(AbstractEntity entity, PositionVector move) {
		if(move.x() == 0 && move.y() == 0) {
			return move;
		}
		int x = entity.offsetX();
		int y = entity.offsetY();
		boolean collide = false;
		if(move.x() > 0) {
			if(x + 1 < width()) {
				if(y - 1 >= 0) {
					collide |= collide(entity, x + 1, y - 1, move);
				}

				collide |= collide(entity, x + 1, y, move);
				if(y + 1 < height()) {
					collide |= collide(entity, x + 1, y - 1, move);
				}
			}
		} else if(move.x() < 0) {
			if(x - 1 >= 0) {
				if(y - 1 >= 0) {
					collide |= collide(entity, x - 1, y - 1, move);
				}
				collide |= collide(entity, x - 1, y, move);
				if(y + 1 < height()) {
					collide |= collide(entity, x - 1, y + 1, move);
				}
			}
		}
		if(collide) {
			move.x(0);
		}		
		collide = false;
		if(move.y() > 0) {
			if(y + 1 < height()) {
				if(x - 1 >= 0) {
					collide |= collide(entity, x - 1, y + 1, move);
				}
				collide |= collide(entity, x, y + 1, move);
				if(x + 1 < width()) {
					collide |= collide(entity, x + 1, y + 1, move);
				}
			}
		} else if(move.y() < 0) {
			if(x - 1 >= 0) {
				if(y - 1 >= 0) {
					collide |= collide(entity, x - 1, y - 1, move);
				}
				collide |= collide(entity, x, y - 1, move);
				if(x + 1 < width()) {
					collide |= collide(entity, x + 1, y - 1, move);
				}
			}
		}
		if(collide) {
			move.y(0);
		}
		return move;
	}
	
	public PositionVector offset() {
		return offset;
	}

	public TiledSpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	public void spriteSheet(TiledSpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int metaLayer(int x, int y) {
		return meta[x][y].value();
	}

	public boolean isWalkable(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		if(metaLayer(x, y) != MetaTilesNumber.COLLISION) {
			return true;
		}
		return false;
	}
	
	public int entityOffset() {
		return entityOffset;
	}
	
	public int tileWidth() {
		return tileWidth;
	}
	
	public int tileHeight() {
		return tileHeight;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public void setSpriteSheet(TiledSpriteSheet tiledSpriteSheet) {
		this.spriteSheet = tiledSpriteSheet;
	}
	
	public TiledSpriteSheet spriteSheet() {
		return spriteSheet;
	}

	public MetaTile[][] meta() {
		return meta;
	}

	public BasicTile[][][] layers() {
		return layers;
	}
	
	public List<AbstractEnemy> enemies() {
		return enemies;
	}
	
	public List<AbstractItem> items() {
		return items;
	}
	
	public EventQueue events() {
		return events;
	}
	
}
