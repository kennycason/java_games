package game.zelda.dialog;

import engine.dialog.BasicDialog;

public class ZeldaDialog extends BasicDialog {

	public ZeldaDialog(String text) {
		this(new String[]{text});
	}

	public ZeldaDialog(String[] text) {
		super(text, 10, 40, 220, 52, 50);
	}
	
}
