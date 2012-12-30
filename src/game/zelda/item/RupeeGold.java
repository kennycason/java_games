package game.zelda.item;

import engine.sound.SoundBank;


public class RupeeGold extends AbstractRupee {

	public RupeeGold() {
		super(100, 422);
		sound = SoundBank.getInstance().get("link_get_rupee5");
	}

}
