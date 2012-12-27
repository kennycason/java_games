package engine.math;

public class Vector2D {

	private double x;
	
	private double y;
	
	public Vector2D() {
		this(0, 0);
	}
	public Vector2D(double x, double y) {
		set(x, y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public void x(double x) {
		this.x = x;
	}
	
	public void y(double y) {
		this.y = y;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D unit() {
		double d = length();
		double newX = x / d;
		double newY = y / d;
		return new Vector2D(newX, newY);
	}
	
	public Vector2D add(Vector2D v2) {
		x += v2.x();
		y += v2.y();
		return this;
	}
	
	public Vector2D add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2D subtract(Vector2D v2) {
		x -= v2.x();
		y -= v2.y();
		return this;
	}
	
	public Vector2D subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2D multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2D divide(double scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}
	
	public Vector2D scaleToLength(double length) {
		double d = length();
		x /= d;
		y /= d;
		x *= length;
		y *= length;
		return this;
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
