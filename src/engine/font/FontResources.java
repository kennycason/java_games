package engine.font;

import java.awt.Font;
import java.util.HashMap;

public class FontResources {
	
	private static FontResources INSTANCE = null;

	private HashMap<String, Font> resources = new HashMap<String, Font>();
	
	private FontResources() {
	}
	
	public static FontResources getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FontResources();
		}
		return INSTANCE;
	}
	
	public void set(String key, Font sprite) {
		resources.put(key, sprite);
	}
	
	public Font get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}
	
}
