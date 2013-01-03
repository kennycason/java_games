package engine.sound;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class SoundBank {
	
	private HashMap<String, AbstractSound> resources = new HashMap<String, AbstractSound>();
	
	private final static Logger LOGGER = Logger.getLogger(SoundBank.class.getName()); 
	
	public SoundBank() {
	}
	
	public void set(String key, AbstractSound sound) {
		LOGGER.debug("loading new sound: " + sound.file());
		resources.put(key, sound);
	}
	
	public AbstractSound get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}

	public HashMap<String, AbstractSound> all() {
		return resources;
	}

	public void stopAll() {
		for(String soundId : all().keySet()) {
			if(get(soundId) instanceof LoopingSound) {
				((LoopingSound) get(soundId)).stop();
			}
		}
		SoundChannels.getInstance().stopAll();
	}
	
}
