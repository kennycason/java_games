package engine.map;

import engine.sprite.SimpleSprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TiledSpriteSheet {
	
	private SimpleSprite[] sprites;
	
	private int tileWidth;

	private int tileHeight;
	
	private int numTiles;
	
	public TiledSpriteSheet(int numTiles, int width, int height) {
		sprites = new SimpleSprite[numTiles];
		this.numTiles = numTiles;
		this.tileWidth = width;
		this.tileHeight = height;
	}
	
	public TiledSpriteSheet(String filename, int width, int height) {
		this.tileWidth = width;
		this.tileHeight = height;
		SimpleSprite sprite = new SimpleSprite(filename);
		int numTilesX = sprite.width() / width;
		int numTilesY = sprite.height() / height;
		numTiles = numTilesX * numTilesY + 1;
		sprites = new SimpleSprite[numTiles];
		for(int y = 0; y < numTilesY; y++) {
			for(int x = 0; x < numTilesX; x++) {
				BufferedImage dest = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = dest.createGraphics();
				int startX = x * width;
				int startY = y * height;
				g2.drawImage(sprite.bufferedImage(), 
						0, 0, width, height,
						startX, startY, startX + width, startY + height, 
						null);
				g2.dispose();
				sprites[y * numTilesX + x + 1] = new SimpleSprite(dest);
			}
		}
	}
	
	public SimpleSprite get(int i) {
		return sprites[i];
	}
	
	public void set(int i, SimpleSprite sprite) {
		sprites[i] = sprite;
	}
	
	public int numTiles() {
		return numTiles;
	}
	
	public int tileWidth() {
		return tileWidth;
	}
	
	public int tileHeight() {
		return tileHeight;
	}
	
}
