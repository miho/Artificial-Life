package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public interface EnvironmentMap {
	public int getHeight();
	
	public int getWidth();
	
	public void placeLife(ALife life);
	
	public Dimension getDimension();
	
	public void setHeight(int height);
	
	public void setWidth(int width);
	
	// public void addResource(Consumable r);
	
	public boolean validPosition(Point p);
	
	public boolean validPosition(double x, double y);
	
	public MapObjectMap getResourceList();
	
	public MapObjectMap getLifeList();
	
	public MapObjectMap getObstacleList();
	
	public boolean hasResource(int x, int y);
	
	public boolean hasResource(Point p);
	
	public boolean addLife(ALife life);
	
	public boolean removeLife(ALife life);
	
	public boolean removeLife(Point p);
	
	public boolean hasLife(Point p);
	
	public boolean addObstacle(Obstacle o);
	
	public boolean removeObstacle(Obstacle o);
	
	public boolean removeObstacle(Point p);
	
	public boolean addResource(Resource r);
	
	public void consumeResource(ALife life);
	
	public boolean removeResource(int x, int y);
	
	public boolean removeResource(Point p);
	
	public boolean removeResource(Resource r);
	
	public void resetMap();
	
	public void initResources();
	
	public int getTimeFrame();
	
	public void incrementTimeFrame();
	
	public List<Paintable> getLifePaintables();
	
	public List<Paintable> getObstaclePaintables();
	
	public List<Paintable> getResourcePaintables();
}
