package net.cammann.tom.fyp.core;

import java.awt.Point;

/**
 * Low level object to give position on a map.
 * 
 * @author TC
 * 
 */
public class AbstractMapObject implements MapObject {
	
	/**
	 * Location of object.
	 */
	private Point p = new Point(0, 0);
	/**
	 * Radius of object.
	 */
	protected double radius;
	
	@Override
	public final int getX() {
		return p.x;
	}
	
	@Override
	public final int getY() {
		return p.y;
	}
	
	@Override
	public final Point getPosition() {
		return new Point(p);
	}
	
	@Override
	public final double getRadius() {
		return radius;
	}
	
	public final void setPosition(final Point p) {
		this.p = new Point(p);
	}
	
	public final void setPosition(final int x, final int y) {
		p.setLocation(x, y);
	}
	
	/**
	 * @param x
	 *            change position
	 */
	public final void setX(final int x) {
		p.x = x;
	}
	
	/**
	 * @param y
	 *            change position
	 */
	public final void setY(final int y) {
		p.y = y;
	}
}
