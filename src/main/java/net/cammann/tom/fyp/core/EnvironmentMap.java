package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * EnvironmentMap interface.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public interface EnvironmentMap {
	
	/**
	 * <p>
	 * getHeight.
	 * </p>
	 * 
	 * @return map height
	 */
	int getHeight();
	
	/**
	 * <p>
	 * getWidth.
	 * </p>
	 * 
	 * @return map width
	 */
	int getWidth();
	
	/**
	 * <p>
	 * getDimension.
	 * </p>
	 * 
	 * @return size of map
	 */
	Dimension getDimension();
	
	/**
	 * Check positions validity.
	 * 
	 * @return true if the position does not contain an obstacle or is off the
	 *         map. False if on top of a obstacle or off the map.
	 * @param p
	 *            a {@link java.awt.Point} object.
	 */
	boolean validPosition(Point p);
	
	/**
	 * Check positions validity.
	 * 
	 * @return true if the position does not contain an obstacle or is off the
	 *         map. False if on top of a obstacle or off the map.
	 * @param x
	 *            a double.
	 * @param y
	 *            a double.
	 */
	boolean validPosition(double x, double y);
	
	/**
	 * Provides an iterator for listing resources.
	 * 
	 * Used by map in painting.
	 * 
	 * @return a {@link java.util.Iterator} object.
	 */
	Iterator<MapObject> getResourceIterator();
	
	/**
	 * Provides iterator for listing obstacles.
	 * 
	 * Gives obstacles currently on the map
	 * 
	 * @return a {@link java.util.Iterator} object.
	 */
	Iterator<MapObject> getObstacleIterator();
	
	/**
	 * Provides an iterator for listing ALife currently on the map.
	 * 
	 * @return a {@link java.util.Iterator} object.
	 */
	Iterator<ALife> getLifeIterator();
	
	/**
	 * <p>
	 * hasResource.
	 * </p>
	 * 
	 * @param x
	 *            a int.
	 * @param y
	 *            a int.
	 * @return true if position contains a resource
	 */
	boolean hasResource(int x, int y);
	
	/**
	 * <p>
	 * hasResource.
	 * </p>
	 * 
	 * @param p
	 *            arbitary position on map
	 * @return true if position contains a resource
	 */
	boolean hasResource(Point p);
	
	/**
	 * Checks position for life form.
	 * 
	 * @param p
	 *            a {@link java.awt.Point} object.
	 * @return true if position has life form
	 */
	boolean hasLife(Point p);
	
	/**
	 * Uses life to consume resource
	 * 
	 * Takes life's current position on the map, if this position contains a
	 * resource then the resource is removed from the map and the calories are
	 * added to life's energy. Returns false if there was no resource on that
	 * position.
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @return true if a resource is consumed.
	 */
	public boolean consumeResource(ALife life);
	
	/**
	 * Used to reset all resources, obstacles and life forms on the map.
	 * 
	 * Resets counters to zero, resets life energy. Removes all resources and
	 * obstacles from the map and adds them back in.
	 */
	public void resetMap();
	
	/**
	 * Adds life form to the current map.
	 * 
	 * This will add the life form in its current position to the map. Checks
	 * position when adding
	 * 
	 * If false is returned it has not been added
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @return false if off map or onto of other life or obstacle and not added.
	 */
	@Deprecated
	boolean addLife(ALife life);
	
	/**
	 * Removes life from the map.
	 * 
	 * Directly removes the ALife from the current map
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @return false if no such life is on the map
	 */
	@Deprecated
	boolean removeLife(ALife life);
	
	/**
	 * Removes life from the map.
	 * 
	 * Directly removes the ALife from the current map
	 * 
	 * @return false if no such life is on the map
	 * @param p
	 *            a {@link java.awt.Point} object.
	 */
	@Deprecated
	boolean removeLife(Point p);
	
	/**
	 * <p>
	 * getTimeFrameNo.
	 * </p>
	 * 
	 * @return a int.
	 */
	int getTimeFrameNo();
	
	/**
	 * <p>
	 * incrementTimeFrame.
	 * </p>
	 */
	void incrementTimeFrame();
	
	/**
	 * <p>
	 * getLifePaintables.
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	List<Paintable> getLifePaintables();
	
	/**
	 * <p>
	 * getObstaclePaintables.
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	
	List<Paintable> getObstaclePaintables();
	
	/**
	 * <p>
	 * getResourcePaintables.
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	
	List<Paintable> getResourcePaintables();
	
	void placeLife(final ALife life);
	
}
