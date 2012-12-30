package game.zelda.item;

import engine.sound.SoundBank;

public class RupeePurple extends AbstractRupee {

	public RupeePurple() {
		super(50, 421);
		sound = SoundBank.getInstance().get("link_get_rupee5");
	}

}
