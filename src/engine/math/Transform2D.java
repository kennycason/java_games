package engine.math;
/**
 * this class contains transformation functions for a 2D vector
 * @author kenny cason
 * http://www.ken-soft.com
 * 2008 December
 */
public class Transform2D {
	
	public Transform2D() {
	}
	 
	/**
	 * [(cos(a),-sin(a)),
	 *  (sin(a),cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Vector2D rotate(Vector2D point, double theta) {
		theta = Math.toRadians(theta);
	    double x = point.x() * Math.cos(theta) + point.y() * -Math.sin(theta);
	    double y = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);
	    return new Vector2D(x, y);
	}


	public Vector2D unit(Vector2D point) {
		double d = Math.sqrt(point.x()*point.x() + point.y()*point.y());
		double x = point.x() / d;
		double y = point.y() / d;
		return new Vector2D(x, y);
	}
	
	
	
}
