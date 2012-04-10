package net.cammann.tom.fyp.core;

import java.util.Random;

public class ObstacleFactory {
	
	private final EnvironmentMap map;
	private final static Random rand = new Random();
	
	public ObstacleFactory(final EnvironmentMap map) {
		this.map = map;
	}
	
	public Obstacle randomObstacle() {
		return new Obstacle((double) rand.nextInt((map.getWidth())
				/ SimpleMap.STEP_SIZE)
				* SimpleMap.STEP_SIZE, rand.nextInt(map.getHeight()
				/ SimpleMap.STEP_SIZE)
				* SimpleMap.STEP_SIZE, 5);
	}
}
