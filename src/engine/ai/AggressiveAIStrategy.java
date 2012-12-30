package engine.ai;

import java.util.Random;

import engine.Game;
import engine.GameFactory;
import engine.entity.AbstractLivingEntity;

public class AggressiveAIStrategy implements IAIStrategy {
	
	private Game game;
	
	private AbstractLivingEntity entity;

	private long lastMoved = System.currentTimeMillis();
	
	private long moveDelay = 500;
	
	private long aggressionMoveDelay = 50;
	
	private boolean moving = false;

	private int moveSpeed = 2;
	
	private int moveSpeedX;
	
	private int moveSpeedY;
	
	private int movingDir = -1;
	
	private int aggressionRange;
	
	Random r = new Random();
	
	public AggressiveAIStrategy(AbstractLivingEntity entity, int moveDelay, int aggressionMoveDelay, int moveSpeed, int aggressionRange) {
		this.game = GameFactory.get();
		this.entity = entity;
		this.aggressionRange = aggressionRange;
		this.aggressionMoveDelay = aggressionMoveDelay;
		this.moveDelay = moveDelay;
		this.moveSpeed = moveSpeed;
	}
	
	@Override
	public void handle() {
		if(moving) {
			entity.locate(entity.x() + moveSpeedX, entity.y() + moveSpeedY);
			moving = false;
		} else {
			double dist = Math.sqrt(Math.pow(entity.x() - game.link().x(), 2) + Math.pow(entity.x() - game.link().y(), 2));
			if(dist <= aggressionRange) {
				if(System.currentTimeMillis() - lastMoved > aggressionMoveDelay) { // move
					moving = true;
					lastMoved = System.currentTimeMillis();	
					if(game.link().x() - entity.x() < -4) {
						moveSpeedX = -moveSpeed;
					} else if(game.link().x() - entity.x() > 4) {
						moveSpeedX = moveSpeed;
					} else {
						moveSpeedX = 0;
					}
					if(game.link().y() - entity.y() < -4) {
						moveSpeedY = -moveSpeed;
					} else if(game.link().y() - entity.y() > 4) {
						moveSpeedY = moveSpeed;
					} else {
						moveSpeedY = 0;
					}
				}
			} else {
				if(System.currentTimeMillis() - lastMoved > moveDelay) { // move
					moving = true;
					System.out.println("moveDelay: " + moveDelay);
					lastMoved = System.currentTimeMillis();			
					movingDir = r.nextInt(16);
					switch(movingDir) {
						case 0:	// north
							moveSpeedX = 0; moveSpeedY = -moveSpeed;
							break;
						case 1:	// east
							moveSpeedX = moveSpeed; moveSpeedY = 0;
							break;
						case 2:	// south
							moveSpeedX = 0; moveSpeedY = moveSpeed;
							break;
						case 3:	// west
							moveSpeedX = -moveSpeed; moveSpeedY = 0;
							break;
						case 4:	// north east
							moveSpeedX = moveSpeed; moveSpeedY = -moveSpeed;
							break;
						case 5:	// south east
							moveSpeedX = moveSpeed; moveSpeedY = moveSpeed;
							break;
						case 6:	// south west
							moveSpeedX = moveSpeed; moveSpeedY = moveSpeed;
							break;
						case 7:	// north west
							moveSpeedX = -moveSpeed; moveSpeedY = -moveSpeed;
							break;
					}
					if(movingDir >= 8 && movingDir <= 12) {
						
					} else {
						moveSpeedX = 0;
						moveSpeedY = 0;
					}					
					
				} else {
					moving = false;
				}
				
			}
			// now check for collision
			if(moving) {
				int offX = entity.offsetX();
				int offY = entity.offsetY();	
				if(moveSpeedX < 0) {
					if(game.map().collide(entity, offX - 1, offY - 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX - 1, offY, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX - 1, offY + 1, moveSpeedX, moveSpeedY)
						) {
						moveSpeedX = 0;
					}
				} else if(moveSpeedX > 0) {
					if(game.map().collide(entity, offX + 1, offY - 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX + 1, offY, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX + 1, offY + 1, moveSpeedX, moveSpeedY)
						) {
						moveSpeedX = 0;
					}
				}
				if(moveSpeedY < 0) {
					if(game.map().collide(entity, offX - 1, offY - 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX, offY - 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX + 1, offY - 1, moveSpeedX, moveSpeedY)
						) {
						moveSpeedY = 0;
					}
				} else if(moveSpeedY > 0) {
					if(game.map().collide(entity, offX - 1, offY + 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX, offY + 1, moveSpeedX, moveSpeedY) ||
						game.map().collide(entity, offX + 1, offY + 1, moveSpeedX, moveSpeedY)
						) {
						moveSpeedY = 0;
					}
				}	
			}
					
		}
		
	}

}
