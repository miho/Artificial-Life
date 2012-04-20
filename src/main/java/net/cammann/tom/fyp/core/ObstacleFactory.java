package net.cammann.tom.fyp.core;

import java.util.Random;

/**
 * <p>ObstacleFactory class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class ObstacleFactory {
	
	private final EnvironmentMap map;
	private final static Random rand = new Random();
	
	/**
	 * <p>Constructor for ObstacleFactory.</p>
	 *
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 */
	public ObstacleFactory(final EnvironmentMap map) {
		this.map = map;
	}
	
	/**
	 * <p>randomObstacle.</p>
	 *
	 * @return a {@link net.cammann.tom.fyp.core.Obstacle} object.
	 */
	public Obstacle randomObstacle() {
		return new Obstacle((double) rand.nextInt((map.getWidth())
				/ AbstractEnvironmentMap.STEP_SIZE)
				* AbstractEnvironmentMap.STEP_SIZE, rand.nextInt(map.getHeight()
				/ AbstractEnvironmentMap.STEP_SIZE)
				* AbstractEnvironmentMap.STEP_SIZE, 5);
	}
}
