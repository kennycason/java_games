package engine.graphics.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import org.apache.log4j.Logger;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class SimpleSprite extends AbstractSprite {

	private BufferedImage bi;
	
	private Texture texture;
	
	private static final Logger LOGGER = Logger.getLogger(SimpleSprite.class.getName());

	public SimpleSprite(String file) {
		try {
			bi = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
		} catch (IOException e) {
			LOGGER.error("Can not read Sprite file: " + file);
			e.printStackTrace();
		}
		spriteWidth = bi.getWidth();
		spriteHeight = bi.getHeight();
	}

	public SimpleSprite(File file) {
		try {
			bi = ImageIO.read(file);
		} catch (IOException e) {
			LOGGER.error("Can not read Sprite file: " + file);
			e.printStackTrace();
		}
		spriteWidth = bi.getWidth();
		spriteHeight = bi.getHeight();
	}

	public SimpleSprite(URL url) {
		try {
			this.bi = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		spriteWidth = bi.getWidth();
		spriteHeight = bi.getHeight();
	}

	public SimpleSprite(BufferedImage bi) {
		this.bi = SpriteUtils.deepCopy(bi);
		spriteWidth = bi.getWidth();
		spriteHeight = bi.getHeight();
	}

	public SimpleSprite(SimpleSprite sprite) {
		bi = SpriteUtils.deepCopy(sprite.bufferedImage());
		spriteWidth = bi.getWidth();
		spriteHeight = bi.getHeight();
	}

	public BufferedImage bufferedImage() {
		return bi;
	}

	public void draw(Graphics g, int x, int y) {
		locate(x, y); // for collision detection
		g.drawImage(bi, x, y, x + spriteWidth, y + spriteHeight, 0, 0, spriteWidth, spriteHeight, null);
	}
	
	public void draw(GL2 gl, int x, int y) {
		locate(x, y);
		gl.glLoadIdentity();
		texture().enable(gl);
		texture().bind(gl);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL2.GL_POLYGON);
			gl.glTexCoord2d (0, 0);
			gl.glVertex2d (x(), y());
			gl.glTexCoord2d(1,0);
			gl.glVertex2d (x() + width(), y());
			gl.glTexCoord2d(1,1);
			gl.glVertex2d (x() +width(), y() + height());
			gl.glTexCoord2d(0,1);
			gl.glVertex2d (x(), y() + height());
		gl.glEnd();
	}

	public void addTransparency(final int rgb) {
		ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = rgb | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(bi.getSource(), filter);
		Image i = Toolkit.getDefaultToolkit().createImage(ip);
		bi = SpriteUtils.imageToBufferedImage(i, spriteWidth, spriteHeight);
		texture = null;
	}
	
	public  Texture texture() {
		if(texture == null) {
			texture = AWTTextureIO.newTexture(GLProfile.get(GLProfile.GL2), bi, true);
		}
		return texture;
	}
	
}
