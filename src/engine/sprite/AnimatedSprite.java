package engine.sprite;

import java.awt.Color;
import java.awt.Graphics;

import engine.Game;

public class AnimatedSprite extends AbstractSprite {

	private SpriteSheet sprites;

	private int numFrames = 1;

	private int animationSpeed = 200;

	private int animationIncrementor = 1;
	
	private boolean reverseCycle = false;

	private int currentFrame;

	private long lastAnimation;
	
	private boolean oneCycle = false;
	
	private boolean doneCycling;
	
	public AnimatedSprite(String file, int width, int height, int animationSpeed) {
		sprites = new SpriteSheet(file, width, height);
		numFrames = sprites.numTiles();
		this.animationSpeed = animationSpeed;
		spriteWidth = width;
		spriteHeight = height;
		reset();
	}
	
	public AnimatedSprite(SpriteSheet spriteSheet, int animationSpeed) {
		sprites = spriteSheet;
		numFrames = sprites.numTiles();
		this.animationSpeed = animationSpeed;
		spriteWidth = sprites.tileWidth();
		spriteHeight = sprites.tileHeight();
		reset();
	}
	
	public AnimatedSprite(AnimatedSprite sprite) {
		sprites = new SpriteSheet(sprite.numFrames, sprite.width(), sprite.height());
		numFrames = sprite.numFrames();
		animationSpeed = sprite.animationSpeed();
		spriteWidth = sprite.width();
		spriteHeight = sprite.height();
		reset();
	}

	public int numFrames() {
		return numFrames;
	}
	
	public SpriteSheet sprites() {
		return sprites;
	}
	
	public void sprites(SpriteSheet sprites) {
		this.sprites = sprites;
	}

	public int animationSpeed() {
		return animationSpeed;
	}
	
	public SimpleSprite currentSprite() {
		return sprites.get(currentFrame);
	}

	public void draw(Graphics g, int x, int y) {
		locate(x, y); // for collision detection
		if(doneCycling) {
			return;
		}
		sprites.get(currentFrame).draw(g, x, y);
		if (numFrames > 0 && animationSpeed > 0) {
			if (Game.clock.elapsedMillis() - lastAnimation > animationSpeed) {
				lastAnimation = Game.clock.elapsedMillis();
				currentFrame += animationIncrementor;
				if(!reverseCycle) {
					if (currentFrame >= numFrames) {
						currentFrame = 0;
						if(oneCycle) {
							doneCycling = true;
						}
					}
				} else {
					if (currentFrame >= numFrames - 1) {
						animationIncrementor = -1;
						currentFrame = numFrames - 1;
						if(oneCycle) {
							doneCycling = true;
						}
					} else if(currentFrame <= 0) {
						animationIncrementor = 1;
						currentFrame = 0;
					}
				}
				
			}
		}
	}
	
	public void oneCycle(boolean oneCycle) {
		this.oneCycle = oneCycle;
	}
	
	public void reset() {
		currentFrame = 0;
		lastAnimation = Game.clock.elapsedMillis();
		doneCycling = false;
	}

	public void reverseCycle(boolean reverseCycle) {
		this.reverseCycle = reverseCycle;
		if(!reverseCycle) {
			animationIncrementor = 1;
		}
	}

	public void addTransparency(Color color) {
		addTransparency(color.getRGB());
	}

	public void addTransparency(final int rgb) {
		for(int i = 0; i < sprites.numTiles(); i++) {
			sprites.get(i).addTransparency(rgb);
		}
	}

	public boolean doneAnimating() {
		return doneCycling;
	}

}
