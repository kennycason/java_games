package engine.font;

import java.awt.Font;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class FontBank {

	private HashMap<String, Font> resources = new HashMap<String, Font>();
	
	private final static Logger LOGGER = Logger.getLogger(FontBank.class.getName());
	
	public FontBank() {
	}
	
	public void set(String key, Font font) {
		LOGGER.debug("loading new font: " + key + " - " + font.toString());
		resources.put(key, font);
	}
	
	public Font get(String key) {
		return resources.get(key);
	}
	
	public int size() {
		return resources.size();
	}
	
}
