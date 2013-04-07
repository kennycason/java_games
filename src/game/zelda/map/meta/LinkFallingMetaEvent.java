package game.zelda.map.meta;

import engine.entity.AbstractLivingEntity;
import engine.event.AbstractEvent;
import engine.map.tiled.MetaTilesNumber;

public class LinkFallingMetaEvent extends AbstractEvent {
	
	private AbstractLivingEntity entity;
	
	public LinkFallingMetaEvent(AbstractLivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void trigger() {
		if(!entity.falling()) {
			entity.fall();
			happened = true;
		}
	}
		
	@Override
	public boolean ready() {
		if(game.map().metaLayer()[entity.mapX()][entity.mapY()].value() == MetaTilesNumber.HOLE) {
			return true;
		}
		happened = true;
		return false;
	}

}
