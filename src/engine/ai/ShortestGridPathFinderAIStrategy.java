package engine.ai;

import engine.Game;
import engine.GameFactory;
import engine.entity.AbstractEntity;
import engine.entity.AbstractLivingEntity;
import engine.map.tiled.MetaTilesNumber;
import engine.math.PositionVector;

public class ShortestGridPathFinderAIStrategy implements IAIStrategy {

	private Game game;

	private AbstractLivingEntity entity;

	private AbstractEntity target;
	
	private long lastMoved = Game.clock.elapsedMillis();

	private long moveDelay = 50;
	
	private boolean moving = false;

	private int moveSpeed = 2;

	private PositionVector move;
	
	private int startX;
	
	private int startY;
	
	private int endX;
	
	private int endY;

	//private LinkedList<PositionVector> path;
	
	private static PathNode[][] nodes; // static is ok for now because it's not multithreaded
	
	// private final static Logger LOGGER = Logger.getLogger(DynamicShortestGridPathFinderAIStrategy.class);

	// TODO debating on a DynamicProgramming approach or A*
	public ShortestGridPathFinderAIStrategy(AbstractLivingEntity entity, AbstractEntity target, int moveDelay, int moveSpeed) {
		game = GameFactory.get();
		this.entity = entity;
		this.target = target;
		this.moveDelay = moveDelay;
		this.moveSpeed = moveSpeed;
		//path = new LinkedList<PositionVector>();
		move = new PositionVector();
		nodes = new PathNode[game.map().width()][game.map().height()];
	}

	@Override
	public void handle() {
		if (moving) {
			move = game.map().move(entity, move);
			entity.locate(entity.x() + move.x(), entity.y() + move.y());
			moving = false;
		} else {
			if(Game.clock.elapsedMillis() - lastMoved > moveDelay) {
				double dist = Math.sqrt(Math.pow(entity.x() - target.x(),
						2) + Math.pow(entity.y() - target.y(), 2));
				if (dist < 500) {
					moving = true;
					move.set(0, 0);
					lastMoved = Game.clock.elapsedMillis();
					resetNodes();
					endX = target.mapX();
					endY = target.mapY();
					startX = entity.mapX();
					endY = entity.mapY();
					search(startX, startY, endX, endY);
				}
				// pop off top position vector and move

			}
		}

	}
	
	private void search(int startX, int startY, int endX, int endY) {
		if(startX < 0 || startX > game.map().width() ||
				startY < 0 || startY > game.map().height()) {
			return;
		}
		if(nodes[startX][startY].checked) {
			
		}
		if(game.map().collisionLayer()[startX][startY].value() == MetaTilesNumber.COLLISION) {
			nodes[startX][startY].checked = true;
			nodes[startX][startY].dist = Integer.MAX_VALUE;
			return;
		}
		nodes[startX][startY].dist = dist(startX, startY, endX, endY);
		search(startX + 1, startY, endX, endY);
		// ...
		
	}
	
	private double dist(int startX, int startY, int endX, int endY) {
		return Math.sqrt(Math.pow(startX - endX, 2)
				+ Math.pow(startY - endY, 2));
	}
	
	private void resetNodes() {
		for(int y = 0; y < game.map().height(); y++) {
			for(int x = 0; x < game.map().width(); x++) {
				nodes[x][y].checked = false;
				nodes[x][y].prevX = -1;
				nodes[x][y].prevY = -1;
				nodes[x][y].dist = Double.MAX_VALUE;
			}
		}
	}
	
	private class PathNode {
		
		public int prevX = -1;
		
		public int prevY = -1;;
		
		public double dist = Double.MAX_VALUE;
		
		public boolean checked = false;
		
	}

}
