package engine.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.GameFactory;


public class EventQueue {
	
	private Game game;
	
	private List<IEvent> events;
	
	public EventQueue() {
		events = new LinkedList<IEvent>();
		game = GameFactory.get();
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
		// add new events
		events.addAll(game.map().newEvents());
		game.map().newEvents().clear();
		
		// handle all events
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
