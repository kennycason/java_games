package game.zelda.item;

import engine.sound.SoundBank;


public class RupeeRed extends AbstractRupee {

	public RupeeRed() {
		super(20, 420);
		sound = SoundBank.getInstance().get("link_get_rupee5");
	}

}
