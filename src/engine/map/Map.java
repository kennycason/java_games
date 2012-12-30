package engine.map;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.entity.AbstractCollidable;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.event.EventQueue;
import engine.math.Vector2D;
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
	
	private Vector2D offset; // on screen
	
	private int entityOffset = 8;

	private TiledSpriteSheet spriteSheet;

    private SimpleSprite bg = new SimpleSprite("sprites/map/Sand.bmp");
    
    private List<AbstractEnemy> enemies;
    
    private List<AbstractItem> items;
	
    private EventQueue events;
    
	public Map(int width, int height, int tileWidth, int tileHeight) {
		offset = new Vector2D();
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		layers = new BasicTile[2][width][height];
		meta = new MetaTile[width][height];
		enemies = new LinkedList<AbstractEnemy>();
		items = new LinkedList<AbstractItem>();
		events = new EventQueue();
	}
	
	public void drawBackground(Graphics2D g) {
		for (int y = -bg.height(); y <= Game.SCREEN_HEIGHT / bg.height() + bg.height(); y++) {
			for (int x = -bg.width(); x <= Game.SCREEN_WIDTH / bg.width() + bg.width(); x++) {
				bg.draw(g, 
						x * bg.width() + (int) offset.x() % bg.width(), 
						y * bg.height() + (int) offset.y() % bg.height());
			}
		}
	}

	public void drawBottomLayer(Graphics2D g) {
		draw(g, 0);
	}
	
	public void drawTopLater(Graphics2D g) {
		draw(g, 1);
	}

	public void drawMetaLater(Graphics2D g) {
		draw(g, 2);
	}

	private void draw(Graphics2D g, int l) {
		int xOff = 0;
		int yOff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				xOff = x * tileWidth + (int) offset.x();
				yOff = y * tileHeight + (int) offset.y();
				if(xOff > -tileWidth && xOff < width * tileWidth && 
						yOff > -tileHeight && yOff < height * tileHeight) {
					if(l < 2) {
						if(layers[l][x][y].value() != 0) {
							layers[l][x][y].draw(g, 
									x * tileWidth + (int) offset.x(), 
									y * tileHeight + (int) offset.y());
						}
					} else {
					//	if(meta[x][y].value() != 0) {
							meta[x][y].draw(g, 
									x * tileWidth + (int) offset.x(), 
									y * tileHeight + (int) offset.y());
					//	}
					}
				}
			}
		}
	}
	
	/**
	 * what a mess :P
	 * @param collidable
	 * @param offX
	 * @param offY
	 * @param xShift
	 * @param yShift
	 * @return
	 */
	public boolean collide(AbstractCollidable collidable,int offX, int offY, int xShift, int yShift) {
		if(offX < 0 || offY < 0 || offX >= width || offY >= height) {
			return true;
		}
		if(meta[offX][offY].value() > 0) {
			meta[offX][offY].locate(meta[offX][offY].x() - xShift - (int)offset().x(), meta[offX][offY].y() - yShift - (int)offset().y());
			boolean collide = meta[offX][offY].rectangleCollide(collidable);
			meta[offX][offY].locate(meta[offX][offY].x() + xShift + (int)offset().x(), meta[offX][offY].y() + yShift + (int)offset().y());
			return collide;
		} else {
			return false;
		}
	}
	
	public Vector2D offset() {
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
