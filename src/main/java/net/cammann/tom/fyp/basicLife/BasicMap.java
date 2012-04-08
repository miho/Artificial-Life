package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.ObstacleFactory;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

public class BasicMap extends SimpleMap {
	
	public BasicMap() {
		super();
	}
	
	public BasicMap(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(this);
		for (int i = 0; i < 200; i++) {
			Resource res = r.createResource(ResourceType.SIMPLE);
			if (res.getX() > getWidth() || res.getX() < 0
					|| res.getY() > getHeight() || res.getY() < 0) {
				throw new IllegalStateException("Bad positon");
			}
			addResource(res);
		}
		ObstacleFactory of = new ObstacleFactory(this);
		for (int i = 0; i < 30; i++) {
			Obstacle o = of.randomObstacle();
			if (!hasResource(o.getPosition())) {
				addObstacle(o);
			} else {
				i--;
			}
		}
		
	}
	
	@Override
	public void resetMap() {
		resourceList.clear();
		initResources();
		for (MapObject i : lifeList) {
			ALife life = (ALife) i;
			placeLife(life);
		}
		timeFrameNo = 0;
		
	}
	
}
