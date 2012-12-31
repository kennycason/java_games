package game.zelda.item;

import engine.Game;


public class RupeeBlue extends AbstractRupee {

	public RupeeBlue() {
		super(5, 419);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
