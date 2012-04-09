package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.ObstacleFactory;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

public class BasicMap extends SimpleMap {
	
	private final int numResource;
	private final int numObstacles;
	
	public BasicMap(int width, int height, int numResource, int numObstacles) {
		super(width, height);
		this.numObstacles = numObstacles;
		this.numResource = numResource;
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(this);
		for (int i = 0; i < numResource; i++) {
			Resource res = r.createResource(ResourceType.SIMPLE);
			if (res.getX() > getWidth() || res.getX() < 0
					|| res.getY() > getHeight() || res.getY() < 0) {
				throw new IllegalStateException("Bad positon");
			}
			addResource(res);
		}
		ObstacleFactory of = new ObstacleFactory(this);
		for (int i = 0; i < numObstacles; i++) {
			Obstacle o = of.randomObstacle();
			if (!hasResource(o.getPosition())) {
				addObstacle(o);
			} else {
				i--;
			}
		}
		
	}
	
}
