package engine.sound;

import engine.Game;

public abstract class AbstractSound {
	
	protected int volume = 50;
	
	protected boolean loaded = false;
	
	protected final String file;
	
	protected SoundChannels channels;
	
	protected AbstractSound(String file) {
		this.file = file;
		if(Game.config.contains("volume")) {
			if("mute".equalsIgnoreCase(Game.config.getString("volume"))) {
				volume = 0;
			} else {
				volume = Game.config.getInt("volume");
			}
		}
	}

	public abstract void play();	
	
	public String file() {
		return file;
	}
	
}
