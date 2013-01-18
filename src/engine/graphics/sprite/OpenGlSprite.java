package engine.graphics.sprite;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

import javax.media.opengl.GL2;

public class OpenGlSprite {
	
	private SimpleSprite s;
	
	public OpenGlSprite() {
		SpriteBank sprites = new SpriteBank();
		sprites.set("entities", new SpriteSheet("sprites/entity/zelda/entities.png", 16, 16));
		s = ((SpriteSheet)sprites.get("entities")).get(3);
	}

	public void draw(GL2 gl) {
		gl.glLoadIdentity();

		WritableRaster raster = 
				Raster.createInterleavedRaster (DataBuffer.TYPE_BYTE,
						s.width(),
						s.height(),
						4,
						null);
			ComponentColorModel colorModel=
				new ComponentColorModel (ColorSpace.getInstance(ColorSpace.CS_sRGB),
						new int[] {8,8,8,8},
						true,
						false,
						ComponentColorModel.TRANSLUCENT,
						DataBuffer.TYPE_BYTE);
			BufferedImage dukeImg = 
				new BufferedImage (colorModel,
						raster,
						false,
						null);
 
			Graphics2D g = dukeImg.createGraphics();
			g.drawImage(s.bufferedImage(), null, null);
			DataBufferByte dukeBuf =
				(DataBufferByte)raster.getDataBuffer();
			byte[] dukeRGBA = dukeBuf.getData();
			ByteBuffer bb = ByteBuffer.wrap(dukeRGBA);
			bb.position(0);
			bb.mark();
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 13);
		gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexImage2D (GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, s.width(), s.height(), 0, GL2.GL_RGBA, 
				GL2.GL_UNSIGNED_BYTE, bb);

		int left = 100;
		int top = 100;
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture (GL2.GL_TEXTURE_2D, 13);
		gl.glBegin (GL2.GL_POLYGON);
		gl.glTexCoord2d (0, 0);
		gl.glVertex2d (left,top);
		gl.glTexCoord2d(1,0);
		gl.glVertex2d (left + s.width(), top);
		gl.glTexCoord2d(1,1);
		gl.glVertex2d (left + s.width(), top + s.height());
		gl.glTexCoord2d(0,1);
		gl.glVertex2d (left, top + s.height());
		gl.glEnd ();	
	}
}
