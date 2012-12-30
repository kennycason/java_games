package game.zelda.item;

import engine.sound.SoundBank;


public class RupeeBlue extends AbstractRupee {

	public RupeeBlue() {
		super(5, 419);
		sound = SoundBank.getInstance().get("link_get_rupee5");
	}

}
