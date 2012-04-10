package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.ObstacleFactory;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;

import org.apache.log4j.Logger;

public class BasicMap extends AbstactMap {
	
	static Logger logger = Logger.getLogger(BasicMap.class);
	private final int numResource;
	private final int numObstacles;
	
	public BasicMap(final int width, final int height, final int numResource,
			final int numObstacles) {
		super(width, height);
		this.numObstacles = numObstacles;
		this.numResource = numResource;
		resetMap();
	}
	
	@Override
	public void initResources() {
		final ResourceFactory r = new ResourceFactory(this);
		for (int i = 0; i < numResource; i++) {
			final Resource res = r.createResource(ResourceType.BASIC);
			if (!addResource(res)) {
				i--;
			}
			logger.trace("Added resource at: " + res.getPosition());
			
		}
		final ObstacleFactory of = new ObstacleFactory(this);
		for (int i = 0; i < numObstacles; i++) {
			final Obstacle o = of.randomObstacle();
			if (!addObstacle(o)) {
				i--;
			}
			logger.trace("Added obstacle at: " + o.getPosition());
		}
		logger.info("Obstacles added: " + this.obstacleList.size());
		logger.info("Resources added: " + this.resourceList.size());
	}
}
