package engine.entity.weapon;

import java.util.HashMap;

public class WeaponResources {
	
	private static WeaponResources INSTANCE;
	
	private HashMap<String, AbstractWeapon>  resources = new HashMap<String, AbstractWeapon>();
	
	private WeaponResources() {
	}

	public static WeaponResources getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new WeaponResources();
		}
		return INSTANCE;
	}

	public void set(String key, AbstractWeapon sprite) {
		resources.put(key, sprite);
	}
	
	public AbstractWeapon get(String resource) {
		return resources.get(resource);
	}
}
