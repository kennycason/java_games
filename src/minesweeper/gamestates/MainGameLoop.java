package minesweeper.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import engine.AbstractGameLoop;
import engine.Game;

public class MainGameLoop extends AbstractGameLoop {

	private boolean[][] map;
	
	private boolean[][] clicked;
	
	private boolean[][] flagged;
	
	private int[][] sorroundingMines;
	
	private Random random;
	
	private int numMines;
	
	private int width;
	
	private int height;
	
	private int tileDim = 10;
	
	private long mouseLastClicked;
	
	private boolean gameOver;
	
	public MainGameLoop() {
		super();
		width = 20;
		height = 20;
		map = new boolean[width][height];
		clicked = new boolean[width][height];
		flagged = new boolean[width][height];
		sorroundingMines = new int[width][height];
		random = new Random();
		numMines = 35;
		newMap();
		// printMap();
		// clickAll();
	}
	
	@Override
	public void run() {
		handleInput();
		if(Game.clock.systemElapsedMillis() - lastRefresh >= refreshInterval) {
			// paint everything
			draw(game.screen().bufferedImage());
			game.screenPanel().repaint();
			lastRefresh = Game.clock.systemElapsedMillis();
		}
		if(!gameOver) {
			if(isWin() || isLose()) {
				gameOver = true;
			}
		}
	}

	public void handleInput() {
		int x = -1;
		int y = -1;

		if(!gameOver) {
			// reveal
			if (Game.mouse.isMouseClicked(MouseEvent.BUTTON1)) {
				if (Game.clock.elapsedMillis() - mouseLastClicked > 250) {
					// System.out.println("imm. after: " + Game.mouse.x() + " " + Game.mouse.y());
					x = (int) ((Game.mouse.x2() - 10) / (double) (tileDim + 1));
					y = (int) ((Game.mouse.y2() - 10) / (double) (tileDim + 1));		
					if (x >= 0 && x < width && y >= 0 && y < height) {
						mouseLastClicked = Game.clock.elapsedMillis();
						// System.out.println("after: " + Game.mouse.x() + " " + Game.mouse.y());
						// System.out.println("final: " + x + " " + y + " zoom: " + Game.zoom() + " tileDim: " + tileDim);
						click(x, y, 0);
					}
				}
			}
			// flag
			if (Game.mouse.isMouseClicked(MouseEvent.BUTTON3)) {
				if (Game.clock.elapsedMillis() - mouseLastClicked > 250) {
					x = (int) ((Game.mouse.x2() - 10) / (double) (tileDim + 1));
					y = (int) ((Game.mouse.y2() - 10) / (double) (tileDim + 1));
					System.out.println("cliked loop: " + x + " " + y);
					if (x >= 0 && x < width && y >= 0 && y < height) {
						mouseLastClicked = Game.clock.elapsedMillis();
						flagged[x][y] = !flagged[x][y];
					}
				}
			}
		}
		if(Game.keyboard.isKeyPressed(KeyEvent.VK_C)) {
			clickAll();	
		}
		if(Game.keyboard.isKeyPressed(KeyEvent.VK_R)) {
			newMap();
		}
		if(Game.keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);	
		}
	}
	
	private void click(int x, int y, int depth) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		if(clicked[x][y]) {
			return;
		}
		clicked[x][y] = true;
		if(map[x][y]) {
			return;
		}
		if(sorroundingMines[x][y] > 0) {
			return;
		}
		if(depth > 10) {
		//	return;
		}
		click(x - 1, y - 1, depth + 1);
		click(x, y - 1, depth + 1);
		click(x + 1, y - 1, depth + 1);
		click(x - 1, y, depth + 1);
		click(x + 1, y, depth + 1);
		click(x - 1, y + 1, depth + 1);
		click(x, y + 1, depth + 1);
		click(x + 1, y + 1, depth + 1);
	}

	@Override
	public void draw(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		if(Game.zoom() > 1) {
			g.scale(Game.zoom(), Game.zoom());
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(clicked[x][y]) {
					if(map[x][y]) { // if is mine
						g.setColor(Color.RED);
						g.fillRect(x * (tileDim + 1) + 10, y * (tileDim + 1) + 10, tileDim, tileDim);
					} else { // draw empty square;
						g.setColor(Color.WHITE);
						g.fillRect(x * (tileDim + 1) + 10, y * (tileDim + 1) + 10, tileDim, tileDim);
						if(sorroundingMines[x][y] > 0) {
							g.setColor(Color.BLACK);
							g.setFont(Game.fonts.get("small"));
							g.drawString(String.valueOf(sorroundingMines[x][y]), 
									x * (tileDim + 1) + 11, y * (tileDim + 1) + 19);
						}
					}
				} else {
					g.setColor(Color.GRAY);
					g.fillRect(x * (tileDim + 1) + 10, y * (tileDim + 1) + 10, tileDim, tileDim);
					if(flagged[x][y]) {
						g.setColor(Color.GREEN);
						g.fillRect(x * (tileDim + 1) + 10, y * (tileDim + 1) + 10, tileDim, tileDim);						
					}
				}
				
			}			
		}
		if(gameOver) {
			g.setColor(Color.RED);
			g.setFont(Game.fonts.get("large"));
			if(isWin()) {
				g.drawString("You Win", 20, 120);
			} else if(isLose()) {
				g.drawString("You Lose", 20, 120);
			}
		}
		g.dispose();
	}
	
	@Override
	public void start() {
		lastRefresh = Game.clock.systemElapsedMillis() - refreshInterval;
		transitionTime = Game.clock.systemElapsedMillis();
		Game.clock.start();
	}
	
	@Override
	public void end() {
		Game.clock.stop();
	}
	
	private void newMap() {
		gameOver = false;
		mouseLastClicked = Game.clock.elapsedMillis();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				clicked[x][y] = false;
				flagged[x][y] = false;
				map[x][y] = false;
			}
		}
		
		int minesPlaced = 0;
		while(minesPlaced < numMines) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			if(!map[x][y]) {
				map[x][y] = true; // place mine
				minesPlaced++;
			}
		}
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				sorroundingMines[x][y] = adjacentMines(x, y);
			}
		}
	}

	private int adjacentMines(int x, int y) {
		int tot = 0;
		if(isMine(x - 1, y - 1)) {
			tot++;
		}
		if(isMine(x, y - 1)) {
			tot++;
		}
		if(isMine(x + 1, y - 1)) {
			tot++;
		}
		if(isMine(x - 1, y)) {
			tot++;
		}
		if(isMine(x + 1, y)) {
			tot++;
		}			
		if(isMine(x - 1, y + 1)) {
			tot++;
		}
		if(isMine(x, y + 1)) {
			tot++;
		}
		if(isMine(x + 1, y + 1)) {
			tot++;
		}
		return tot;
	}
	
	private boolean isMine(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			if(map[x][y]) {
				return true;
			}
		}
		return false;
	}

	private void clickAll() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				clicked[x][y] = true;
			}			
		}	
	}
	
	private boolean isWin() {
		boolean win = true;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				win &= ((clicked[x][y] && !map[x][y]) || 
						(!clicked[x][y] && map[x][y]));
			}
		}
		return win;
	}
	
	private boolean isLose() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(clicked[x][y] && map[x][y]) {
					return true;
				}
			}
		}
		return false;
	}

	private void printMap() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				System.out.print(map[x][y] + ",");
			}			
			System.out.println();
		}
	}

}
