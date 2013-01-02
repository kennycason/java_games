package engine.map.tiled;


public class MetaTile implements ITile {

	private int value;

	public MetaTile(int value) {
		this.value = value;
	}

	@Override
	public int value() {
		return value;
	}
	
	@Override
	public void value(int value) {
		this.value = value;
	}
	
}
