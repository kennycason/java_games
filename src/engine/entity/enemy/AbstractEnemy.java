package engine.entity.enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import engine.entity.item.AbstractItem;
import engine.Game;
import engine.entity.AbstractLivingEntity;

public abstract class AbstractEnemy extends AbstractLivingEntity {

	protected List<AbstractItem> dropItems;
	
	protected int dropItemProbability = 100;
	
	private static Random random = new Random();
	
	public AbstractEnemy() {
		super();
		hitSound = Game.sounds.get("enemy_hit");
		deadSound = Game.sounds.get("enemy_die");
		dropItems = new LinkedList<AbstractItem>();
		invincibleTime = 400;
	}
	
	@Override
	public void handle() {
		if(invincible) {
			if(System.currentTimeMillis() - lastTimeHit > invincibleTime) {
				invincible = false;
				flickerCount = 0;
			}
		}
	}
	
	@Override
	public void hit(double damage) {
		super.hit(damage);
		if(dead() && dropItems()) {
			for(AbstractItem item : dropItems) {
				item.locate(x() + -width() / 2 + random.nextInt(width()), y() + -height() / 2 + random.nextInt(height()));
				item.justDropped();
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
