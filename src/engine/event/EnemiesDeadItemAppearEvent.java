package engine.event;

import engine.entity.item.AbstractItem;
import engine.sound.AbstractSound;
import engine.sound.SoundBank;


public class EnemiesDeadItemAppearEvent extends AbstractEnemiesDeadEvent {
	
	private AbstractItem item;
	
	private AbstractSound sound;
	
	public EnemiesDeadItemAppearEvent(AbstractItem item) {
		super();
		this.item = item;
		sound = SoundBank.getInstance().get("secret");
	}

	@Override
	public void deploy() {
		sound.play();
		game.map().items().add(item);
		happened = true;
	}

}
