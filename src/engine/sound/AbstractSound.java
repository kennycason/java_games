package engine.sound;

public abstract class AbstractSound {
	
	protected int volume = 50;
	
	protected boolean loaded = false;
	
	protected final String file;
	
	protected AbstractSound(String file) {
		this.file = file;
	}

	public abstract void play();
	
	public abstract void pause();
	
	public abstract void stop();
	
	public abstract boolean playing();
	
	public abstract void volume(int volume);
	
	public int volume() {
		return volume;
	}
	
	public boolean mute() {
		return volume == 0;
	}
	
	public String file() {
		return file;
	}
	
}
