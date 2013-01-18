package game.zelda.enemy;

import java.awt.Graphics2D;

import engine.FaceDirection;
import engine.Game;
import engine.ai.RandomAIStrategy;
import engine.entity.AbstractSimpleEntity;
import engine.entity.enemy.AbstractEnemy;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.graphics.sprite.SpriteUtils;
import game.zelda.item.Heart;
import game.zelda.item.RupeeGold;
import game.zelda.item.RupeeGreen;

public class BabyWorm extends AbstractEnemy {
	
	private Body tail1;
	
	private Body tail2;
	
	public BabyWorm(int x, int y) {
		setAIStrategy(new RandomAIStrategy(this, 2500));
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		
		spriteW = new AnimatedSprite(sheet.range(561, 561), 0);
		spriteE = SpriteUtils.flipHorizontal(spriteW);
		spriteS = SpriteUtils.rotate(spriteE, Math.PI / 2);
		spriteN = SpriteUtils.rotate(spriteW, Math.PI / 2);
		tail1 = new Body(sheet.range(562, 562));
		tail2 = new Body(sheet.range(563, 563));
		
		face = FaceDirection.EAST;
		spriteCurrent = spriteE;
		
		locate(x * game.map().tileWidth(), y * game.map().tileHeight());
		damage = 2.0;
		life = 10;
		maxLife = 10;
		dropItems.add(new Heart());
		dropItems.add(new Heart());
		dropItems.add(new RupeeGreen());
		dropItems.add(new RupeeGold());
	}

	@Override
	public void draw(Graphics2D g) {
		tail2.draw(g);
		tail1.draw(g);
		spriteCurrent.draw(g, x(), y());
		System.out.println("last face: " + lastFace() + " face: " + face());
	}
	
	@Override
	public void handle() {
		super.handle();
		strategy.handle();
		
	//	tail2.locate(tail1.x(), tail1.y());
		//tail1.locate(x(), y());

	}

	@Override
	public void face(FaceDirection face) {
		this.face = face;
		switch(face) {
			case NORTH:
				spriteCurrent = spriteN;
				break;
			case EAST:
				spriteCurrent = spriteE;
				break;
			case SOUTH:
				spriteCurrent = spriteS;
				break;
			case WEST:
				spriteCurrent = spriteW;
				break;
		}
	}

	private class Body extends AbstractSimpleEntity {

		public Body(SpriteSheet sprites) {
			this.sprite = new AnimatedSprite(sprites, 0);
		}
		
		@Override
		public void draw(Graphics2D g) {
			sprite.draw(g, x(), y());
		}
		
	}
	
}
