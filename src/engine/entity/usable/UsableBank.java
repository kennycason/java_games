package engine.entity.usable;

import java.util.HashMap;

public class UsableBank {
	
	private HashMap<String, AbstractUsableEntity>  resources = new HashMap<String, AbstractUsableEntity>();
	
	public UsableBank() {
	}

	public void set(String key, AbstractUsableEntity sprite) {
		resources.put(key, sprite);
	}
	
	public AbstractUsableEntity get(String resource) {
		return resources.get(resource);
	}

	public HashMap<String, AbstractUsableEntity> all() {
		return resources;
	}
	
}
