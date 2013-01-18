package engine.graphics.sprite;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class SpriteBank {
	
	private HashMap<String, ISpriteBankResource> resources = new HashMap<String, ISpriteBankResource>();
	
	private final static Logger LOGGER = Logger.getLogger(SpriteBank.class.getName());
	
	public SpriteBank() {
	}
	
	public void set(String key, ISpriteBankResource sprite) {
		LOGGER.debug("loading new sprite: " + key);
		resources.put(key, sprite);
	}
	
	public ISpriteBankResource get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}
	
}
