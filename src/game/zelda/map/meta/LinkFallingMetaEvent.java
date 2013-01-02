package game.zelda.map.meta;

import engine.event.AbstractEvent;
import engine.map.tiled.MetaTilesNumber;

public class LinkFallingMetaEvent extends AbstractEvent {
	
	public LinkFallingMetaEvent() {

	}

	@Override
	public void trigger() {
		if(!game.link().falling()) {
			game.link().fall();
			happened = true;
		}
	}
		
	@Override
	public boolean ready() {
		if(game.map().metaLayer()[game.link().mapX()][game.link().mapY()].value() == MetaTilesNumber.HOLE) {
			return true;
		}
		happened = true;
		return false;
	}

}
