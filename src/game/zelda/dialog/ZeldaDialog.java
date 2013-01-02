package game.zelda.dialog;

import engine.dialog.BasicDialog;

public class ZeldaDialog extends BasicDialog {

	public ZeldaDialog(String text) {
		this(new String[]{text});
	}

	public ZeldaDialog(String[] text) {
		super(text, 10, 130, 220, 100, 30);
		textSpeed = 100;
	}
	
}
