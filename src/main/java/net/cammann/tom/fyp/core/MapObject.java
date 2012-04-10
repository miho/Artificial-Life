package net.cammann.tom.fyp.core;

import java.awt.Point;

/**
 * High level interface used by any object that is put on the map.
 * 
 * @author TC
 * 
 */
public interface MapObject {
	
	/**
	 * 
	 * @return x position
	 */
	int getX();
	
	/**
	 * 
	 * @return y position
	 */
	int getY();
	
	/**
	 * 
	 * @return position
	 */
	Point getPosition();
	
	/**
	 * 
	 * @return radius
	 */
	double getRadius();
}
