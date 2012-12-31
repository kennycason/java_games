package engine.il8n;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringBank {
	
	private final ResourceBundle texts;

	public StringBank(Locale locale) {
		texts = ResourceBundle.getBundle("properties/texts", locale, new Utf8Control());
	}

	//Overloading to allow default parameter
	public String get(String key){
		return get(key, (Object[])null);
	}
	
	public String get(String key, Object ... o){
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