package game.zelda.usables;

import game.zelda.player.Link;

public class SwordLevel2 extends AbstractSword {

	public SwordLevel2(Link link) {
		super(link, 104, 2);
	}
	
	@Override
	public String menuDisplayName() {
		return "L-2";
	}
	
}
