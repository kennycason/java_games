package game.zelda.item;

import engine.Game;
import game.zelda.player.Link;

public class RupeePurple extends AbstractRupee {

	public RupeePurple(Link link) {
		super(link, 50, 421);
		sound = Game.sounds.get("link_get_rupee5");
	}

}
