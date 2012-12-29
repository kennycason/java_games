package engine;

public class GameFactory {

	private static Game game;
	
	public static void set(Game g) {
		game = g;
	}
	
	public static Game get() {
		return game;
	}
	
}
