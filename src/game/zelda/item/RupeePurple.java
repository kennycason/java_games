package game.zelda.item;

import engine.Game;

public class RupeePurple extends AbstractRupee {

	public RupeePurple() {
		super(50, 421);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
