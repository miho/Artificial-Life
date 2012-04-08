package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class SimpleMap implements EnvironmentMap {
	
	protected int height;
	protected int width;
	protected final MapObjectMap resourceList;
	protected final MapObjectMap obstacleList;
	protected final MapObjectMap lifeList;
	protected static final int DEFAULT_WIDTH = 300;
	protected static final int DEFAULT_HEIGHT = 300;
	protected int timeFrameNo = 0;
	
	public SimpleMap() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public SimpleMap(int width, int height) {
		this.height = height;
		this.width = width;
		resourceList = new MapObjectMap();
		obstacleList = new MapObjectMap();
		lifeList = new MapObjectMap();
		initResources();
		
	}
	
	// TODO WHY IS THIS ABSTRACT
	@Override
	public abstract void placeLife(ALife life);
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
		
	}
	
	@Override
	public Dimension getDimension() {
		return new Dimension(width, height);
		
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public boolean hasResource(int x, int y) {
		return this.hasResource(new Point(x, y));
	}
	
	@Override
	public boolean hasResource(Point p) {
		
		return resourceList.hasObject(p) ? true : false;
	}
	
	// @Override
	// public Resource getResource(Point p) {
	// return (Resource) resourceList.getObject(p);
	// }
	//
	// @Override
	// public Resource getResource(int x, int y) {
	// Point p = new Point(x, y);
	// return this.getResource(p);
	// }
	
	@Override
	public boolean removeResource(int x, int y) {
		return this.removeResource(new Point(x, y));
	}
	
	// could change to return resource.
	// only remove one resource (not stacked)
	@Override
	public boolean removeResource(Point p) {
		return resourceList.removeObject(p);
	}
	
	@Override
	public boolean addResource(Resource r) {
		if (resourceList.hasObject(r.getPosition())) {
			return false;
		}
		resourceList.addObject(r);
		return true;
	}
	
	@Override
	public MapObjectMap getResourceList() {
		return resourceList;
	}
	
	@Override
	public boolean removeResource(Resource r) {
		return resourceList.removeObject(r);
	}
	
	@Override
	public boolean validPosition(Point p) {
		
		if (p.getX() > getWidth() || p.getX() < 0) {
			return false;
		} else if (p.getY() > getHeight() || p.getY() < 0) {
			return false;
		}
		if (obstacleList.hasObject(p)) {
			return false;
		}
		
		return true;
		
	}
	
	@Override
	public boolean validPosition(double x, double y) {
		Point p = new Point();
		p.setLocation(x, y);
		return validPosition(p);
	}
	
	@Override
	public boolean addObstacle(Obstacle o) {
		if (obstacleList.hasObject(o.getPosition())) {
			return false;
		}
		obstacleList.addObject(o);
		return true;
		
	}
	
	@Override
	public boolean removeObstacle(Obstacle o) {
		return obstacleList.removeObject(o);
	}
	
	@Override
	public boolean removeObstacle(Point p) {
		return obstacleList.removeObject(p);
	}
	
	@Override
	public boolean consumeResource(ALife life) {
		Point p = life.getPosition();
		if (hasResource(p)) {
			Resource r = (Resource) resourceList.getObject(p);
			resourceList.removeObject(r);
			life.energy += r.getCalories();
			return true;
		}
		life.energy -= 5;
		return false;
	}
	
	@Override
	public boolean hasLife(Point p) {
		if (lifeList.hasObject(p)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addLife(ALife life) {
		if (lifeList.hasObject(life.getPosition())) {
			return false;
		}
		lifeList.addObject(life);
		placeLife(life);
		return true;
	}
	
	@Override
	public boolean removeLife(ALife life) {
		return lifeList.removeObject(life);
	}
	
	@Override
	public boolean removeLife(Point p) {
		return lifeList.removeObject(p);
	}
	
	public Iterator<MapObject> lifeIterator() {
		return lifeList.iterator();
	}
	
	@Override
	public MapObjectMap getLifeList() {
		return lifeList;
	}
	
	@Override
	public MapObjectMap getObstacleList() {
		return obstacleList;
	}
	
	@Override
	public int getTimeFrame() {
		return timeFrameNo;
	}
	
	@Override
	public void incrementTimeFrame() {
		timeFrameNo++;
		for (MapObject mo : lifeList) {
			((ALife) mo).doMove();
			
		}
	}
	
	@Override
	public List<Paintable> getLifePaintables() {
		List<Paintable> paints = new ArrayList<Paintable>();
		
		return paints;
	}
	
	@Override
	public List<Paintable> getObstaclePaintables() {
		List<Paintable> paints = new ArrayList<Paintable>();
		
		return paints;
	}
	
	@Override
	public List<Paintable> getResourcePaintables() {
		List<Paintable> paints = new ArrayList<Paintable>();
		
		return paints;
	}
}
