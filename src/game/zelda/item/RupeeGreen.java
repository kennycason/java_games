package game.zelda.item;

import engine.sound.SoundBank;


public class RupeeGreen extends AbstractRupee {

	public RupeeGreen() {
		super(1, 418);
		sound = SoundBank.getInstance().get("link_get_rupee");
	}

}
