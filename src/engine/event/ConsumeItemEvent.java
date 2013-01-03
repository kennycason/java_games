package engine.event;

import engine.entity.item.AbstractItem;

public class ConsumeItemEvent extends AbstractEvent {
	
	private AbstractItem item;
	
	public ConsumeItemEvent(AbstractItem item) {
		super();
		this.item = item;
	}

	@Override
	public void trigger() {
		happened = true;
		if(item != null) {
			item.consume();
		}
	}

	@Override
	public boolean ready() {
		return true;
	}

}
