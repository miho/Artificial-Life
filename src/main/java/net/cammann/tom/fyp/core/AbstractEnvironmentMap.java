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
 * Abstract AbstactMap class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public abstract class AbstractEnvironmentMap implements EnvironmentMap {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(AbstractEnvironmentMap.class);

	/**
	 * Step size.
	 * 
	 */
	public static final int STEP_SIZE = 10;

	/**
	 * height of map.
	 */
	protected int height;
	/**
	 * width of map.
	 */
	protected int width;
	/**
	 * HashMap that holds all resources.
	 */
	protected final MapObjectMap resourceList;
	/**
	 * HashMap that holds all life.
	 */
	protected final MapObjectMap obstacleList;
	/**
	 * List that holds all life.
	 */
	protected final List<ALife> lifeList;
	/**
	 * Records what time frame map is in.
	 */
	private int timeFrameNo = 0;

	/**
	 * <p>
	 * Constructor for AbstactMap.
	 * </p>
	 * 
	 * @param width
	 *            a int.
	 * @param height
	 *            a int.
	 */
	public AbstractEnvironmentMap(final int width, final int height) {
		this.height = height;
		this.width = width;
		resourceList = new MapObjectMap();
		obstacleList = new MapObjectMap();
		lifeList = new ArrayList<ALife>();
		resetMap();
	}

	/** {@inheritDoc} */
	@Override
	public void resetMap() {
		timeFrameNo = 0;
		logger.trace("timeFrameNo: " + getTimeFrameNo());
		resourceList.clear();
		initResources();
		initObstacles();
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
	 * initObstacles
	 * <p>
	 */
	protected abstract void initObstacles();

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
		life.setX(new Random().nextInt((getWidth() + 1) / STEP_SIZE)
				* STEP_SIZE);
		life.setY(new Random().nextInt((getHeight() + 1) / STEP_SIZE)
				* STEP_SIZE);
		life.reset();
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		return width;

	}

	/** {@inheritDoc} */
	@Override
	public final Dimension getDimension() {
		return new Dimension(width, height);

	}

	/** {@inheritDoc} */
	@Override
	public final boolean hasResource(final int x, final int y) {
		return this.hasResource(new Point(x, y));
	}

	/** {@inheritDoc} */
	@Override
	public final boolean hasResource(final Point p) {

		return resourceList.hasObject(p) ? true : false;
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
	public final Iterator<MapObject> getResourceIterator() {
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
	public final boolean validPosition(final Point p) {

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
	public final boolean validPosition(final double x, final double y) {
		final Point p = new Point();
		p.setLocation(x, y);
		return validPosition(p);
	}

	/**
	 * Adds an obstacle to the current map.
	 * 
	 * @param obstacle
	 *            to add
	 * @return whether it has been added to the hashmap or not.
	 */
	protected final boolean addObstacle(final Obstacle obstacle) {
		if (obstacleList.hasObject(obstacle.getPosition())) {
			logger.trace("duplicated obstacle");
			return false;
		}
		if (resourceList.hasObject(obstacle.getPosition())) {
			logger.trace("Resource occupies obstacle position");
			return false;
		}
		// Check in bounds
		if (obstacle.getX() > getWidth() || obstacle.getX() < 0
				|| obstacle.getY() > getHeight() || obstacle.getY() < 0) {
			// logger.trace("Invalid position");
			logger.trace("obstacle out of map");
			return false;
		}
		obstacleList.addObject(obstacle);
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
	public final boolean consumeResource(final ALife life) {
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
	public final boolean hasLife(final Point p) {
		for (final ALife i : lifeList) {
			if (i.getPosition().equals(p)) {
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean addLife(final ALife life) {
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
	public final boolean removeLife(final ALife life) {
		return lifeList.remove(life);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean removeLife(final Point p) {
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
	public final Iterator<ALife> getLifeIterator() {
		return lifeList.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public final Iterator<MapObject> getObstacleIterator() {
		return obstacleList.hashMap.values().iterator();
	}

	/** {@inheritDoc} */
	@Override
	public final int getTimeFrameNo() {
		return timeFrameNo;
	}

	/** {@inheritDoc} */
	@Override
	public final void incrementTimeFrame() {
		timeFrameNo++;
		for (final MapObject mo : lifeList) {
			((ALife) mo).doMove();

		}
	}

	/** {@inheritDoc} */
	@Override
	public final List<Paintable> getLifePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}

	/** {@inheritDoc} */
	@Override
	public final List<Paintable> getObstaclePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}

	/** {@inheritDoc} */
	@Override
	public final List<Paintable> getResourcePaintables() {
		final List<Paintable> paints = new ArrayList<Paintable>();
		// TODO finish
		return paints;
	}
}
