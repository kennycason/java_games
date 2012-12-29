package engine;

import java.awt.image.BufferedImage;

public abstract class AbstractGameLoop {

	protected Game game;
	
	protected long transitionTime;
	
	protected long lastRefresh;
	
	protected final long refreshInterval = 30;
	
	protected AbstractGameLoop() {
		game = GameFactory.get();
		reset();
	}
	
	public void reset() {
		lastRefresh = System.currentTimeMillis() - refreshInterval;
		transitionTime = System.currentTimeMillis();
	}
	
	public abstract void run();
	
	public abstract void draw(BufferedImage bi);
	
	public long transitionTime() {
		return transitionTime;
	}
	
	public void transitionTime(long transitionTime) {
		this.transitionTime = transitionTime;
	}
}
