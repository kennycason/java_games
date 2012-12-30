package engine.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundChannels {

	private static SourceDataLine[] lines;
	
	private final int numLines;
	
	private static SoundChannels INSTANCE;
	
	public static SoundChannels getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SoundChannels(10);
		}
		return INSTANCE;
	}
	
	public SoundChannels(int numLines) {
		this.numLines = numLines;
		lines = new SourceDataLine[numLines];
	}
	
	public SourceDataLine getFreeLine(AudioFormat format, int volume) {
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			for(int i = 0; i < numLines; i++) {
				if(lines[i] == null) {
					lines[i] = (SourceDataLine) AudioSystem.getLine(info);
				} else if(!lines[i].isActive()) {
					return lines[i];
				}
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SourceDataLine[] lines() {
		return lines;
	}

	public void stopAll() {
		for(int i = 0; i < numLines; i++) {
			if(lines[i] != null) {
				if(lines[i].isActive()) {
					lines[i].close();
				}
			}
		}
	}
	
}
