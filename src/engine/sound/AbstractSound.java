package engine.sound;

public abstract class AbstractSound {
	
	protected int volume = 50;
	
	protected boolean loaded = false;
	
	protected final String file;
	
	protected SoundChannels channels;
	
	protected AbstractSound(String file) {
		this.file = file;
	}

	public abstract void play();	
	
	public String file() {
		return file;
	}
	
}
