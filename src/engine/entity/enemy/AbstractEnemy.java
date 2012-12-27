package engine.entity.enemy;

import engine.Game;
import engine.entity.AbstractMultiFacingAnimatedEntity;
import engine.sound.Sound;

public abstract class AbstractEnemy extends AbstractMultiFacingAnimatedEntity {

	public AbstractEnemy(Game game) {
		super(game);
		hitSound = new Sound("sound/effects/Oracle_Enemy_Hit.wav");
		deadSound = new Sound("sound/effects/Oracle_Enemy_Die.wav");
	}

}
