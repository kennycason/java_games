package engine.map.tiled;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import engine.utils.Base64Coder;
import engine.utils.ZLibUtils;

/**
 * A simplified Map based of the Tiled Map Editor
 * 
 * @author destructo
 * 
 */
public class TiledMapLoader {

	private int width;

	private int height;

	private Map map;
	
	private final static Logger LOGGER = Logger.getLogger(TiledMapLoader.class);
	
	public TiledMapLoader() {
		
	}

	public Map load(String file) {

		map = null;
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		// lol nice use of Scanner ^_^
		Scanner s = new Scanner(is).useDelimiter("\\A");
		String xml = s.hasNext() ? s.next() : "";

		Document doc = Jsoup.parse(xml, "UTF-8");
		Element mapElement = doc.select("map").first();
		width = Integer.parseInt(mapElement.attr("width"));
		height = Integer.parseInt(mapElement.attr("height"));
		int tileWidth = Integer.parseInt(mapElement.attr("tilewidth"));
		int tileHeight = Integer.parseInt(mapElement.attr("tileheight"));
		// String orientation = map.attr("orientation");
		// layers = new int[3][width][height];

		// Only use the first tileset, the others are all for meta data
		Element tileset = mapElement.select("tileset").first();
		Element img = tileset.select("img").first(); // jsoup fixed image to img
		tileWidth = Integer.parseInt(tileset.attr("tilewidth"));
		tileHeight = Integer.parseInt(tileset.attr("tileheight"));

		map = new Map(width, height, tileWidth, tileHeight);
		map.setSpriteSheet(new TiledSpriteSheet(
				img.attr("source").substring(3), tileWidth, tileHeight));

		Elements layers = mapElement.select("layer");
		for (Element layer : layers) {
			int layerNumber = -1;
			if ("bottom".equals(layer.attr("name"))) {
				LOGGER.trace("Loading Bottom Layer");
				layerNumber = 0;
			} else if ("middle".equals(layer.attr("name"))) {
				LOGGER.trace("Loading Middle Layer");
				layerNumber = 1;
			} else if ("top".equals(layer.attr("name"))) {
				LOGGER.trace("Loading Top Layer");
				layerNumber = 2;
			} else if ("collision".equals(layer.attr("name"))) {
				LOGGER.trace("Loading Collision Layer");
				layerNumber = 3;
			} else if ("meta".equals(layer.attr("name"))) {
				LOGGER.trace("Loading Meta Layer");
				layerNumber = 4;
			}
			Element data = layer.select("data").first();
			String encoding = data.attr("encoding");
			String compression = data.attr("compression");
			if ("csv".equals(encoding)) {
				buildLayerFromCSV(layerNumber, data.text());
			} else if ("base64".equals(encoding) && "gzip".equals(compression)) {
				buildLayerFromGZIP(layerNumber, data.text());
			} else { // xml
				buildLayerFromXML(layerNumber, data.select("tile"));
			}
		}

		return map;
	}

	private void buildLayerFromCSV(int layer, String csv) {
		csv = csv.trim();
		int gid;
		String[] ys = csv.split(System.getProperty("line.separator"));
		for (int y = 0; y < ys.length; y++) {
			String[] xs = ys[y].split(",");
			if (xs.length == width * height) { // all in single row (my hack)
				int x = 0;
				y = 0;
				for (int i = 0; i < xs.length; i++, x++) {
					if (i > 0 && i % width == 0) {
						x = 0;
						y++;
					}
					gid = Integer.parseInt(xs[x]);
					if (layer < 3) {
						map.renderLayers()[layer][x][y] = new RenderTile(map
								.spriteSheet().get(gid), gid);
					} else if (layer == 3) {
						if (gid > 0) {
							gid -= (map.getSpriteSheet().numTiles() - 1);
						}
						map.collisionLayer()[x][y] = new CollisionTile(gid);
					} else if (layer == 4) {
						if (gid > 0) {
							gid -= (map.getSpriteSheet().numTiles() - 1);
						}
						map.metaLayer()[x][y] = new MetaTile(gid);
					}
				}
			} else { // standard row
				for (int x = 0; x < xs.length; x++) {
					gid = Integer.parseInt(xs[x]);
					if (layer < 3) {
						map.renderLayers()[layer][x][y] = new RenderTile(map
								.spriteSheet().get(gid), gid);
					} else if (layer == 3) {
						if (gid > 0) {
							gid -= (map.getSpriteSheet().numTiles() - 1);
						}
						map.collisionLayer()[x][y] = new CollisionTile(gid);
					} else if (layer == 4) {
						if (gid > 0) {
							gid -= (map.getSpriteSheet().numTiles() - 1);
						}
						map.metaLayer()[x][y] = new MetaTile(gid);
					}
				}
			}
		}
	}

	private void buildLayerFromXML(int layer, Elements tiles) {
		int x = 0;
		int y = 0;
		int i = 0;
		int gid;
		for (Element tile : tiles) {
			if (i > 0 && i % width == 0) {
				x = 0;
				y++;
			}
			gid = Integer.parseInt(tile.attr("gid"));
			if (layer < 3) {
				map.renderLayers()[layer][x][y] = new RenderTile(map.spriteSheet()
						.get(gid), gid);
			} else if (layer == 3) {
				if (gid > 0) {
					gid -= (map.getSpriteSheet().numTiles() - 1);
				}
				map.collisionLayer()[x][y] = new CollisionTile(gid);
			} else if (layer == 4) {
				if (gid > 0) {
					gid -= (map.getSpriteSheet().numTiles() - 1);
				}
				map.metaLayer()[x][y] = new MetaTile(gid);
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
}
