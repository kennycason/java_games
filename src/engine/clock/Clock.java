package engine.clock;

public class Clock {
	
	private static Clock INSTANCE;
	
	private long milliseconds;
	
	private boolean running;
	
	private long startTime;
	
	private long stopTime;
	
	public static Clock getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Clock();
		}
		return INSTANCE;
	}
	
	public Clock() {
		milliseconds = 0;
		stopTime = System.currentTimeMillis();
		running = false;
	}
	
	public void start() {
		running = true;
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		milliseconds = 0;
		running = false;
		stopTime = System.currentTimeMillis();
	}
	
	public void pause() {
		running = false;
		stopTime = System.currentTimeMillis();
		milliseconds += stopTime - startTime;
	}
	
	public void reset() {
		stop();
		start();
	}
	
	public long elapsedMillis() {
		if(running) {
			return milliseconds + System.currentTimeMillis() - startTime;
		} else {
			return milliseconds;
		}
	}
	
	public long elapsedSeconds() {
		return elapsedMillis() / 1000;
	}
	
	public boolean running() {
		return running;
	}

}
