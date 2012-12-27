package game.zelda.weapon;

import engine.Game;


public class SwordLevel3 extends AbstractSword {

	public SwordLevel3(Game game) {
		super(game, 55, 3);
	}

	@Override
	public String menuDisplayName() {
		return "L-3";
	}
	
}
