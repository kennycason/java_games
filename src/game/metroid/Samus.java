package game.metroid;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.FaceDirection;
import engine.Game;
import engine.entity.AbstractLivingEntity;
import engine.keyboard.KeyBoard;
import engine.math.Vector2D;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;
import engine.sprite.SpriteUtils;


public class Samus extends AbstractLivingEntity {
	
	private Vector2D mapPosition; // position in the map grid
	
	private Vector2D acceleration;
	
	private double accelerationRate;
	
	private AnimatedSprite samusCurrent;
	private AnimatedSprite samusN;
	private AnimatedSprite samusNE;
	private AnimatedSprite samusE;
	private AnimatedSprite samusSE;
	private AnimatedSprite samusS;
	private AnimatedSprite samusSW;
	private AnimatedSprite samusW;
	private AnimatedSprite samusNW;
	
	private SpriteSheet beamCurrent;
	private SpriteSheet beamsN;
	private SpriteSheet beamsNE;
	private SpriteSheet beamsE;
	private SpriteSheet beamsSE;
	private SpriteSheet beamsS;
	private SpriteSheet beamsSW;
	private SpriteSheet beamsW;
	private SpriteSheet beamsNW;
	
	private int currentBeam;
	
	private long beamSwitchTime;
	
	private Vector2D weaponOffset;
	
	public Samus(Game game) {
		super(game);

		samusE = new AnimatedSprite("sprites/entity/samus_varia_east.png", 19, 25, 10);
		samusW = SpriteUtils.rotate(samusE, Math.PI);
		samusN = SpriteUtils.rotate(samusE, -Math.PI / 2.0);
		samusS = SpriteUtils.rotate(samusE, Math.PI / 2.0);
		samusNE = SpriteUtils.rotate(samusE, -Math.PI / 4.0);
		samusSE = SpriteUtils.rotate(samusE, Math.PI / 4.0);
		samusSW = SpriteUtils.rotate(samusE, 3 * Math.PI / 4.0);
		samusNW = SpriteUtils.rotate(samusE, 5 * Math.PI / 4.0);
		
		beamsE = new SpriteSheet("sprites/entity/beams_east.png", 14, 11);
		beamsW = SpriteUtils.flipHorizontal(beamsE);
		beamsN = SpriteUtils.rotate(beamsE, -Math.PI / 2.0);
		beamsS = SpriteUtils.flipVertical(beamsN);
		beamsNE = SpriteUtils.rotate(beamsE, -Math.PI / 4.0);
		beamsSE = SpriteUtils.rotate(beamsE, Math.PI / 4.0);
		beamsSW = SpriteUtils.rotate(beamsE, 3 * Math.PI / 4.0);
		beamsNW = SpriteUtils.rotate(beamsE, 5 * Math.PI / 4.0);
		
		locate(6 * game.map().tileWidth(), 12 * game.map().tileHeight());
		
		mapPosition = new Vector2D(10, 10);
		weaponOffset = new Vector2D(10, 12);
		acceleration = new Vector2D(0, 0);
		accelerationRate = 4;
		face = FaceDirection.EAST;
		samusCurrent = samusE;
		beamCurrent = beamsE;
		currentBeam = 0;
		beamSwitchTime = System.currentTimeMillis();
	}
	
	public void draw(Graphics2D g) {
		beamCurrent.get(currentBeam).draw(g, x() + (int) weaponOffset.x(), y() +  (int) weaponOffset.y());
		samusCurrent.draw(g, renderX(), renderY());
	}
	
	public int offsetX() {
		return x() / 16;
	}

	public int offsetY() {
		return y() / 16;
	}

	public void keyBoard(KeyBoard kb) {
		acceleration.set(0, 0);
		boolean move = false;
		
		int offX = 0;
		int offY = 0;
		if (kb.isKeyPressed(KeyEvent.VK_LEFT) ||
				kb.isKeyPressed(KeyEvent.VK_RIGHT) ||
				kb.isKeyPressed(KeyEvent.VK_UP) ||
				kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			offX = offsetX();
			offY = offsetY();
		}
		
		if (kb.isKeyPressed(KeyEvent.VK_RIGHT)) {
			if(game.map().offset().x() % 16 != 0) {
				offX--;
			}
			if (game.map().metaLayer(offX + 1, offY) != 545) {
				acceleration.add(accelerationRate, 0);
				move = true;
			}
		}
		if (kb.isKeyPressed(KeyEvent.VK_LEFT)) {
			if(game.map().offset().x() % 16 != 0) {
				offX++;
			}
			if (game.map().metaLayer(offX - 1, offY) != 545) {
				acceleration.subtract(accelerationRate, 0);
				move = true;
			}
		}
		if(kb.isKeyPressed(KeyEvent.VK_UP)) {
			if(game.map().offset().y() % 16 != 0) {
				offY++;
			}
			if(game.map().metaLayer(offX, offY - 1) != 545) {
				acceleration.subtract(0, accelerationRate);
				move = true;
			}
		}
		if(kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(game.map().offset().y() % 16 != 0) {
				offY--;
			}
			if(game.map().metaLayer(offX, offY + 1) != 545) {
				acceleration.add(0, accelerationRate);
				move = true;
			}
		}

		// update position on grid
		if(move) {

			offX = (int) (-(game.map().offset().x() + -acceleration.x()) / 16 + 10);
			offY = (int) (-(game.map().offset().y() + -acceleration.y()) / 16 + 10);
			game.map().offset().subtract(acceleration);
			mapPosition.set(offX, offY);
		}
		
		// facing
		if (kb.isKeyPressed(KeyEvent.VK_UP) && kb.isKeyPressed(KeyEvent.VK_RIGHT)) {
			weaponOffset.set(12, 4);
			face = FaceDirection.NORTH_EAST;
			samusCurrent = samusNE;	
			beamCurrent = beamsNE;
		} else if (kb.isKeyPressed(KeyEvent.VK_DOWN) && kb.isKeyPressed(KeyEvent.VK_RIGHT)) {
			weaponOffset.set(4, 17);
			face = FaceDirection.SOUTH_EAST;
			samusCurrent = samusSE;	
			beamCurrent = beamsSE;
		} else if (kb.isKeyPressed(KeyEvent.VK_UP) && kb.isKeyPressed(KeyEvent.VK_LEFT)) {
			weaponOffset.set(0, -2);
			face = FaceDirection.NORTH_WEST;
			samusCurrent = samusNW;	
			beamCurrent = beamsNW;
		} else if (kb.isKeyPressed(KeyEvent.VK_DOWN) && kb.isKeyPressed(KeyEvent.VK_LEFT)) {
			weaponOffset.set(-7, 9);
			face = FaceDirection.SOUTH_WEST;
			samusCurrent = samusSW;	
			beamCurrent = beamsSW;
		} else if (kb.isKeyPressed(KeyEvent.VK_RIGHT)) {
			weaponOffset.set(10, 12);
			face = FaceDirection.EAST;
			samusCurrent = samusE;
			beamCurrent = beamsE;
		} else if (kb.isKeyPressed(KeyEvent.VK_LEFT)) {
			weaponOffset.set(-7, 3);
			face = FaceDirection.WEST;
			samusCurrent = samusW;
			beamCurrent = beamsW;
		} else if(kb.isKeyPressed(KeyEvent.VK_UP)) {
			weaponOffset.set(7, -1);
			face = FaceDirection.NORTH;
			samusCurrent = samusN;
			beamCurrent = beamsN;
		} else if(kb.isKeyPressed(KeyEvent.VK_DOWN)) {
			weaponOffset.set(-1, 13);
			face = FaceDirection.SOUTH;
			samusCurrent = samusS;
			beamCurrent = beamsS;
		}
		// beam select
		if(kb.isKeyPressed(KeyEvent.VK_S)) {
			if(System.currentTimeMillis() - beamSwitchTime > 500) {
				beamSwitchTime = System.currentTimeMillis();
				currentBeam++;
				if(currentBeam >= beamCurrent.numTiles()) {
					currentBeam = 0;
				}
			}
		}
	}

	@Override
	public int width() {
		return samusCurrent.width();
	}

	@Override
	public int height() {
		return samusCurrent.height();
	}

	@Override
	public void handle() {
	}

	@Override
	public void face(FaceDirection face) {
		this.face = face;
	}
	
}
