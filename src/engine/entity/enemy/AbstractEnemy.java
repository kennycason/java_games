package engine.entity.enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import engine.Game;
import engine.entity.AbstractLivingEntity;
import engine.entity.item.AbstractItem;
import engine.sound.SoundBank;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	protected List<AbstractItem> dropItems;
	
	protected int dropItemProbability = 100;
	
	private static Random random = new Random();
	
	public AbstractEnemy(Game game) {
		super(game);
		hitSound = SoundBank.getInstance().get("enemy_hit");
		deadSound = SoundBank.getInstance().get("enemy_die");
		dropItems = new LinkedList<AbstractItem>();
	}
	
	@Override
	public void hit(double damage) {
		super.hit(damage);
		if(dead() && dropItems()) {
			for(AbstractItem item : dropItems) {
				item.locate(x() + -width() / 2 + random.nextInt(width()), y() + -height() / 2 + random.nextInt(height()));
			}
			game.map().items().addAll(dropItems);
		}
	}

	public boolean dropItems() {
		int num = random.nextInt(100) + 1;
		if(num <= dropItemProbability) {
			return true;
		}
		return false;
	}

}
