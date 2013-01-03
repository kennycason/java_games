package engine.dialog;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Game;
import game.zelda.Buttons;

public class BasicDialog extends AbstractDialog {

	public BasicDialog(String text, int x, int y, int width, int height, int textSpeed) {
		super(text, x, y, width, height, textSpeed);
	}
	
	public BasicDialog(String[] text, int x, int y, int width, int height, int textSpeed) {
		super(text, x, y, width, height, textSpeed);
	}
	
	
    private void drawString(Graphics2D g, String text, int x, int y) {
    	int i = 0;
        for (String line : text.split("\n")) {
        	if(i > 0) {
        		y += g.getFontMetrics().getHeight();
        	}
            g.drawString(line, x, y);
            i++;
        }
    }

	@Override
	public void trigger() {
		happened = true;
		
		while(!finished) {
			if(phase == 1) {
				if(System.currentTimeMillis() - lastDraw > textSpeed) {	
					Graphics2D g = game.screen().bufferedImage().createGraphics();				
					game.screen().draw(g, 0, 0);
					if(game.zoom() > 1) {
						g.scale(game.zoom(), game.zoom());
					}
					g.setColor(Color.GRAY);
					g.fillRect(x, y, width, height);
					g.setColor(Color.BLACK);
					g.fillRect(x + 2, y + 2, width - 4, height - 4);

					g.setFont(Game.fonts.get("menu_small"));
					g.setColor(Color.WHITE);

					drawString(g, pages[page].substring(0, currentChar), x + 5, y + 15);
					textTyped.play();

					g.dispose();
					game.screenPanel().repaint();
					
					lastDraw = System.currentTimeMillis();
					if(currentChar >= pages[page].length()) {
						phase = 2;
						game.sleep(150);
						pageFinished.play();
					}
					currentChar++;
				}
			} else if(phase == 2) {
				while(!game.keyboard().isKeyPressed(Buttons.ITEM_A) &&
						!game.keyboard().isKeyPressed(Buttons.ITEM_B) &&
						!game.keyboard().isKeyPressed(Buttons.START)) {
					if(page >= pages.length) {
						finished = true;
						game.sleep(150);
					} else {
						page++;
						currentChar = 0;
						phase = 1;
					}
				}
			} 
			
		}
	}

	@Override
	public boolean ready() {
		return true;
	}

}
