package game.zelda.item;

import engine.Game;
import game.zelda.player.Link;


public class RupeeRed extends AbstractRupee {

	public RupeeRed(Link link) {
		super(link, 20, 420);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
