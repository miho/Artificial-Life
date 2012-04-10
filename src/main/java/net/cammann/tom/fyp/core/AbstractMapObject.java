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
	protected Point p;
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
		return p;
	}
	
	@Override
	public final double getRadius() {
		return radius;
	}
	
}
