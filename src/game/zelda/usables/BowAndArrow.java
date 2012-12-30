package game.zelda.usables;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import engine.FaceDirection;
import engine.Game;
import engine.entity.AbstractSimpleEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.weapon.AbstractWeapon;
import engine.math.Vector2D;
import engine.sprite.AnimatedSprite;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;
import engine.sprite.SpriteUtils;

public class BowAndArrow extends AbstractWeapon {
	
	private SimpleSprite bow;
	private AnimatedSprite arrowN; 
	private AnimatedSprite arrowS;
	private AnimatedSprite arrowE;
	private AnimatedSprite arrowW;
	
	private int speed;
	
	private List<Arrow> arrows;
	
	private int numArrows;

	// private ISound sound;
	
	private boolean using;
	
	private long lastShotTime;
	
	public BowAndArrow() {
		super();
		SpriteSheet entities = (SpriteSheet) SpriteBank.getInstance().get("entities");
		
		bow = entities.get(163);
		arrowN = new AnimatedSprite(entities.range(362, 362), 0);
		arrowS = SpriteUtils.flipVertical(arrowN);
		arrowE = new AnimatedSprite(entities.range(363, 363), 0);
		arrowW = SpriteUtils.flipVertical(arrowE);
		
		using = false;
		speed = 8;
		numArrows = 30;
		damage = 2;
		collisionOffset = 6;
		//sound = SoundBank.getInstance().get("boomerang");
		arrows = new LinkedList<Arrow>();
	}
	
	public void draw(Graphics2D g) {
		for(Arrow arrow : arrows) {
			arrow.draw(g);
		}
	}
	
	public void use() {
		if(numArrows > 0 && System.currentTimeMillis() - lastShotTime > 750) {
			lastShotTime = System.currentTimeMillis();
			//sound.play();
	
			x = game.link().x();
			y = game.link().y();
			numArrows--;
			switch(game.link().face()) {
				case NORTH:
					arrows.add(new Arrow(arrowN, 0, -speed, x, y));
					break;
				case EAST:
					arrows.add(new Arrow(arrowE, speed, 0, x, y));
					break;
				case SOUTH:
					arrows.add(new Arrow(arrowS, 0, speed, x, y));
					break;
				case WEST:
					arrows.add(new Arrow(arrowW, -speed, 0, x, y));
					break;
			}
		}
	}
	
	public boolean using() {
		return using;
	}
	
	public void handle() {

		Iterator<Arrow> arrowIter = arrows.iterator();
		while(arrowIter.hasNext()) {
			Arrow arrow = arrowIter.next();
			
			arrow.handle();
			
			// enemies
			Iterator<AbstractEnemy> iter = game.map().enemies().iterator();
			while (iter.hasNext()) {
				AbstractEnemy enemy = iter.next();
				if (arrow.rectangleCollide(enemy)) {
					enemy.hit(damage());
					arrowIter.remove();
					break;
				}
			}	
			
			if(arrow.renderX() < -arrow.width() || arrow.renderX() > Game.SCREEN_WIDTH ||
					arrow.renderY() < -arrow.height() || arrow.renderY() > Game.SCREEN_HEIGHT) {
				arrowIter.remove();
			}
		}

	}
	
	public void menuDraw(Graphics2D g, int x, int y) {
		bow.draw(g, x - 2, y);
		arrowN.draw(g, x + 4, y);
	}
	
	@Override
	public String menuDisplayName() {
		return String.valueOf(numArrows);
	}
	
	public void face(FaceDirection face) {
		
	}

	private class Arrow extends AbstractSimpleEntity {
		
		private Vector2D acceleration = new Vector2D();
		
		private int speedX;
		
		private int speedY;
		
		public Arrow(AnimatedSprite sprite, int speedX, int speedY, int x, int y) {
			this.sprite = sprite; // make a copy later for case of animated arrows
			acceleration.set(speedX, speedY);
			locate(x, y);
			this.speedX = speedX;
			this.speedY = speedY;
			collisionOffset = 10;
		}
		
		public void handle() {
			x += speedX;
			y += speedY;
		}

		@Override
		public void draw(Graphics2D g) {
			sprite.draw(g, renderX(), renderY());
		}
		
	}

	@Override
	public int width() {
		return bow.width();
	}

	@Override
	public int height() {
		return bow.height();
	}
	
	public int numArrows() {
		return numArrows;
	}
	
	public void numArrows(int numArrows) {
		this.numArrows = numArrows;
	}
	
}
