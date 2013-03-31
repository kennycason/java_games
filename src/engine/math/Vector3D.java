package engine.math;

public class Vector3D {

	private double x;
	
	private double y;
	
	private double z;
	
	public Vector3D() {
		this(0, 0, 0);
	}
	public Vector3D(double x, double y, double z) {
		set(x, y, z);
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public double z() {
		return z;
	}
	
	public void x(double x) {
		this.x = x;
	}
	
	public void y(double y) {
		this.y = y;
	}
	
	public void z(double z) {
		this.z = z;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3D unit() {
		double d = length();
		double newX = x / d;
		double newY = y / d;
		double newZ = z / d;
		return new Vector3D(newX, newY, newZ);
	}
	
	public Vector3D add(Vector3D v2) {
		x += v2.x();
		y += v2.y();
		return this;
	}
	
	public Vector3D add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public Vector3D subtract(Vector3D v2) {
		x -= v2.x();
		y -= v2.y();
		z -= v2.z();
		return this;
	}
	
	public Vector3D subtract(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public Vector3D multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3D divide(double scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
		return this;
	}
	
	public Vector3D scaleToLength(double length) {
		double d = length();
		x /= d;
		y /= d;
		z /= d;
		x *= length;
		y *= length;
		z *= length;
		return this;
	}
	
	public Vector3D clone() {
		return new Vector3D(x, y, z);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
