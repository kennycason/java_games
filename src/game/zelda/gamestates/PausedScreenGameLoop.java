package game.zelda.gamestates;

import java.awt.image.BufferedImage;

import engine.AbstractGameLoop;
import engine.GameStateEnum;
import game.zelda.Buttons;

public class PausedScreenGameLoop extends AbstractGameLoop {

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastRefresh >= refreshInterval) {
			if (game.keyboard().isKeyPressed(Buttons.PAUSE)) {
				if (currentTime - game.gameLoops().get(GameStateEnum.PAUSED).transitionTime() >= 1000) {
					end();
					game.gameLoops().get(GameStateEnum.MAIN).start();
					game.gameState(GameStateEnum.MAIN);
				}
			}
			lastRefresh = System.currentTimeMillis();
		}
	}

	@Override
	public void end() {
		
	}

	@Override
	public void draw(BufferedImage bi) {
		
	}

}
