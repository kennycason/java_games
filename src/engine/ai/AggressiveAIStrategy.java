package engine.ai;

import java.util.Random;

import engine.Game;
import engine.GameFactory;
import engine.entity.AbstractLivingEntity;
import engine.math.PositionVector;

public class AggressiveAIStrategy implements IAIStrategy {
	
	private Game game;
	
	private AbstractLivingEntity entity;

	private long lastMoved = System.currentTimeMillis();
	
	private long moveDelay = 500;
	
	private long aggressionMoveDelay = 50;
	
	private boolean moving = false;

	private int moveSpeed = 2;
	
	private PositionVector move;
	
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
		move = new PositionVector();
	}
	
	@Override
	public void handle() {
		if(moving) {
			move = game.map().move(entity, move);
			entity.locate(entity.x() + move.x(), entity.y() + move.y());
			moving = false;
		} else {
			double dist = Math.sqrt(Math.pow(entity.x() - game.link().x(), 2) + Math.pow(entity.x() - game.link().y(), 2));
			if(dist <= aggressionRange) {
				if(System.currentTimeMillis() - lastMoved > aggressionMoveDelay) { // move
					moving = true;
					move.set(0, 0);
					lastMoved = System.currentTimeMillis();	
					if(game.link().x() - entity.x() < -4) {
						move.x(-moveSpeed);
					} else if(game.link().x() - entity.x() > 4) {
						move.x(moveSpeed);
					} else {
						move.x(0);
					}
					if(game.link().y() - entity.y() < -4) {
						move.y(-moveSpeed);
					} else if(game.link().y() - entity.y() > 4) {
						move.y(moveSpeed);
					} else {
						move.y(0);
					}
				}
			} else {
				if(System.currentTimeMillis() - lastMoved > moveDelay) { // move
					moving = true;
					// System.out.println("moveDelay: " + moveDelay);
					lastMoved = System.currentTimeMillis();			
					movingDir = r.nextInt(16);
					switch(movingDir) {
						case 0:	// north
							move.set(0, -moveSpeed);
							break;
						case 1:	// east
							move.set(moveSpeed, 0);
							break;
						case 2:	// south
							move.set(0, moveSpeed);
							break;
						case 3:	// west
							move.set(-moveSpeed, 0);
							break;
						case 4:	// north east
							move.set(moveSpeed, -moveSpeed);
							break;
						case 5:	// south east
							move.set(moveSpeed, moveSpeed);
							break;
						case 6:	// south west
							move.set(-moveSpeed, moveSpeed);
							break;
						case 7:	// north west
							move.set(-moveSpeed, -moveSpeed);
							break;
					}
					if(movingDir >= 8 && movingDir <= 12) {
						
					} else {
						move.set(0, 0);
					}					
					
				} else {
					moving = false;
				}
				
			}
					
		}
		
	}

}
