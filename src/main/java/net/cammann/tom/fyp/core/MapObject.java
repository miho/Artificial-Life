package net.cammann.tom.fyp.core;

import java.awt.Point;

/**
 * High level interface used by any object that is put on the map.
 *
 * @author TC
 * @version $Id: $
 */
public interface MapObject {
	
	/**
	 * <p>getX.</p>
	 *
	 * @return x position
	 */
	int getX();
	
	/**
	 * <p>getY.</p>
	 *
	 * @return y position
	 */
	int getY();
	
	/**
	 * <p>getPosition.</p>
	 *
	 * @return position
	 */
	Point getPosition();
	
	/**
	 * <p>getRadius.</p>
	 *
	 * @return radius
	 */
	double getRadius();
}
