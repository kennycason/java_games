package engine.map;

import engine.Game;
import engine.math.Vector2D;
import engine.sprite.SimpleSprite;
import engine.utils.Base64Coder;
import engine.utils.ZLibUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A simplified Map Loader based of the Tiled Map Editor 
 * @author destructo
 *
 */
public class Map {

	private int[][][] layers;

	private int width;

	private int height;

	private int tileWidth;

	private int tileHeight;
	
	private Vector2D offset; // on screen
	
	private int entityOffset = 8;

	private TiledSpriteSheet spriteSheet;

    private SimpleSprite bg = new SimpleSprite("sprites/map/Sand.bmp");

	public Map() {
		offset = new Vector2D();
	}

	public void load(String filename) {

		Document doc;
		try {
			doc = Jsoup.parse(new File(filename), "UTF-8");
			Element map = doc.select("map").first();
			width = Integer.parseInt(map.attr("width"));
			height = Integer.parseInt(map.attr("height"));
			tileWidth = Integer.parseInt(map.attr("tilewidth"));
			tileHeight = Integer.parseInt(map.attr("tileheight"));
			// String orientation = map.attr("orientation");
			layers = new int[3][width][height];
			
			// Only use the first tileset, the others are all for meta data
			Element tileset = map.select("tileset").first();
			Element img = tileset.select("img").first(); // jsoup fixed image to img
			int tileWidth = Integer.parseInt(tileset.attr("tilewidth"));
			int tileHeight = Integer.parseInt(tileset.attr("tileheight"));
			
			spriteSheet = new TiledSpriteSheet(img.attr("source").substring(3), tileWidth, tileHeight);

			Elements layers = map.select("layer");
			for(Element layer : layers) {
				int layerNumber = -1;
				if("bottom".equals(layer.attr("name"))) {
					layerNumber = 0;
				} else if("top".equals(layer.attr("name"))) {
					layerNumber = 1;
				} else if("meta".equals(layer.attr("name"))) {
					layerNumber = 2;
				}
				Element data = layer.select("data").first();
				String encoding = data.attr("encoding");
				String compression = data.attr("compression");
				if("csv".equals(encoding)) {
					buildLayerFromCSV(layerNumber, data.text());
				} else if("base64".equals(encoding) && "gzip".equals(compression) ) {
					buildLayerFromGZIP(layerNumber, data.text());
				} else { // xml
					buildLayerFromXML(layerNumber, data.select("tile"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void buildLayerFromCSV(int layer, String csv) {
		csv = csv.trim();
		String[] ys = csv.split(System.getProperty("line.separator"));
		for(int y = 0; y < ys.length; y++) {
			String[] xs = ys[y].split(",");
			if(xs.length ==  width * height) { // all in single row (my hack)
				int x = 0;y = 0;
				for(int i = 0; i < xs.length; i++, x++) {
					if(i > 0 && i % width == 0) {
						x = 0;
						y++;
					}
					layers[layer][x][y] = Integer.parseInt(xs[i]);
				}
			} else { // standard row
				for(int x = 0; x < xs.length; x++) {
					layers[layer][x][y] = Integer.parseInt(xs[x]);
				}
			}
		}
	}

	private void buildLayerFromXML(int layer, Elements tiles) {
		int x = 0;
		int y = 0;
		int i = 0;
		for (Element tile : tiles) {
			if (i > 0 && i % width == 0) {
				x = 0;
				y++;
			}
			layers[layer][x][y] = Integer.parseInt(tile.attr("gid"));
			if(layer == 0) {
			}
			x++;
			i++;
		}

	}
	
	private void buildLayerFromGZIP(int layer, String data) {
		System.out.println("gzip");
		System.out.println(data);
		System.out.println(Base64Coder.decode(data));
		System.out.println(ZLibUtils.inflate(Base64Coder.decode(data)));
		// @TOOD

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

	public void drawBottomLayer(Graphics g) {
		draw(g, 0);
	}

	public void drawTopLayer(Graphics g) {
		draw(g, 1);
	}

	private void draw(Graphics g, int l) {
		int xOff = 0;
		int yOff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				xOff = x * tileWidth + (int) offset.x();
				yOff = y * tileHeight + (int) offset.y();
				if(xOff > -tileWidth && xOff < width * tileWidth && 
						yOff > -tileHeight && yOff < height * tileHeight) {
					if(layers[l][x][y] != 0) {
						spriteSheet.get(layers[l][x][y]).draw(g, 
								x * tileWidth + (int) offset.x(), 
								y * tileHeight + (int) offset.y());
					}
				}
			}
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
		return layers[2][x][y];
	}

	public boolean isWalkable(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		if(metaLayer(x, y) != MetaTiles.COLLISION) {
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
	
}
