package engine.event;

public interface IEvent {
	
	void trigger();

	boolean happened();
	
	boolean ready();
	
}
