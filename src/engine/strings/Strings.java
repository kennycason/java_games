package engine.strings;

import java.util.Locale;
import java.util.ResourceBundle;

public class Strings {
	protected final ResourceBundle texts;

	public Strings(Locale locale) {
		texts = ResourceBundle.getBundle("properties/texts", locale);
	}

	//Overloading to allow default parameter
	public String get(String key){
		return get(key, (Object[])null);
	}
	
	public String get(String key, Object... o){
		if(o == null){
			return texts.getString(key);
		} else {
			String str = texts.getString(key);
		    for(int i = 0; i < o.length; i++) {
		    	str = str.replace("{" + i + "}", o[i].toString());
		    }
		    return str;
		}
	}

}