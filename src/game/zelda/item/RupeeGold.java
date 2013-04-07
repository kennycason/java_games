package game.zelda.item;

import engine.Game;
import game.zelda.player.Link;


public class RupeeGold extends AbstractRupee {

	public RupeeGold(Link link) {
		super(link, 100, 422);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
