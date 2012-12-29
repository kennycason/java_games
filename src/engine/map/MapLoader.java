package engine.map;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import engine.utils.Base64Coder;
import engine.utils.ZLibUtils;

/**
 * A simplified Map based of the Tiled Map Editor 
 * @author destructo
 *
 */
public class MapLoader {
	
	private int width;
	
	private int height;
	
	private Map map;

	public Map load(String filename) {
		try {
			map = null;
			Document doc = Jsoup.parse(new File(filename), "UTF-8");
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
			map.setSpriteSheet(new TiledSpriteSheet(img.attr("source").substring(3), tileWidth, tileHeight));

			Elements layers = mapElement.select("layer");
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
		return map;
	}
	
	private void buildLayerFromCSV(int layer, String csv) {
		csv = csv.trim();
		int gid;
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
					gid = Integer.parseInt(xs[x]);
					if(layer < 2) {
						map.layers()[layer][x][y] = new BasicTile(map.spriteSheet().get(gid), gid);
					} else if (layer == 2) {
						map.meta()[x][y] = new MetaTile(gid);
					}
				}
			} else { // standard row
				for(int x = 0; x < xs.length; x++) {
					gid = Integer.parseInt(xs[x]);
					if(layer < 2) {
						map.layers()[layer][x][y] = new BasicTile(map.spriteSheet().get(gid), gid);
					} else if (layer == 2) {
						map.meta()[x][y] = new MetaTile(gid);
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
			if(layer < 2) {
				map.layers()[layer][x][y] = new BasicTile(map.spriteSheet().get(gid), gid);
			} else if (layer == 2) {
				map.meta()[x][y] = new MetaTile(gid);
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
