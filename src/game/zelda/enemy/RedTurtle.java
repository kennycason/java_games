package game.zelda.enemy;

import engine.ai.AggressiveAIStrategy;
import engine.entity.enemy.AbstractEnemy;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteBank;
import engine.sprite.SpriteSheet;
import game.zelda.item.RupeeBlue;

public class RedTurtle extends AbstractEnemy {

	public RedTurtle(int x, int y) {
		setAIStrategy(new AggressiveAIStrategy(this, 20, 0, 2, 500));
		SpriteBank rsrcs = SpriteBank.getInstance();
		SpriteSheet sheet = (SpriteSheet) rsrcs.get("entities");
		
		SpriteSheet frames = new SpriteSheet(2, 16, 16);
		frames.set(0, sheet.get(679));
		frames.set(1, sheet.get(629));
		spriteCurrent = new AnimatedSprite(frames, 230);
		spriteCurrent.reverseCycle(true);
		spriteE = spriteW = spriteN = spriteS = spriteCurrent;
		locate(x * game.map().tileWidth(), y * game.map().tileHeight());
		damage = 0.5;
		life = 2;
		maxLife = 2;
		dropItemProbability = 60;
		dropItems.add(new RupeeBlue());
	}

	@Override
	public void handle() {
		super.handle();
		strategy.handle();
	
	}

}
