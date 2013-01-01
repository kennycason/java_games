package engine.math;

public class PositionVector {

	private int x;
	
	private int y;
	
	public PositionVector() {
		this(0, 0);
	}
	public PositionVector(int x, int y) {
		set(x, y);
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public void x(int x) {
		this.x = x;
	}
	
	public void y(int y) {
		this.y = y;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public PositionVector unit() {
		double d = length();
		int newX = (int) (x / d);
		int newY = (int) (y / d);
		return new PositionVector(newX, newY);
	}
	
	public PositionVector add(PositionVector v2) {
		x += v2.x();
		y += v2.y();
		return this;
	}
	
	public PositionVector add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public PositionVector subtract(PositionVector v2) {
		x -= v2.x();
		y -= v2.y();
		return this;
	}
	
	public PositionVector subtract(int x, int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public PositionVector multiply(int scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public PositionVector divide(int scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}
	
	public PositionVector scaleToLength(int length) {
		double d = length();
		x /= d;
		y /= d;
		x *= length;
		y *= length;
		return this;
	}
	
	public PositionVector copy() {
		return new PositionVector(x, y);
	}
	
	public String toString() {
		return "(" + x() + ", " + y() + ")";
	}
}
