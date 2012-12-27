package game.zelda.weapon;

import engine.Game;


public class SwordLevel1 extends AbstractSword {

	public SwordLevel1(Game game) {
		super(game, 54, 1);
	}

	@Override
	public String menuDisplayName() {
		return "L-1";
	}
	
}
