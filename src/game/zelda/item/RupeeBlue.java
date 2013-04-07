package game.zelda.item;

import engine.Game;
import game.zelda.player.Link;


public class RupeeBlue extends AbstractRupee {

	public RupeeBlue(Link link) {
		super(link, 5, 419);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
