package engine.entity.weapon;

import java.util.HashMap;

public class WeaponBank {
	
	private static WeaponBank INSTANCE;
	
	private HashMap<String, AbstractWeapon>  resources = new HashMap<String, AbstractWeapon>();
	
	private WeaponBank() {
	}

	public static WeaponBank getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new WeaponBank();
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
