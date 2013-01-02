package game.zelda.player;

import java.awt.Graphics2D;
import java.util.Iterator;

import engine.FaceDirection;
import engine.Game;
import engine.GameStateEnum;
import engine.entity.AbstractLivingEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.item.AbstractItem;
import engine.entity.usable.AbstractUsableEntity;
import engine.keyboard.KeyBoard;
import engine.math.PositionVector;
import engine.sound.LoopingSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;
import engine.sprite.SpriteUtils;
import game.zelda.Buttons;

public class Link extends AbstractLivingEntity {

	private PositionVector mapPosition; // position in the map grid

	protected PositionVector move = new PositionVector(0, 0);
	
	private int moveRate;

	private int moveRateDiag;

	private int rupees = 0;

	private int maxRupees = 999;
	
	private int heartPieces = 0;
	
	/**
	 * keys will be stored on a dungeon basis in future
	 */
	private int smallKeys = 5;
	
	private boolean bossKey = true;

	private AbstractUsableEntity itemA;

	private AbstractUsableEntity itemB;

	private AbstractUsableEntity[] items;

	private LoopingSound lowHeartsSound;

	private AnimatedSprite attackN;
	private AnimatedSprite attackE;
	private AnimatedSprite attackS;
	private AnimatedSprite attackW;

	public Link() {
		super();

		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");

		spriteE = new AnimatedSprite(sheet.range(2, 3), 200);
		spriteW = SpriteUtils.flipHorizontal(spriteE);
		spriteN = new AnimatedSprite(sheet.range(4, 5), 200);
		spriteS = new AnimatedSprite(sheet.range(0, 1), 200);

		attackE = new AnimatedSprite(sheet.range(6, 6), 0);
		attackW = SpriteUtils.flipHorizontal(spriteE);
		attackN = new AnimatedSprite(sheet.range(51, 51), 0);
		attackS = new AnimatedSprite(sheet.range(50, 50), 0);

		items = new AbstractUsableEntity[16];
		items[0] = Game.usables.get("megaton");
		items[1] = Game.usables.get("sword1");
		items[2] = Game.usables.get("boomerang");
		items[3] = Game.usables.get("ocarina");
		items[4] = Game.usables.get("sword2");
		items[5] = Game.usables.get("bracelet");
		
		itemA = Game.usables.get("bow");
		itemB = Game.usables.get("sword3");

		//game.map().startX(6 * game.map().tileWidth());
		//game.map().startX(12 * game.map().tileHeight());
		// game.map().locate(AbstractLivingEntity entity)
		locate(6 * game.map().tileWidth(), 12 * game.map().tileHeight());
		game.map().offset().set(2 * game.map().tileWidth(), -4 *  game.map().tileHeight());

		mapPosition = new PositionVector();
		move = new PositionVector();
		moveRate = 4; // for horizontal & vertical directions
		moveRateDiag = 3; // for moving diagonally ~4.2 pixels

		face = FaceDirection.EAST;
		spriteCurrent = spriteE;
		invincibleTime = 500;
		life = 3;
		maxLife = 4;
		collisionOffset = 5;
		deadSound = Game.sounds.get("link_die");
		hitSound = Game.sounds.get("link_hurt");
		fallSound = Game.sounds.get("link_fall");
		lowHeartsSound = (LoopingSound) Game.sounds.get("link_low_life");
	}

	@Override
	public void handle() {
		if (dead) {
			lowHeartsSound.stop();
			deadSound.play();
			game.sleep(2000);
			game.gameState(GameStateEnum.DEAD);
			return;
		}
		
		if(falling()) {
			System.out.println("fall");
			// do some animations
			falling = false;
			canMove = true;
			fallSound.play();
			if (itemA() != null) {
				itemA().reset();
			}
			if (itemB() != null) {
				itemB().reset();
			}
			game.sleep(1500);
			game.map().offset().set(2 * game.map().tileWidth(), -4 *  game.map().tileHeight());
			locate(6 * game.map().tileWidth(), 12 * game.map().tileHeight());
			// locate(game.map().startX(), game.map().startY());
			hit(1);
		}
		
		if (itemA() != null) {
			itemA().handle();
		}
		if (itemB() != null) {
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
			}
		}

	}

	@Override
	public void draw(Graphics2D g) {
		if (itemA() != null) {
			itemA().draw(g);
		}
		if (itemB() != null) {
			itemB().draw(g);
		}
		super.draw(g);
	}
	
	public void keyBoard(KeyBoard kb) {
		move.set(0, 0);
		// handle angles first
		if(canMove) {
			if (kb.isKeyPressed(Buttons.RIGHT) && kb.isKeyPressed(Buttons.UP)) {
				move.set(moveRateDiag,-moveRateDiag); 
				if (kb.keyPressedTime(Buttons.RIGHT) < kb.keyPressedTime(Buttons.UP)) {
					face(FaceDirection.EAST);
				} else {
					face(FaceDirection.NORTH);
				}
			} else if (kb.isKeyPressed(Buttons.RIGHT) && kb.isKeyPressed(Buttons.DOWN)) {
				move.set(moveRateDiag, moveRateDiag); 
				if (kb.keyPressedTime(Buttons.RIGHT) < kb.keyPressedTime(Buttons.DOWN)) {
					face(FaceDirection.EAST);
				} else {
					face(FaceDirection.SOUTH);
				}
			} else if (kb.isKeyPressed(Buttons.LEFT) && kb.isKeyPressed(Buttons.UP)) {
				move.set(-moveRateDiag, -moveRateDiag); 
				if (kb.keyPressedTime(Buttons.LEFT) < kb.keyPressedTime(Buttons.UP)) {
					face(FaceDirection.WEST);
				} else {
					face(FaceDirection.NORTH);
				}
			} else if (kb.isKeyPressed(Buttons.LEFT)&& kb.isKeyPressed(Buttons.DOWN)) {
				move.set(-moveRateDiag, moveRateDiag);
				if (kb.keyPressedTime(Buttons.LEFT) < kb.keyPressedTime(Buttons.DOWN)) {
					face(FaceDirection.WEST);
				} else {
					face(FaceDirection.SOUTH);
				}
			} else if (kb.isKeyPressed(Buttons.RIGHT)) {
				move.set(moveRate, 0);
				face(FaceDirection.EAST);
			} else if (kb.isKeyPressed(Buttons.LEFT)) {
				move.set(-moveRate, 0);
				face(FaceDirection.WEST);
			} else if (kb.isKeyPressed(Buttons.UP)) {
				move.set(0, -moveRate);
				face(FaceDirection.NORTH);
			} else if (kb.isKeyPressed(Buttons.DOWN)) {
				move.set(0, moveRate);
				face(FaceDirection.SOUTH);
			}
		}

		// update position on grid
		if (move.x() != 0 || move.y() != 0) {
			game.map().handleMetaEvents(this);
			move = game.map().move(this, move);
			game.map().offset().subtract(move);
			mapPosition.set(mapX(), mapX());
			x += move.x();
			y += move.y();
		}

		if (kb.isKeyPressed(Buttons.ITEM_B)) {
			if (itemB() != null && !itemA().using() && !itemB().using()) {
				itemB().use();
			}
		}
		if (kb.isKeyPressed(Buttons.ITEM_A)) {
			boolean itemConsumed = false;
			if (face() == FaceDirection.NORTH) {
				for (AbstractItem item : game.map().items()) {
					if (!item.walkable()) {
						boolean wasConsumed = item.consumed();
						item.consume(); // actually should just attempt to
										// consume
						if (item.consumed() && !wasConsumed) {
							itemConsumed = true;
							game.sleep(300);
						}
					}
				}
			}
			if (!itemConsumed) {
				if (itemA() != null && !itemA().using() && !itemB().using()) {
					itemA().use();
				}
			}
		}
		if (kb.isKeyPressed(Buttons.START)) {
			if (System.currentTimeMillis()
					- game.gameLoops().get(GameStateEnum.MAIN).transitionTime() >= 1000) {
				game.gameLoops().get(GameStateEnum.ITEM_SCREEN).start();
				game.gameState(GameStateEnum.ITEM_SCREEN);
			}
		}
		if (kb.isKeyPressed(Buttons.PAUSE)) {
			if (System.currentTimeMillis()
					- game.gameLoops().get(GameStateEnum.MAIN).transitionTime() >= 1000) {
				game.gameLoops().get(GameStateEnum.PAUSED).start();
				game.gameState(GameStateEnum.PAUSED);
			}
		}
		if (kb.isKeyPressed(Buttons.RESET)) {
			game.gameLoops().get(GameStateEnum.MAIN).end();
			game.gameState(GameStateEnum.TITLE_SCREEN);
		}
		if (kb.isKeyPressed(Buttons.ESCAPE)) {
			game.gameState(GameStateEnum.END);
		}
	}
	
	@Override
	public void hit(double damage) {
		super.hit(damage);
		if (life() <= 3 && maxLife() > 6) {
			if (!lowHeartsSound.playing()) {
				lowHeartsSound.play();
			}
		}
	}

	@Override
	public void life(double life) {
		super.life(life);
		if (life() > 3) {
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
		if (rupees < maxRupees) {
			rupees = maxRupees;
		}
		this.rupees = rupees;
	}
	
	public int heartPieces() {
		return heartPieces;
	}

	public void heartPieces(int heartPieces) {
		if (heartPieces >= 4) {
			heartPieces = 0;
			maxLife++;
		}
		this.heartPieces = heartPieces;
	}

	public int maxRupees() {
		return maxRupees;
	}

	public void maxRupees(int maxRupees) {
		this.maxRupees = maxRupees;
	}
	
	public boolean canMove() {
		return canMove;
	}
	
	public void canMove(boolean canMove) {
		this.canMove = canMove;
	}
	
	public boolean bossKey() {
		return bossKey;
	}
	
	public void bossKey(boolean bossKey) {
		this.bossKey = bossKey;
	}
	
	public int smallKeys() {
		return smallKeys;
	}
	
	public void smallKeys(int smallKeys) {
		this.smallKeys = smallKeys;
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
		switch (face) {
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
