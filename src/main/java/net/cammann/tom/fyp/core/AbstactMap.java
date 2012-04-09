package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class AbstactMap implements EnvironmentMap {
	
	static Logger logger = Logger.getLogger(AbstactMap.class);
	protected int height;
	protected int width;
	protected final MapObjectMap resourceList;
	protected final MapObjectMap obstacleList;
	protected final List<ALife> lifeList;
	
	private int timeFrameNo = 0;
	
	public AbstactMap(int width, int height) {
		this.height = height;
		this.width = width;
		resourceList = new MapObjectMap();
		obstacleList = new MapObjectMap();
		lifeList = new ArrayList<ALife>();
		resetMap();
	}
	
	@Override
	public void resetMap() {
		timeFrameNo = 0;
		logger.trace("timeFrameNo: " + getTimeFrameNo());
		resourceList.clear();
		initResources();
		for (MapObject i : lifeList) {
			ALife life = (ALife) i;
			placeLife(life);
		}
	}
	
	protected abstract void initResources();
	
	protected void placeLife(ALife life) {
		// TODO check not on resource
		life.setX(new Random().nextInt((getWidth() + 1) / 10) * 10);
		life.setY(new Random().nextInt((getHeight() + 1) / 10) * 10);
		life.reset();
	}
	
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
	public boolean hasResource(int x, int y) {
		return this.hasResource(new Point(x, y));
	}
	
	@Override
	public boolean hasResource(Point p) {
		
		return resourceList.hasObject(p) ? true : false;
	}
	
	protected boolean removeResource(int x, int y) {
		return this.removeResource(new Point(x, y));
	}
	
	// could change to return resource.
	// only remove one resource (not stacked)
	
	protected boolean removeResource(Point p) {
		return resourceList.removeObject(p);
	}
	
	protected boolean addResource(Resource r) {
		if (obstacleList.hasObject(r.getPosition())) {
			logger.trace("Obstacle occupies obstacle position");
			return false;
		}
		if (resourceList.hasObject(r.getPosition())) {
			logger.trace("duplicated resource");
			return false;
		}
		// Check in bounds
		if (r.getX() > getWidth() || r.getX() < 0 || r.getY() > getHeight()
				|| r.getY() < 0) {
			// logger.trace("Invalid position");
			logger.trace("obstacle out of map");
			return false;
		}
		resourceList.addObject(r);
		return true;
	}
	
	@Override
	public Iterator<MapObject> getResourceIterator() {
		return resourceList.hashMap.values().iterator();
	}
	
	protected boolean removeResource(Resource r) {
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
	
	/**
	 * Adds an obstacle to the current map
	 * 
	 * @param o
	 * @return
	 */
	protected boolean addObstacle(Obstacle o) {
		if (obstacleList.hasObject(o.getPosition())) {
			logger.trace("duplicated obstacle");
			return false;
		}
		if (resourceList.hasObject(o.getPosition())) {
			logger.trace("Resource occupies obstacle position");
			return false;
		}
		// Check in bounds
		if (o.getX() > getWidth() || o.getX() < 0 || o.getY() > getHeight()
				|| o.getY() < 0) {
			// logger.trace("Invalid position");
			logger.trace("obstacle out of map");
			return false;
		}
		obstacleList.addObject(o);
		return true;
		
	}
	
	protected boolean removeObstacle(Obstacle o) {
		return obstacleList.removeObject(o);
	}
	
	protected boolean removeObstacle(Point p) {
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
		for (ALife i : lifeList) {
			if (i.getPosition().equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean addLife(ALife life) {
		if (obstacleList.hasObject(life.getPosition())) {
			logger.trace("obstacle occupies life position");
			return false;
		}
		if (resourceList.hasObject(life.getPosition())) {
			logger.trace("resource occupies life positon");
			// Could accept this, and return true..
			return false;
		}
		// Check in bounds
		if (life.getX() > getWidth() || life.getX() < 0
				|| life.getY() > getHeight() || life.getY() < 0) {
			
			logger.trace("life out of map");
			return false;
		}
		if (hasLife(life.getPosition())) {
			logger.trace("life exist here already");
			return false;
		}
		lifeList.add(life);
		placeLife(life);
		return true;
	}
	
	@Override
	public boolean removeLife(ALife life) {
		return lifeList.remove(life);
	}
	
	@Override
	public boolean removeLife(Point p) {
		ALife tmp = null;
		for (ALife i : lifeList) {
			if (i.getPosition().equals(p)) {
				tmp = i;
			}
		}
		if (tmp == null) {
			return false;
		} else {
			return lifeList.remove(tmp);
		}
	}
	
	@Override
	public Iterator<ALife> getLifeIterator() {
		return lifeList.iterator();
	}
	
	@Override
	public Iterator<MapObject> getObstacleIterator() {
		return obstacleList.hashMap.values().iterator();
	}
	
	@Override
	public int getTimeFrameNo() {
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
		// TODO finish
		return paints;
	}
	
	@Override
	public List<Paintable> getObstaclePaintables() {
		List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}
	
	@Override
	public List<Paintable> getResourcePaintables() {
		List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}
}
