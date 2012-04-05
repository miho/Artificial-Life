package net.cammann.tom.fyp.core;

import java.util.Random;

public class ObstacleFactory {
	
	private final EnvironmentMap map;
	private final static Random rand = new Random();
	
	public ObstacleFactory(EnvironmentMap map) {
		this.map = map;
	}
	
	public Obstacle randomObstacle() {
		return new Obstacle((double) rand.nextInt((map.getWidth()) / 10) * 10,
				rand.nextInt(map.getHeight() / 10) * 10, 5);
	}
}
