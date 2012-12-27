package engine.sprite;

import java.util.HashMap;

public class SpriteResources {
	
	private static SpriteResources INSTANCE = null;

	private HashMap<String, ISpriteResource> resources = new HashMap<String, ISpriteResource>();
	
	private SpriteResources() {
	}
	
	public static SpriteResources getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SpriteResources();
		}
		return INSTANCE;
	}
	
	public void set(String key, ISpriteResource sprite) {
		resources.put(key, sprite);
	}
	
	public ISpriteResource get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}
	
}
