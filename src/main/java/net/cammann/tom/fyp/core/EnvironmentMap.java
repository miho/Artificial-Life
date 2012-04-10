package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public interface EnvironmentMap {
	
	/**
	 * 
	 * @return map height
	 */
	public int getHeight();
	
	/**
	 * 
	 * @return map width
	 */
	public int getWidth();
	
	/**
	 * 
	 * @return size of map
	 */
	public Dimension getDimension();
	
	/**
	 * Check positions validity
	 * 
	 * @return true if the position does not contain an obstacle or is off the
	 *         map. False if on top of a obstacle or off the map.
	 */
	public boolean validPosition(Point p);
	
	/**
	 * Check positions validity
	 * 
	 * @return true if the position does not contain an obstacle or is off the
	 *         map. False if on top of a obstacle or off the map.
	 */
	public boolean validPosition(double x, double y);
	
	/**
	 * Provides an iterator for listing resources
	 * 
	 * Used by map in painting.
	 * 
	 * @return
	 */
	public Iterator<MapObject> getResourceIterator();
	
	/**
	 * Provides iterator for listing obstacles
	 * 
	 * Gives obstacles currently on the map
	 * 
	 * @return
	 */
	public Iterator<MapObject> getObstacleIterator();
	
	/**
	 * Provides an iterator for listing ALife currently on the map
	 * 
	 * @return
	 */
	public Iterator<ALife> getLifeIterator();
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true if position contains a resource
	 */
	public boolean hasResource(int x, int y);
	
	/**
	 * 
	 * @param p
	 *            arbitary position on map
	 * @return true if position contains a resource
	 */
	public boolean hasResource(Point p);
	
	/**
	 * Checks position for life form
	 * 
	 * @param p
	 * @return true if position has life form
	 */
	public boolean hasLife(Point p);
	
	/**
	 * Uses life to consume resource
	 * 
	 * Takes life's current position on the map, if this position contains a
	 * resource then the resource is removed from the map and the calories are
	 * added to life's energy. Returns false if there was no resource on that
	 * position.
	 * 
	 * @param life
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
	 * @return false if off map or onto of other life or obstacle and not added.
	 */
	@Deprecated
	public boolean addLife(ALife life);
	
	/**
	 * Removes life from the map
	 * 
	 * Directly removes the ALife from the current map
	 * 
	 * 
	 * @param life
	 * @return false if no such life is on the map
	 */
	@Deprecated
	public boolean removeLife(ALife life);
	
	/**
	 * Removes life from the map
	 * 
	 * Directly removes the ALife from the current map
	 * 
	 * 
	 * @param point
	 *            to remove life from
	 * @return false if no such life is on the map
	 */
	@Deprecated
	public boolean removeLife(Point p);
	
	public int getTimeFrameNo();
	
	public void incrementTimeFrame();
	
	public List<Paintable> getLifePaintables();
	
	public List<Paintable> getObstaclePaintables();
	
	public List<Paintable> getResourcePaintables();
	
}
