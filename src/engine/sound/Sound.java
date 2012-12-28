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
 * @TODO 
 *       http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 *       use SourceDataLine
 * @author kenny
 * 
 */
public class Sound implements ISound {

	private Clip clip;

	private boolean loaded = false;

	private int volume = 50;

	private boolean looping = false;

	public Sound(String url) {
		this(url, false);
	}

	public Sound(String file, boolean looping) {
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(file));

			// @TODO use below method to load
			// AudioInputStream inputStream =
			// AudioSystem.getAudioInputStream(Game.class.getResourceAsStream(file));
			clip.open(inputStream);
			loaded = true;
			this.looping = looping;
			volume(volume);

		} catch (LineUnavailableException e) {
			e.printStackTrace();
			loaded = false;
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			loaded = false;
		} catch (IOException e) {
			e.printStackTrace();
			loaded = false;
		}
	}

	@Override
	public void play() {
		if (loaded) {
			if (clip.isRunning()) {
				clip.stop();
			}
			if (looping) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.setFramePosition(0); // Must always rewind!
				clip.start();
			}
		}
	}

	@Override
	public void pause() {
		if (loaded) {
			clip.stop();
		}
	}

	@Override
	public void stop() {
		if (loaded) {
			clip.stop();
		}
	}

	@Override
	public boolean playing() {
		return clip.isRunning();
	}

	@Override
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

	@Override
	public int volume() {
		return volume;
	}

}
