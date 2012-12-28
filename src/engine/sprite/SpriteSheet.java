package engine.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteSheet implements ISpriteBankResource {
	
	private SimpleSprite[] sprites;
	
	private int tileWidth;

	private int tileHeight;
	
	private int numTiles;
	
	public SpriteSheet(int numTiles, int width, int height) {
		sprites = new SimpleSprite[numTiles];
		this.numTiles = numTiles;
		this.tileWidth = width;
		this.tileHeight = height;
	}
	
	public SpriteSheet(String filename, int width, int height) {
		this(new SimpleSprite(filename), width, height);
	}
	
	public SpriteSheet(SimpleSprite sprite, int width, int height) {
		this.tileWidth = width;
		this.tileHeight = height;
		int numTilesX = sprite.width() / width;
		int numTilesY = sprite.height() / height;
		numTiles = numTilesX * numTilesY;
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
				sprites[y * numTilesX + x] = new SimpleSprite(dest);
			}
		}
	}
	
	public SpriteSheet getRange(int start, int end) {
		int num = end - start + 1;
		SpriteSheet sheet = new SpriteSheet(num, tileWidth(), tileHeight());
		for(int i = 0; i < num; i++) {
			sheet.set(i, this.get(start + i));
		}
		return sheet;
	}
	
	public SpriteSheet getRange(int[] indexes) {
		SpriteSheet sheet = new SpriteSheet(indexes.length, tileWidth(), tileHeight());
		for(int i = 0; i < numTiles; i++) {
			sheet.set(i, this.get(indexes[i]));
		}
		return sheet;
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
