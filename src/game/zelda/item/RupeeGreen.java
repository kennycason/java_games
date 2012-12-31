package game.zelda.item;

import engine.Game;


public class RupeeGreen extends AbstractRupee {

	public RupeeGreen() {
		super(1, 418);
		sound = Game.sounds.get("link_get_rupee");
	}

}
