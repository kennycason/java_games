package game.zelda.usables;

import game.zelda.player.Link;

public class SwordLevel3 extends AbstractSword {

	public SwordLevel3(Link link) {
		super(link, 55, 3);
	}

	@Override
	public String menuDisplayName() {
		return "L-3";
	}
	
}
