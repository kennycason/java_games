package engine.sound;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class SoundBank {
	
	private static SoundBank INSTANCE = null;

	private HashMap<String, ISound> resources = new HashMap<String, ISound>();
	
	private final static Logger LOGGER = Logger.getLogger(SoundBank.class.getName()); 
	
	private SoundBank() {
	}
	
	public static SoundBank getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SoundBank();
		}
		return INSTANCE;
	}
	
	public void set(String key, ISound sound) {
		LOGGER.debug("loading new sound: " + sound.file());
		resources.put(key, sound);
	}
	
	public ISound get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}
	
}
