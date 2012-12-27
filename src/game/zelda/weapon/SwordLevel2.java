package game.zelda.weapon;

import engine.Game;


public class SwordLevel2 extends AbstractSword {

	public SwordLevel2(Game game) {
		super(game, 104, 2);
	}
	
	@Override
	public String menuDisplayName() {
		return "L-2";
	}
	
}
