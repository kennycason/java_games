package game.zelda.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import engine.FaceDirection;
import engine.GameStateEnum;
import engine.entity.AbstractLivingEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.weapon.AbstractUsableEntity;
import engine.entity.weapon.UsableBank;
import engine.keyboard.KeyBoard;
import engine.math.Vector2D;
import engine.sound.AbstractSound;
import engine.sound.SoundBank;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;
import engine.sprite.SpriteUtils;

public class Link extends AbstractLivingEntity {

	private Vector2D mapPosition; // position in the map grid

	protected Vector2D acceleration = new Vector2D(0, 0);

	private int accelerationRate;
	
	private int accelerationRateAngled;

	private int rupees = 208;

	private AbstractUsableEntity itemA;

	private AbstractUsableEntity itemB;
	
	private AbstractUsableEntity[] items;

	private AbstractSound lowHeartsSound;
	
	private AnimatedSprite attackN;
	private AnimatedSprite attackE;
	private AnimatedSprite attackS;
	private AnimatedSprite attackW;

	public Link() {
		super();

		SpriteSheet sheet = (SpriteSheet) SpriteBank.getInstance().get("entities");

		spriteE = new AnimatedSprite(sheet.range(2, 3), 200);
		spriteW = SpriteUtils.flipHorizontal(spriteE);
		spriteN = new AnimatedSprite(sheet.range(4, 5), 200);
		spriteS = new AnimatedSprite(sheet.range(0, 1), 200);
		
		attackE = new AnimatedSprite(sheet.range(6, 6), 0);
		attackW = SpriteUtils.flipHorizontal(spriteE);
		attackN = new AnimatedSprite(sheet.range(51, 51), 0);
		attackS = new AnimatedSprite(sheet.range(50, 50), 0);
		
		items = new AbstractUsableEntity[16];
		items[0] = UsableBank.getInstance().get("megaton");
		items[1] = UsableBank.getInstance().get("sword3");
		items[2] = UsableBank.getInstance().get("boomerang");
		items[3] = UsableBank.getInstance().get("ocarina");
		items[4] = UsableBank.getInstance().get("sword2");
		
		itemA = UsableBank.getInstance().get("sword1");
		itemB = UsableBank.getInstance().get("bow");
		
		locate(6 * game.map().tileWidth(), 12 * game.map().tileHeight());

		mapPosition = new Vector2D();
		acceleration = new Vector2D();
		accelerationRate = 4; // for horizontal & vertical directions
		accelerationRateAngled = 3; // for moving diagonally ~4.2 pixels
		
		face = FaceDirection.EAST;
		spriteCurrent = spriteE;
		invincibleTime = 500;
		life = 5.5;
		maxLife = 11;
		collisionOffset(5);
		deadSound = SoundBank.getInstance().get("link_die");
		hitSound = SoundBank.getInstance().get("link_hurt");
		lowHeartsSound = SoundBank.getInstance().get("link_low_life");
	}

	@Override
	public void handle() {
		if (dead()) {
			game.gameState(GameStateEnum.DEAD);
			lowHeartsSound.stop();
		}
		if(itemA() != null) {
			itemA().handle();
		}
		if(itemB() != null) {
			itemB().handle();
		}

		// handle invincibility (after getting hit)
		if (invincible) {
			if (System.currentTimeMillis() - lastTimeHit > invincibleTime) {
				invincible = false;
				flickerCount = 0;
			}
		}

		// handle enemy collisions
		Iterator<AbstractEnemy> iter = game.map().enemies().iterator();
		while (iter.hasNext()) {
			AbstractEnemy entity = iter.next();
			if (rectangleCollide(entity)) {
				hit(entity.damage());
				if (life() <= 3 && maxLife() > 6) {
					if (!lowHeartsSound.playing()) {
						lowHeartsSound.play();
					}
				}
			}
		}

	}

	@Override
	public void draw(Graphics2D g) {
		if(itemA() != null) {
			itemA().draw(g);
		}
		if(itemB() != null) {
			itemB().draw(g);
		}
		super.draw(g);
	}

	public void keyBoard(KeyBoard kb) {
		acceleration.set(0, 0);
		boolean move = false;

		int offX = 0;
		int offY = 0;
		if (kb.isKeyPressed(KeyEvent.VK_LEFT)
				|| kb.isKeyPressed(KeyEvent.VK_RIGHT)
				|| kb.isKeyPressed(KeyEvent.VK_UP)
				|| kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			offX = offsetX();
			offY = offsetY();

		}
		
		//@TODO do not check offx + m, offy + n such that it is out outside the map
		// handle angles first
		if (kb.isKeyPressed(KeyEvent.VK_RIGHT)
				&& kb.isKeyPressed(KeyEvent.VK_UP)) {
			if(!game.map().collide(this, offX + 1, offY, accelerationRateAngled, -accelerationRateAngled) &&
					!game.map().collide(this, offX + 1, offY - 1, accelerationRateAngled, -accelerationRateAngled) &&
					!game.map().collide(this, offX , offY - 1, accelerationRateAngled, -accelerationRateAngled)
					) {
				acceleration.add(accelerationRateAngled, -accelerationRateAngled); // normalize later
				move = true;
			}
			if(kb.keyPressedTime(KeyEvent.VK_RIGHT) < kb.keyPressedTime(KeyEvent.VK_UP)) {
				face(FaceDirection.EAST);
			} else {
				face(FaceDirection.NORTH);
			}
			face = FaceDirection.NORTH_EAST;
		} else if (kb.isKeyPressed(KeyEvent.VK_RIGHT)
				&& kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(!game.map().collide(this, offX + 1, offY, accelerationRateAngled, accelerationRateAngled) &&
					!game.map().collide(this, offX + 1, offY + 1, accelerationRateAngled, accelerationRateAngled) &&
					!game.map().collide(this, offX , offY + 1, accelerationRateAngled, accelerationRateAngled)
					) {
				acceleration.add(accelerationRateAngled, accelerationRateAngled); // normalize later
				move = true;
			}
			if(kb.keyPressedTime(KeyEvent.VK_RIGHT) < kb.keyPressedTime(KeyEvent.VK_DOWN)) {
				face(FaceDirection.EAST);
			} else {
				face(FaceDirection.SOUTH);
			}
			face = FaceDirection.SOUTH_EAST;
		} else if (kb.isKeyPressed(KeyEvent.VK_LEFT)
				&& kb.isKeyPressed(KeyEvent.VK_UP)) {
			if(!game.map().collide(this, offX - 1, offY, -accelerationRateAngled, -accelerationRateAngled) &&
					!game.map().collide(this, offX - 1, offY - 1, -accelerationRateAngled, -accelerationRateAngled) &&
					!game.map().collide(this, offX , offY - 1, -accelerationRateAngled, -accelerationRateAngled)
					) {
				acceleration.add(-accelerationRateAngled, -accelerationRateAngled); // normalize later
				move = true;
			}
			if(kb.keyPressedTime(KeyEvent.VK_LEFT) < kb.keyPressedTime(KeyEvent.VK_UP)) {
				face(FaceDirection.WEST);
			} else {
				face(FaceDirection.NORTH);
			}
			face = FaceDirection.NORTH_WEST;
		} else if (kb.isKeyPressed(KeyEvent.VK_LEFT)
				&& kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(!game.map().collide(this, offX - 1, offY, -accelerationRateAngled, accelerationRateAngled) &&
					!game.map().collide(this, offX - 1, offY + 1, -accelerationRateAngled, accelerationRateAngled) &&
					!game.map().collide(this, offX , offY + 1, -accelerationRateAngled, accelerationRateAngled)
					) {
				acceleration.add(-accelerationRateAngled, accelerationRateAngled); // normalize later
				move = true;
			}
			if(kb.keyPressedTime(KeyEvent.VK_LEFT) < kb.keyPressedTime(KeyEvent.VK_DOWN)) {
				face(FaceDirection.WEST);
			} else {
				face(FaceDirection.SOUTH);
			}
			face = FaceDirection.SOUTH_WEST;
		} else if (kb.isKeyPressed(KeyEvent.VK_RIGHT)) {
			if(!game.map().collide(this, offX + 1, offY - 1, accelerationRate, 0) &&
					!game.map().collide(this, offX + 1, offY, accelerationRate, 0) &&
					!game.map().collide(this, offX + 1, offY + 1, accelerationRate, 0)
					) {
				acceleration.add(accelerationRate, 0);
				move = true;
			}
			face(FaceDirection.EAST);
		} else if (kb.isKeyPressed(KeyEvent.VK_LEFT)) {
			if(!game.map().collide(this, offX - 1, offY - 1, -accelerationRate, 0) &&
					!game.map().collide(this, offX - 1, offY, -accelerationRate, 0) &&
					!game.map().collide(this, offX - 1, offY + 1, -accelerationRate, 0)
					) {
				acceleration.add(-accelerationRate, 0);
				move = true;
			}
			face(FaceDirection.WEST);
		} else if (kb.isKeyPressed(KeyEvent.VK_UP)) {
			if(!game.map().collide(this, offX - 1, offY - 1, 0, -accelerationRate) &&
					!game.map().collide(this, offX, offY - 1, 0, -accelerationRate) &&
					!game.map().collide(this, offX + 1, offY - 1, 0, -accelerationRate)
					) {
				acceleration.add(0, -accelerationRate);
				move = true;
			}
			face(FaceDirection.NORTH);
		} else if (kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(!game.map().collide(this, offX - 1, offY + 1, 0, accelerationRate) &&
					!game.map().collide(this, offX, offY + 1, 0, accelerationRate) &&
					!game.map().collide(this, offX + 1, offY + 1, 0, accelerationRate)
					) { 
				acceleration.add(0, accelerationRate);
				move = true;
			}
			face(FaceDirection.SOUTH);
		}

		// update position on grid
		if (move) {
			game.map().offset().subtract(acceleration);
			mapPosition.set(offsetX(), offsetX());
			x += (int) acceleration.x();
			y += (int) acceleration.y();
		}

		if (kb.isKeyPressed(KeyEvent.VK_A)) {
			if (itemB() != null && !itemA().using() && !itemB().using()) {
				itemB().use();
			}
		}
		if (kb.isKeyPressed(KeyEvent.VK_S)) {
			if (itemA() != null && !itemA().using() && !itemB().using()) {
				itemA().use();
			}
		}
		if (kb.isKeyPressed(KeyEvent.VK_SPACE)) {
			if(System.currentTimeMillis() - game.gameLoops().get(GameStateEnum.MAIN).transitionTime() >= 1000) {
				game.gameLoops().get(GameStateEnum.PAUSED).reset();
				game.gameState(GameStateEnum.PAUSED);
			}
		}
		if (kb.isKeyPressed(KeyEvent.VK_R)) {
			game.gameState(GameStateEnum.TITLE_SCREEN);
		}
		if (kb.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			game.gameState(GameStateEnum.END);
		}
	}
	
	public void life(double life) {
		super.life(life);
		if(life() > 3){ 
			lowHeartsSound.stop(); 
		}
	}

	public AbstractUsableEntity itemA() {
		return itemA;
	}

	public void itemA(AbstractUsableEntity itemA) {
		this.itemA = itemA;
	}
	
	public AbstractUsableEntity itemB() {
		return itemB;
	}

	public void itemB(AbstractUsableEntity itemB) {
		this.itemB = itemB;
	}
	
	public int rupees() {
		return rupees;
	}

	public void rupees(int rupees) {
		this.rupees = rupees;
	}

	@Override
	public int renderX() {
		return game.map().tileWidth() * game.map().entityOffset();
	}

	@Override
	public int renderY() {
		return game.map().tileHeight() * game.map().entityOffset();
	}
	
	public void attackFace(FaceDirection face) {
		this.face = face;
		switch(face) {
			case NORTH:
				spriteCurrent = attackN;
				break;
			case EAST:
				spriteCurrent = attackE;
				break;
			case SOUTH:
				spriteCurrent = attackS;
				break;
			case WEST:
				spriteCurrent = attackW;
				break;
		}
	}
	
	public AbstractUsableEntity[] items() {
		return items;
	}

}
