package engine.entity.enemy;

import engine.Game;
import engine.entity.AbstractMultiFacingAnimatedEntity;
import engine.sound.SoundBank;

public abstract class AbstractEnemy extends AbstractMultiFacingAnimatedEntity {

	public AbstractEnemy(Game game) {
		super(game);
		hitSound = SoundBank.getInstance().get("enemy_hit");
		deadSound = SoundBank.getInstance().get("enemy_die");
	}

}
