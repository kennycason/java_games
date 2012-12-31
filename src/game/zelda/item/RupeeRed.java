package game.zelda.item;

import engine.Game;


public class RupeeRed extends AbstractRupee {

	public RupeeRed() {
		super(20, 420);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
