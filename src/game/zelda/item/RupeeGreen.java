package game.zelda.item;

import engine.Game;
import game.zelda.player.Link;


public class RupeeGreen extends AbstractRupee {

	public RupeeGreen(Link link) {
		super(link, 1, 418);
		sound = Game.sounds.get("link_get_rupee");
	}

}
