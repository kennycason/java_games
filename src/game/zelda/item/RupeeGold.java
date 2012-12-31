package game.zelda.item;

import engine.Game;


public class RupeeGold extends AbstractRupee {

	public RupeeGold() {
		super(100, 422);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
