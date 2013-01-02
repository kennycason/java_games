package engine.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class EventQueue {
	
	private List<IEvent> events;
	
	public EventQueue() {
		events = new LinkedList<IEvent>();
	}
	
	public void add(IEvent event) {
		events.add(event);
	}
	
	public void add(List<IEvent> events) {
		this.events.addAll(events);
	}
	
	public List<IEvent> all() {
		return events;
	}
	
	public void handle() {
		Iterator<IEvent> iter = events.iterator();
		while(iter.hasNext()) {
			IEvent event = iter.next();
			if(event.ready()) {
				event.trigger();
				iter.remove();
			}
		}
	}

	public int size() {
		return events.size();
	}

}
