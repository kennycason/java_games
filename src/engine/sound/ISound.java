package engine.sound;

public interface ISound {

	void play();
	
	void pause();
	
	void stop();
	
	boolean playing();
	
	void volume(int volume);
	
	int volume();

	String file();
	
}
