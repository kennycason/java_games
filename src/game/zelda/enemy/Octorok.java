package game.zelda.enemy;

import engine.FaceDirection;
import engine.Game;
import engine.ai.RandomAIStrategy;
import engine.entity.enemy.AbstractEnemy;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.graphics.sprite.SpriteUtils;
import game.zelda.item.Heart;
import game.zelda.item.RupeeGreen;

public class Octorok extends AbstractEnemy {

	public Octorok(int x, int y) {
		setAIStrategy(new RandomAIStrategy(this, 2500));
		SpriteSheet sheet = (SpriteSheet) Game.sprites.get("entities");
		SpriteSheet west = new SpriteSheet(2, 16, 16);
		SpriteSheet south = new SpriteSheet(2, 16, 16);
		
		west.set(0, sheet.get(213));
		west.set(1, sheet.get(263));
		
		south.set(0, sheet.get(214));
		south.set(1, sheet.get(313));
		
		spriteW = new AnimatedSprite(west, 230);
		spriteE = SpriteUtils.flipHorizontal(spriteW);
		spriteS = new AnimatedSprite(south, 230);
		spriteN = SpriteUtils.flipVertical(spriteS);
		
		face = FaceDirection.EAST;
		spriteCurrent = spriteE;
		
		locate(x * game.map().tileWidth(), y * game.map().tileHeight());
		damage = 0.5;
		life = 4;
		maxLife = 1;
		dropItems.add(new Heart());
		dropItems.add(new Heart());
		dropItems.add(new RupeeGreen());
		dropItems.add(new RupeeGreen());
		
		
	}

	@Override
	public void handle() {
		super.handle();
		strategy.handle();
		
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

}
