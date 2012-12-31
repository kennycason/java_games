package engine.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LoopingSound extends AbstractSound {
	
	private Clip clip;

	public LoopingSound(String file) {	
		super(file);
		try {
			// below causes bug per: http://stackoverflow.com/questions/9659842/java-exception-reading-stream-from-resource-wav
			//InputStream is = getClass().getClassLoader().getResourceAsStream(file);
			URL url = this.getClass().getClassLoader().getResource(file);
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
	        clip.open(inputStream);
	        loaded = true;
	        volume(volume);
		} catch(LineUnavailableException e) {
			e.printStackTrace();
			loaded = false;
		} catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
			loaded = false;
		} catch(IOException e) {
			e.printStackTrace();
			loaded = false;
		}
	}

	public void play() {
		if (loaded) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0); 
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void stop() {
		if (loaded) {
			clip.stop();
		}
	}

	public boolean playing() {
		return clip.isActive();
	}

	public void volume(int volume) {
		this.volume = volume;
		if (this.volume < 0) {
			this.volume = 0;
		} else if (this.volume > 100) {
			this.volume = 100;
		}
		if (loaded) {
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			float amt = 0;
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			amt = (float) (gainControl.getMinimum() + range * this.volume
					/ 100.0);
			gainControl.setValue(amt); // Reduce volume by 10 decibels.
		}
	}

}
