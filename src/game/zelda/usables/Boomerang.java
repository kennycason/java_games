package game.zelda.usables;

import java.awt.Graphics2D;
import java.util.Iterator;

import game.zelda.player.Link;
import engine.FaceDirection;
import engine.Game;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.entity.usable.AbstractWeapon;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.graphics.sprite.SpriteUtils;
import engine.math.PositionVector;
import engine.sound.LoopingSound;
import game.zelda.Buttons;

public class Boomerang extends AbstractWeapon {
	
	private AnimatedSprite sprite;

	private PositionVector move = new PositionVector();
	
	private int speed;
	
	private int speedAngled;
	
	private double distance;
	
	private PositionVector start;
	
	private boolean returning = false;
	
	private LoopingSound sound;
	
	private AbstractItem pickedUpItem;
	
	public Boomerang(Link link) {
		super(link);
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		SpriteSheet sheet = new SpriteSheet(8, 16, 16);
		sheet.set(0, entities.get(411));
		sheet.set(1, entities.get(412));
		sheet.set(2, entities.get(413));
		sheet.set(3, SpriteUtils.rotate(sheet.get(1), -Math.PI / 2));
		sheet.set(4, SpriteUtils.rotate(sheet.get(2), -Math.PI / 2));
		sheet.set(5, SpriteUtils.rotate(sheet.get(1), -Math.PI ));
		sheet.set(6, SpriteUtils.rotate(sheet.get(2), -Math.PI ));
		sheet.set(7, SpriteUtils.rotate(sheet.get(1), Math.PI / 2));
		sprite = new AnimatedSprite(sheet, 70);
		
		using = false;
		move = new PositionVector();
		start = new PositionVector();
		speed = 8;
		speedAngled = 6;
		distance = 16 * 5;
		damage = 1;
		collisionOffset = 4;
		sound = (LoopingSound) Game.sounds.get("boomerang");
	}
	
	public void draw(Graphics2D g) {
		if(!using) {
			return;
		}
		sprite.draw(g, x() + game.map().offset().x(), y() + game.map().offset().y());
	}
	
	public void use() {
		if(using) {
			return;
		}
		pickedUpItem = null;
		sound.play();
		using = true;
		returning = false;
		
		x = user.x();
		y = user.y();
		start.x(x);
		start.y(y);
		if (Game.keyboard.isKeyPressed(Buttons.RIGHT)
				&& Game.keyboard.isKeyPressed(Buttons.UP)) {
			move.set(speedAngled, -speedAngled);
		} else if (Game.keyboard.isKeyPressed(Buttons.RIGHT)
				&& Game.keyboard.isKeyPressed(Buttons.DOWN)) {
			move.set(speedAngled, speedAngled);
		} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)
				&& Game.keyboard.isKeyPressed(Buttons.DOWN)) {
			move.set(-speedAngled, speedAngled);
		} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)
				&& Game.keyboard.isKeyPressed(Buttons.UP)) {
			move.set(-speedAngled, -speedAngled);
		} else if (Game.keyboard.isKeyPressed(Buttons.UP)) {
			move.set(0, -speed);
		} else if (Game.keyboard.isKeyPressed(Buttons.RIGHT)) {
			move.set(speed, 0);
		} else if (Game.keyboard.isKeyPressed(Buttons.DOWN)) {
			move.set(0, speed);
		} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)) {
			move.set(-speed, 0);
		} else {
			switch(user.face()) {
				case NORTH:
					move.set(0, -speed);
					break;
				case EAST:
					move.set(speed, 0);
					break;
				case SOUTH:
					move.set(0, speed);
					break;
				case WEST:
					move.set(-speed, 0);
					break;
			}	
		}
	}
	
	public boolean using() {
		return using;
	}
	
	public void handle() {
		if(!using) {
			return;
		}
		// items
		if(pickedUpItem == null) {
			for(AbstractItem item : game.map().items()) {
				if(!item.mustTouch() && rectangleCollide(item)) {
					if(!item.isJustDropped()) {
						pickedUpItem = item;
						returning = true;
						break;
					}
				}
			}
		} else {
			pickedUpItem.locate(x() + sprite.width() / 2 - pickedUpItem.width() / 2, y() + sprite.height() / 2 - pickedUpItem.height() / 2);
		}
		// enemies
		Iterator<AbstractEnemy> iter = game.map().enemies().iterator();
		while (iter.hasNext()) {
			AbstractEnemy enemy = iter.next();
			if (rectangleCollide(enemy)) {
				enemy.hit(damage());
				returning = true;
			}
		}
		
		if(!returning) {
			x += move.x();
			y += move.y();
			
			double dist = Math.sqrt(Math.pow(x - start.x(), 2) + Math.pow(y - start.y(), 2));
			if(dist > distance) {
				returning = true;
			}
		} else {
			int mX = 0, mY = 0;
			if(user.x() - x() < -4) {
				mX = -speed;
			} else if(user.x() - x() > 4) {
				mX = speed;
			}
			if(user.y() - y() < -4) {
				mY = -speed;
			} else if(user.y() - y() > 4) {
				mY = speed;
			} 
			move.set(mX, mY);
			x += move.x();
			y += move.y();
			if(rectangleCollide(user)) {
				if(pickedUpItem != null) {
					pickedUpItem.consume();
				}
				using = false;
				sound.stop();
			}
		}
	}
	
	public void menuDraw(Graphics2D g, int x, int y) {
		sprite.sprites().get(0).draw(g, x, y);
	}
	
	public String menuDisplayName() {
		return "";
	}
	
	public void face(FaceDirection face) {
		
	}

	@Override
	public void reset() {
		using = false;
		sound.stop();
	}

	@Override
	public int width() {
		return sprite.width();
	}

	@Override
	public int height() {
		return sprite.height();
	}
	
}
