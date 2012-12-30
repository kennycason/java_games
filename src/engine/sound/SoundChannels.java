package engine.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;

public class SoundChannels {

	private static SourceDataLine[] lines;
	
	private final int numLines;
	
	private volatile int currentLine = 0;
	
	private static SoundChannels INSTANCE;
	
	private final static Logger LOGGER = Logger.getLogger(SoundChannels.class.getName());
	
	public static SoundChannels getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SoundChannels(20);
		}
		return INSTANCE;
	}
	
	public SoundChannels(int numLines) {
		this.numLines = numLines;
		lines = new SourceDataLine[numLines];
	}
	
	public SourceDataLine getFreeLine(AudioFormat format, int volume) {
		try {
			synchronized(this) {
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
					if(currentLine >= lines.length) {
						currentLine = 0;
					}
					if(lines[currentLine] == null) {
						lines[currentLine] = (SourceDataLine) AudioSystem.getLine(info);
					} 
					if(!lines[currentLine].isActive()) {
						return lines[currentLine++];
					}
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
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
