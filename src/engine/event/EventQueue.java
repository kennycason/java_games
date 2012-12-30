package engine.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EventQueue {
	
	private List<AbstractEvent> events;
	
	public EventQueue() {
		events = new LinkedList<AbstractEvent>();
	}
	
	public void add(AbstractEvent event) {
		events.add(event);
	}
	
	public void add(List<AbstractEvent> events) {
		this.events.addAll(events);
	}
	
	public List<AbstractEvent> all() {
		return events;
	}
	
	public void handle() {
		Iterator<AbstractEvent> iter = events.iterator();
		while(iter.hasNext()) {
			AbstractEvent event = iter.next();
			if(event.ready()) {
				event.deploy();
				iter.remove();
			}
		}
	}

}
