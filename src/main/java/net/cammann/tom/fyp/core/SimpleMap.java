package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * <p>
 * Abstract SimpleMap class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public abstract class SimpleMap implements EnvironmentMap {

	/**
	 * Logger.
	 */
	static Logger logger = Logger.getLogger(SimpleMap.class);

	/**
	 * Height of map.
	 */
	protected int height;
	/**
	 * Width of map.
	 */
	protected int width;
	/**
	 * Hashmap of resources.
	 */
	protected final MapObjectMap resourceList;
	/**
	 * Hashmap of obstacles.
	 */
	protected final MapObjectMap obstacleList;
	/**
	 * List of ALife on map.
	 */
	protected final List<ALife> lifeList;
	/**
	 * Current time frame on map.
	 */
	private int timeFrameNo = 0;

	/**
	 * <p>
	 * Constructor for SimpleMap.
	 * </p>
	 * 
	 * @param width
	 *            a int.
	 * @param height
	 *            a int.
	 */
	public SimpleMap(final int width, final int height) {
		this.height = height;
		this.width = width;
		resourceList = new MapObjectMap();
		obstacleList = new MapObjectMap();
		lifeList = new ArrayList<ALife>();

	}

	/** {@inheritDoc} */
	@Override
	public void resetMap() {
		timeFrameNo = 0;
		logger.trace("timeFrameNo: " + getTimeFrameNo());
		resourceList.clear();
		initResources();
		for (final MapObject i : lifeList) {
			final ALife life = (ALife) i;
			placeLife(life);
		}
	}

	/**
	 * <p>
	 * initResources.
	 * </p>
	 */
	protected abstract void initResources();

	/**
	 * <p>
	 * placeLife.
	 * </p>
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	protected void placeLife(final ALife life) {
		// TODO check not on resource
		life.setX(new Random().nextInt((getWidth() + 1) / 10) * 10);
		life.setY(new Random().nextInt((getHeight() + 1) / 10) * 10);
		life.reset();
	}

	/** {@inheritDoc} */
	@Override
	public final int getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public final int getWidth() {
		return width;

	}

	/** {@inheritDoc} */
	@Override
	public final Dimension getDimension() {
		return new Dimension(width, height);

	}

	/** {@inheritDoc} */
	@Override
	public boolean hasResource(final int x, final int y) {
		return this.hasResource(new Point(x, y));
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasResource(final Point p) {

		return resourceList.hasObject(p) ? true : false;
	}

	/**
	 * <p>
	 * removeResource.
	 * </p>
	 * 
	 * @param x
	 *            a int.
	 * @param y
	 *            a int.
	 * @return a boolean.
	 */
	protected boolean removeResource(final int x, final int y) {
		return this.removeResource(new Point(x, y));
	}

	// could change to return resource.
	// only remove one resource (not stacked)

	/**
	 * <p>
	 * removeResource.
	 * </p>
	 * 
	 * @param p
	 *            a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	protected boolean removeResource(final Point p) {
		return resourceList.removeObject(p);
	}

	/**
	 * <p>
	 * addResource.
	 * </p>
	 * 
	 * @param r
	 *            a {@link net.cammann.tom.fyp.core.Resource} object.
	 * @return a boolean.
	 */
	protected boolean addResource(final Resource r) {
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

	/** {@inheritDoc} */
	@Override
	public Iterator<MapObject> getResourceIterator() {
		return resourceList.hashMap.values().iterator();
	}

	/**
	 * <p>
	 * removeResource.
	 * </p>
	 * 
	 * @param r
	 *            a {@link net.cammann.tom.fyp.core.Resource} object.
	 * @return a boolean.
	 */
	protected boolean removeResource(final Resource r) {
		return resourceList.removeObject(r);
	}

	/** {@inheritDoc} */
	@Override
	public boolean validPosition(final Point p) {

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

	/** {@inheritDoc} */
	@Override
	public boolean validPosition(final double x, final double y) {
		final Point p = new Point();
		p.setLocation(x, y);
		return validPosition(p);
	}

	/**
	 * Adds an obstacle to the current map.
	 * 
	 * @param o
	 *            a {@link net.cammann.tom.fyp.core.Obstacle} object.
	 * @return a boolean.
	 */
	protected boolean addObstacle(final Obstacle o) {
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

	/**
	 * <p>
	 * removeObstacle.
	 * </p>
	 * 
	 * @param o
	 *            a {@link net.cammann.tom.fyp.core.Obstacle} object.
	 * @return a boolean.
	 */
	protected boolean removeObstacle(final Obstacle o) {
		return obstacleList.removeObject(o);
	}

	/**
	 * <p>
	 * removeObstacle.
	 * </p>
	 * 
	 * @param p
	 *            a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	protected boolean removeObstacle(final Point p) {
		return obstacleList.removeObject(p);
	}

	/** {@inheritDoc} */
	@Override
	public boolean consumeResource(final ALife life) {
		final Point p = life.getPosition();
		if (hasResource(p)) {
			final Resource r = (Resource) resourceList.getObject(p);
			resourceList.removeObject(r);
			life.energy += r.getCalories();
			return true;
		}
		life.energy -= 5;
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasLife(final Point p) {
		for (final ALife i : lifeList) {
			if (i.getPosition().equals(p)) {
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addLife(final ALife life) {
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

	/** {@inheritDoc} */
	@Override
	public boolean removeLife(final ALife life) {
		return lifeList.remove(life);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeLife(final Point p) {
		ALife tmp = null;
		for (final ALife i : lifeList) {
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

	/** {@inheritDoc} */
	@Override
	public Iterator<ALife> getLifeIterator() {
		return lifeList.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<MapObject> getObstacleIterator() {
		return obstacleList.hashMap.values().iterator();
	}

	/** {@inheritDoc} */
	@Override
	public int getTimeFrameNo() {
		return timeFrameNo;
	}

	/** {@inheritDoc} */
	@Override
	public void incrementTimeFrame() {
		timeFrameNo++;
		for (final MapObject mo : lifeList) {
			((ALife) mo).doMove();

		}
	}

	/** {@inheritDoc} */
	@Override
	public List<Paintable> getLifePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}

	/** {@inheritDoc} */
	@Override
	public List<Paintable> getObstaclePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}

	/** {@inheritDoc} */
	@Override
	public List<Paintable> getResourcePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}
}
