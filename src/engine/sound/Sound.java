package engine.sound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *       http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 *       http://docs.oracle.com/javase/1.4.2/docs/guide/sound/programmer_guide/chapter4.html
 * @author kenny
 * 
 */
public class Sound implements ISound {

	private static final int BUFFER_SIZE = 64 * 1024; // 64 KB
	
	private SourceDataLine line;
	
	private byte[] data;

	private boolean loaded = false;

	private int volume = 50;
	
	private String file;
	
	private AudioFormat format ;

	public Sound(String file) {

		// Set up an audio input stream piped from the sound file.
		try {
			this.file = file;
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file));
			format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);

			int nBytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while (nBytesRead != -1) {
	            while ((nBytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
	                baout.write(buffer, 0, nBytesRead);
	            }
	            audioInputStream.close();
	            baout.close();
	            data = baout.toByteArray();
			}
			// System.out.println("loaded: " + line.getBufferSize() + " bytes");
			loaded = true;
			volume(volume);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			loaded = false;
		} catch (IOException e) {
			e.printStackTrace();
			loaded = false;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			loaded = false;
		}
	}

	@Override
	public void play() {
		if (loaded) {
			Runnable runner = new Runnable() {
				public void run() {
					System.out.println("play: " + file);
					try {
						line.open(format);
						line.start();
						line.write(data, 0, data.length);
						line.drain();
						line.close();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				}
			};
			Thread playThread = new Thread(runner);
			playThread.start();
		}
	}

	@Override
	public void pause() {
		if (loaded) {
			line.stop();
		}
	}

	@Override
	public void stop() {
		if (loaded) {
			line.stop();
		}
	}

	@Override
	public boolean playing() {
		return line.isActive();
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
			FloatControl gainControl = (FloatControl) line
					.getControl(FloatControl.Type.MASTER_GAIN);
			float amt = 0;
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			amt = (float) (gainControl.getMinimum() + range * this.volume / 100.0);
			gainControl.setValue(amt); // Reduce volume by 10 decibels.
		}
	}

	@Override
	public int volume() {
		return volume;
	}

	@Override
	public String file() {
		return file;
	}
	
}
