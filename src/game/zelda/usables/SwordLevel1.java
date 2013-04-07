package game.zelda.usables;

import game.zelda.player.Link;

public class SwordLevel1 extends AbstractSword {

	public SwordLevel1(Link link) {
		super(link, 54, 1);
	}

	@Override
	public String menuDisplayName() {
		return "L-1";
	}
	
}
