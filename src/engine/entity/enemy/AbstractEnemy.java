package engine.entity.enemy;

import engine.Game;
import engine.entity.AbstractLivingEntity;
import engine.sound.SoundBank;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	public AbstractEnemy(Game game) {
		super(game);
		hitSound = SoundBank.getInstance().get("enemy_hit");
		deadSound = SoundBank.getInstance().get("enemy_die");
	}

}
