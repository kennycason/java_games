package engine.map.tiled;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.entity.AbstractCollidable;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.event.EventQueue;
import engine.math.PositionVector;
import engine.sprite.SimpleSprite;
import game.zelda.map.meta.LinkFallingMetaEvent;

/**
 * A simplified Map
 * @author destructo
 *
 */
public class Map {
	
	/**
	 * three layers for render:
	 * bottom - everything under player/entitiees
	 * middle - misc. rendering, typically used for tiles that can change during game play, i.e. a locked door, or grass that can be cut
	 * top layer - everything on top of player/entities, cross walks, tops of tree, tops of buildings
	 */
	private RenderTile[][][] renderLayers;
	
	/**
	 * simple layer solely for collision
	 */
	private CollisionTile[][] collisionLayer;
	
	/**
	 * specialized layer for representing a wide variety of cases, 
	 *  i.e. lava, swamp, cuttable grass, bombable door, door, hole, warp, etc
	 */
	private MetaTile[][] metaLayer;
	
	/**
	 * the players initial starting X, Y position
	 */
	private int startX;
	
	private int startY;
	
	private int width;

	private int height;

	private int tileWidth;

	private int tileHeight;
	
	private PositionVector offset; // on screen
	
	private int entityOffset = 8;

	private TiledSpriteSheet spriteSheet;

    private SimpleSprite bg;
    
    private List<AbstractEnemy> enemies;
    
    private List<AbstractItem> items;
    
    private EventQueue events;
    
    //private HashMap<Integer, IEvent> metaEvents;
    
    // private final static Logger LOGGER = Logger.getLogger(Map.class);
    
	public Map(int width, int height, int tileWidth, int tileHeight) {
		offset = new PositionVector();
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		renderLayers = new RenderTile[3][width][height];
		collisionLayer = new CollisionTile[width][height];
		metaLayer = new MetaTile[width][height];
		enemies = new LinkedList<AbstractEnemy>();
		items = new LinkedList<AbstractItem>();
		//metaEvents = new HashMap<Integer, IEvent>();
		events = new EventQueue();
		startX = 0;
		startY = 0;
		// look to decouple this later, still experimenting with a good way of handling map events
		//metaEvents.put(MetaTilesNumber.HOLE, new LinkFallingMetaEvent());
	}
	
	public void drawBackground(Graphics2D g) {
		if(bg != null) {
			for (int y = -bg.height(); y <= Game.SCREEN_HEIGHT / bg.height() + bg.height(); y++) {
				for (int x = -bg.width(); x <= Game.SCREEN_WIDTH / bg.width() + bg.width(); x++) {
					bg.draw(g, 
							x * bg.width() + offset.x() % bg.width(), 
							y * bg.height() + offset.y() % bg.height());
				}
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

	public void drawMetaLaters(Graphics2D g) {
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
						if(renderLayers[l][x][y].value() != 0) {
							renderLayers[l][x][y].draw(g, 
									x * tileWidth + offset.x(), 
									y * tileHeight + offset.y());
						}
					} else {
						collisionLayer[x][y].locate(
								x * tileWidth + offset.x(), 
								y * tileHeight + offset.y());
						metaLayer[x][y].locate(
								x * tileWidth + offset.x(), 
								y * tileHeight + offset.y());
					}
				} else { // just locate tiles for collision, but don't draw them
					if(l < 3) {
//						if(layers[l][x][y].value() != 0) {
//							layers[l][x][y].locate(x * tileWidth + offset.x(), y * tileHeight + offset.y());
//						}
					} else {
						collisionLayer[x][y].locate(
								x * tileWidth + offset.x(), 
								y * tileHeight + offset.y());
						metaLayer[x][y].locate(
								x * tileWidth + offset.x(), 
								y * tileHeight + offset.y());
					}
				}
			}
		}
	}
	
	public boolean collide(AbstractCollidable collidable, int offX, int offY, PositionVector move) {
		if(offX < 0 || offY < 0 || offX >= width || offY >= height) {
			return false; // really need to solve this issue
		}
		if(collisionLayer[offX][offY].value() == MetaTilesNumber.COLLISION) {
			collisionLayer[offX][offY].locate(collisionLayer[offX][offY].x() - (int)move.x() - (int)offset().x(), collisionLayer[offX][offY].y() - (int)move.y() - (int)offset().y());
			boolean collide = collisionLayer[offX][offY].rectangleCollide(collidable);
			collisionLayer[offX][offY].locate(collisionLayer[offX][offY].x() + (int)move.x() + (int)offset().x(), collisionLayer[offX][offY].y() + (int)move.y() + (int)offset().y());
			return collide;
		} else {
			return false;
		}
	}
	
//	public boolean metaCollide(AbstractCollidable collidable, int offX, int offY) {
//		if(offX < 0 || offY < 0 || offX >= width || offY >= height) {
//			return false;
//		}
//		if(metaLayer[offX][offY].value() > 0) {
//			metaLayer[offX][offY].locate(metaLayer[offX][offY].x() - (int)offset().x(), metaLayer[offX][offY].y() - (int)offset().y());
//			boolean collide = metaLayer[offX][offY].rectangleCollide(collidable);
//			metaLayer[offX][offY].locate(metaLayer[offX][offY].x() + (int)offset().x(), metaLayer[offX][offY].y() + (int)offset().y());
//			return collide;
//		} else {
//			return false;
//		}
//	}
	
	public void handleMetaEvents(AbstractCollidable entity) {
		int x = entity.mapX();
		int y = entity.mapY();
		// System.out.println("meta event: " + metaLayer[x][y].value());
		switch(metaLayer[x][y].value()) {
			case MetaTilesNumber.HOLE:
				events.add(new LinkFallingMetaEvent());
				break;
		}
//		if(metaEvents.containsKey(metaLayer[x][y].value())) {
//			IEvent event = metaEvents.get(metaLayer[x][y].value());
//			if(event.ready()) {
//				event.trigger();
//			}
//		}
	}

	public PositionVector move(AbstractCollidable entity, PositionVector move) {
		if(move.x() == 0 && move.y() == 0) {
			return move;
		}
		int x = entity.mapX();
		int y = entity.mapY();
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

	public int collisionLayer(int x, int y) {
		return collisionLayer[x][y].value();
	}

	public boolean isWalkable(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		if(collisionLayer(x, y) != MetaTilesNumber.COLLISION) {
			return true;
		}
		return false;
	}
	
	public int entityOffset() {
		return entityOffset;
	}
	
	public void startX(int startX) {
		this.startX = startX;
	}
	
	public int startX() {
		return startX;
	}
	
	public void startY(int startY) {
		this.startY = startY;
	}
	
	public int startY() {
		return startY;
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

	public CollisionTile[][] collisionLayer() {
		return collisionLayer;
	}
	
	public MetaTile[][] metaLayer() {
		return metaLayer;
	}

	public RenderTile[][][] renderLayers() {
		return renderLayers;
	}
	
	public void setBackground(SimpleSprite bg) {
		this.bg = bg;
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
