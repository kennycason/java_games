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
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SimpleSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.graphics.sprite.SpriteUtils;
import engine.math.PositionVector;
import engine.sound.LoopingSound;
import game.zelda.Buttons;

public class Link extends AbstractLivingEntity {

	private PositionVector mapPosition; // position in the map grid
	
	private PositionVector mapStartPosition;
	
	private PositionVector drawOffset;
	
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
		attackW = SpriteUtils.flipHorizontal(attackE);
		attackN = new AnimatedSprite(sheet.range(51, 51), 0);
		attackS = new AnimatedSprite(sheet.range(50, 50), 0);
		
		face = FaceDirection.EAST;
		spriteCurrent = spriteE;

		items = new AbstractUsableEntity[16];
		items[0] = Game.usables.get("megaton");
		items[1] = Game.usables.get("sword1");
		items[2] = Game.usables.get("boomerang");
		items[3] = Game.usables.get("ocarina");
		items[4] = Game.usables.get("sword2");
		items[5] = Game.usables.get("bracelet");
		
		itemA = Game.usables.get("bow");
		itemB = Game.usables.get("sword3");
		
		mapPosition = new PositionVector();
		drawOffset = new PositionVector(); 
		
		mapStartPosition = new PositionVector(10, 12);
		
		setLocation(mapStartPosition);
		
		mapPosition = new PositionVector();
		move = new PositionVector();
		moveRate = 4; // for horizontal & vertical directions
		moveRateDiag = 3; // for moving diagonally ~4.2 pixels

		invincibleTime = 500;
		life = 3;
		maxLife = 3;
		collisionOffset = 4;
		deadSound = Game.sounds.get("link_die");
		hitSound = Game.sounds.get("link_hurt");
		fallSound = Game.sounds.get("link_fall");
		lowHeartsSound = (LoopingSound) Game.sounds.get("link_low_life");
		
	}

	private void setLocation(PositionVector position) {
		mapPosition.set(position.x(), position.y());
		int realX = -(position.x() - 8) * game.map().tileWidth();
		int realY = -(position.y() - 8) *  game.map().tileHeight();
		game.map().offset().set(realX, realY);
		locate((position.x()) * width(), (position.y()) * height());
		drawOffset.set(8 * width(), 8 * height());
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
			setLocation(mapStartPosition);
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
			if (Game.clock.elapsedMillis() - lastTimeHit > invincibleTime) {
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

		keyBoard();
	}

	@Override
	public void draw(Graphics2D g) {
		if (itemA() != null) {
			itemA().draw(g);
		}
		if (itemB() != null) {
			itemB().draw(g);
		}
		if(!invincible) {
			spriteCurrent.draw(g, drawOffset.x(), drawOffset.y());
		} else {
			if(flicker) {
				// @TODO find better way to do this without creating a new sprite each time
				SimpleSprite neg = SpriteUtils.negative(spriteCurrent.currentSprite());
				neg.draw(g, drawOffset.x(), drawOffset.y());
				neg = null;	
				flicker = false;
				flickerCount++;
			} else {
				spriteCurrent.draw(g, drawOffset.x(), drawOffset.y());
				if(flickerCount < maxFlickerCount) {
					flicker = true;
				}
			}	
		}
	}
	
	public void keyBoard() {
		move.set(0, 0);
		// handle angles first
		if(canMove) {
			if (Game.keyboard.isKeyPressed(Buttons.RIGHT) && Game.keyboard.isKeyPressed(Buttons.UP)) {
				move.set(moveRateDiag,-moveRateDiag); 
				if (Game.keyboard.keyPressedTime(Buttons.RIGHT) < Game.keyboard.keyPressedTime(Buttons.UP)) {
					face(FaceDirection.EAST);
				} else {
					face(FaceDirection.NORTH);
				}
			} else if (Game.keyboard.isKeyPressed(Buttons.RIGHT) && Game.keyboard.isKeyPressed(Buttons.DOWN)) {
				move.set(moveRateDiag, moveRateDiag); 
				if (Game.keyboard.keyPressedTime(Buttons.RIGHT) < Game.keyboard.keyPressedTime(Buttons.DOWN)) {
					face(FaceDirection.EAST);
				} else {
					face(FaceDirection.SOUTH);
				}
			} else if (Game.keyboard.isKeyPressed(Buttons.LEFT) && Game.keyboard.isKeyPressed(Buttons.UP)) {
				move.set(-moveRateDiag, -moveRateDiag); 
				if (Game.keyboard.keyPressedTime(Buttons.LEFT) < Game.keyboard.keyPressedTime(Buttons.UP)) {
					face(FaceDirection.WEST);
				} else {
					face(FaceDirection.NORTH);
				}
			} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)&& Game.keyboard.isKeyPressed(Buttons.DOWN)) {
				move.set(-moveRateDiag, moveRateDiag);
				if (Game.keyboard.keyPressedTime(Buttons.LEFT) < Game.keyboard.keyPressedTime(Buttons.DOWN)) {
					face(FaceDirection.WEST);
				} else {
					face(FaceDirection.SOUTH);
				}
			} else if (Game.keyboard.isKeyPressed(Buttons.RIGHT)) {
				move.set(moveRate, 0);
				face(FaceDirection.EAST);
			} else if (Game.keyboard.isKeyPressed(Buttons.LEFT)) {
				move.set(-moveRate, 0);
				face(FaceDirection.WEST);
			} else if (Game.keyboard.isKeyPressed(Buttons.UP)) {
				move.set(0, -moveRate);
				face(FaceDirection.NORTH);
			} else if (Game.keyboard.isKeyPressed(Buttons.DOWN)) {
				move.set(0, moveRate);
				face(FaceDirection.SOUTH);
			}
		}

		// update position on grid
		if (move.x() != 0 || move.y() != 0) {
			game.map().handleMetaEvents(this);
			move = game.map().move(this, move);
			
			updatePosition(move);
				
			mapPosition.set(mapX(), mapY());
		}

		if (Game.keyboard.isKeyPressed(Buttons.ITEM_B)) {
			if (itemB() != null && !itemA().using() && !itemB().using()) {
				itemB().use();
			}
		}
		if (Game.keyboard.isKeyPressed(Buttons.ITEM_A)) {
			boolean itemConsumed = false;
			if (face() == FaceDirection.NORTH) {
				for (AbstractItem item : game.map().items()) {
					if (!item.walkable()) {
						boolean wasConsumed = item.consumed();
						item.consume(); // actually should just attempt to consume
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
		if (Game.keyboard.isKeyPressed(Buttons.START)) {
			if (Game.clock.systemElapsedMillis()
					- game.gameLoops().get(GameStateEnum.MAIN).transitionTime() >= 1000) {
				game.gameLoops().get(GameStateEnum.ITEM_SCREEN).start();
				game.gameState(GameStateEnum.ITEM_SCREEN);
			}
		}
		if (Game.keyboard.isKeyPressed(Buttons.PAUSE)) {
			if (Game.clock.systemElapsedMillis()
					- game.gameLoops().get(GameStateEnum.MAIN).transitionTime() >= 1000) {
				game.gameLoops().get(GameStateEnum.PAUSED).start();
				game.gameState(GameStateEnum.PAUSED);
			}
		}
		if (Game.keyboard.isKeyPressed(Buttons.RESET)) {
			game.gameLoops().get(GameStateEnum.MAIN).end();
			game.gameState(GameStateEnum.TITLE_SCREEN);
		}
		if (Game.keyboard.isKeyPressed(Buttons.ESCAPE)) {
			game.gameState(GameStateEnum.END);
		}
	}
	
	private void updatePosition(PositionVector move) {
		// update position on grid
		// simplest
		if (move.x() != 0 || move.y() != 0) {
			game.map().handleMetaEvents(this);
			move = game.map().move(this, move);

			game.map().offset().x(game.map().offset().x() - move.x());
			x += move.x();
			game.map().offset().y(game.map().offset().y() - move.y());
			y += move.y();

			mapPosition.set(mapX(), mapY());
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
		if (rupees > maxRupees) {
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
	
	public PositionVector drawOffset() {
		return drawOffset;
	}

	public AbstractUsableEntity[] items() {
		return items;
	}

}
