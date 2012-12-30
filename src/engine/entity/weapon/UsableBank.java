package engine.entity.weapon;

import java.util.HashMap;

public class UsableBank {
	
	private static UsableBank INSTANCE;
	
	private HashMap<String, AbstractUsableEntity>  resources = new HashMap<String, AbstractUsableEntity>();
	
	private UsableBank() {
	}

	public static UsableBank getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UsableBank();
		}
		return INSTANCE;
	}

	public void set(String key, AbstractUsableEntity sprite) {
		resources.put(key, sprite);
	}
	
	public AbstractUsableEntity get(String resource) {
		return resources.get(resource);
	}
}
