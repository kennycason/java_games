package game.zelda.enemy;

import engine.FaceDirection;
import engine.Game;
import engine.ai.RandomAIStrategy;
import engine.entity.enemy.AbstractEnemy;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;

public class LikeLike extends AbstractEnemy {

	public LikeLike(Game game, int x, int y) {
		super(game);
		setAIStrategy(new RandomAIStrategy(game, this, 2500));
		SpriteBank rsrcs = SpriteBank.getInstance();
		SpriteSheet sheet = (SpriteSheet) rsrcs.get("entities");
		sprite = new AnimatedSprite(sheet.getRange(462,464), 230);
		sprite.reverseCycle(true);
		spriteE = spriteW = spriteN = spriteS = sprite;
		locate(x * game.map().tileWidth(), y * game.map().tileHeight());
		damage = 0.5;
		life = 2;
		maxLife = 2;
	}

	@Override
	public void handle() {
		strategy.handle();
		
		if(invincible) {
			if(System.currentTimeMillis() - lastTimeHit > invincibleTime) {
				invincible = false;
				flickerCount = 0;
			}
		}
	}

	@Override
	public void face(FaceDirection face) {
		this.face = face;
	}

}
