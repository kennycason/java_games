package engine.graphics.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SpriteUtils {
	
	public static BufferedImage scale(BufferedImage bi, int x, int y) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(2.0, 2.0);
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(bi, after);
		return after;
	}

	public static AnimatedSprite flipHorizontal(AnimatedSprite sprite) {
		return new AnimatedSprite(flipHorizontal(sprite.sprites()), sprite.animationSpeed());
	}
	
	public static AnimatedSprite flipVertical(AnimatedSprite sprite) {
		return new AnimatedSprite(flipVertical(sprite.sprites()), sprite.animationSpeed());
	}
	
	public static AnimatedSprite rotate(AnimatedSprite sprite, double deg) {
		return new AnimatedSprite(rotate(sprite.sprites(), deg), sprite.animationSpeed());
	}
	
	public static SpriteSheet flipHorizontal(SpriteSheet spriteSheet) {
		SpriteSheet newSpriteSheet = new SpriteSheet(spriteSheet.numTiles(), spriteSheet.tileWidth(), spriteSheet.tileHeight());
		for(int i = 0; i < newSpriteSheet.numTiles(); i++) {
		    newSpriteSheet.set(i, flipHorizontal(spriteSheet.get(i)));
		}
	    return newSpriteSheet;
	}
	
	public static SpriteSheet flipVertical(SpriteSheet spriteSheet) {
		SpriteSheet newSpriteSheet = new SpriteSheet(spriteSheet.numTiles(), spriteSheet.tileWidth(), spriteSheet.tileHeight());
		for(int i = 0; i < newSpriteSheet.numTiles(); i++) {
			 newSpriteSheet.set(i, flipVertical(spriteSheet.get(i)));
		}
	    return newSpriteSheet;
	}
	
	public static SpriteSheet rotate(SpriteSheet spriteSheet, double deg) {
		SpriteSheet newSpriteSheet = new SpriteSheet(spriteSheet.numTiles(), spriteSheet.tileWidth(), spriteSheet.tileHeight());
		for(int i = 0; i < newSpriteSheet.numTiles(); i++) {
			newSpriteSheet.set(i, rotate(spriteSheet.get(i), deg));
		}
	    return newSpriteSheet;
	}
	
	public static SimpleSprite flipHorizontal(SimpleSprite sprite) {
		BufferedImage bi = SpriteUtils.deepCopy(sprite.bufferedImage());
	    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-bi.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    bi = op.filter(bi, null);
	    return new SimpleSprite(bi);
	}
	
	public static SimpleSprite flipVertical(SimpleSprite sprite) {
		BufferedImage bi = SpriteUtils.deepCopy(sprite.bufferedImage());
	    AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
	    tx.translate(-bi.getWidth(null), -bi.getHeight(null));
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    bi = op.filter(bi, null);
	    return new SimpleSprite(bi);
	}
	
	public static SimpleSprite rotate(SimpleSprite sprite, double deg) {
		BufferedImage bi = SpriteUtils.deepCopy(sprite.bufferedImage());
	    AffineTransform tx = new AffineTransform();
	    tx.rotate(deg, bi.getWidth() / 2, bi.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_BILINEAR);
	    bi = op.filter(bi, null);
	    return new SimpleSprite(bi);
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		if (bi == null) {
			return null;
		}
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage imageToBufferedImage(Image image, int width,
			int height) {
		BufferedImage dest = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;
	}
	
	public static SimpleSprite negative(SimpleSprite sprite) {
		SimpleSprite tmp = new SimpleSprite(sprite);
		BufferedImage bi = tmp.bufferedImage();
        Color col;
        for (int x = 0; x < bi.getWidth(); x++) { //width
            for (int y = 0; y < bi.getHeight(); y++) { //height
                int RGBA = bi.getRGB(x, y); //gets RGBA data for the specific pixel
                if((RGBA >> 24) != 0x00) { // if not transparent
	                col = new Color(RGBA, true); //get the color data of the specific pixel
	                col = new Color(Math.abs(col.getRed() - 255),
	                        Math.abs(col.getGreen() - 255), Math.abs(col.getBlue() - 255)); //Swaps values
	                //i.e. 255, 255, 255 (white)
	                //becomes 0, 0, 0 (black)
	                bi.setRGB(x, y, col.getRGB()); //set the pixel to the altered colors
                }
            }
        }
        return tmp;
	}
	
}
