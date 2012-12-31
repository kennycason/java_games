package engine.strings;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Strings {
	protected final ResourceBundle texts;

	public Strings(Locale locale) {
		texts = ResourceBundle.getBundle("properties/texts", locale);
	}

	public String getStatic(String property) {
		return texts.getString(property);
	}

	public String sword() {
		return texts.getString("sword");
	}
	
	public String gameTitle() {
		return texts.getString("title");
	}
	
	public String swordFound(int level) {
		return MessageFormat.format(texts.getString("swordFound"), level);
	}


}