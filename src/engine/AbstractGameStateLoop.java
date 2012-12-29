package engine;

import java.awt.image.BufferedImage;

public abstract class AbstractGameStateLoop {

	protected Game game;
	
	protected long transitionTime;
	
	protected long lastRefresh;
	
	protected final long refreshInterval = 30;
	
	protected AbstractGameStateLoop(Game game) {
		this.game = game;
		transitionTime = System.currentTimeMillis();
	}
	
	public void reset() {
		lastRefresh = System.currentTimeMillis() - refreshInterval;
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
