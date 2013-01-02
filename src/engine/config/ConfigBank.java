package engine.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigBank extends Properties {
	
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(ConfigBank.class);
	
	public ConfigBank() {
		super();
		load();
	}
	
	public void load() {
		// first local for file
		 try {
			InputStream is = new FileInputStream("game.config");
			super.load(is);
			LOGGER.trace("Loaded user config");
		} catch (Exception e) { // pokemon error handling
			// else load default config in resources
			try {
				super.load(getClass().getClassLoader().getResourceAsStream("config/default.config"));
				LOGGER.trace("Loaded default config");
			} catch (IOException e1) {
				LOGGER.error("Default Config file failed to load");
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * must override because super.contains(Object o) returns false when passing strings
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		if(key == null) {
			return false;
		}
		for(Object k : super.keySet()) {
			if(key.equals(k)) {
				return true;
			}
		}
		return false;
	}
	
	public String getString(String key) {
		return (String)super.get(key);
	}
	
	public int getInt(String key) {
		return Integer.parseInt((String)super.get(key));
	}
	
	public long getLong(String key) {
		return Long.parseLong((String)super.get(key));
	}
	
	public float getFloat(String key) {
		return Float.parseFloat((String)super.get(key));
	}
	
	public double getDouble(String key) {
		return Double.parseDouble((String)super.get(key));
	}	

}