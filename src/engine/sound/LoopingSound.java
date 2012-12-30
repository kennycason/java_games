package engine.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author kenny
 *
 */
public class LoopingSound extends AbstractSound {
	
	private Clip clip;

	public LoopingSound(String file) {	
		super(file);
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(file));
			// @TODO use below method to load
	        // AudioInputStream inputStream = AudioSystem.getAudioInputStream(Game.class.getResourceAsStream(file));
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
