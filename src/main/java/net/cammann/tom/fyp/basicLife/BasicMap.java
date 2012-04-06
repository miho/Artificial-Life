package net.cammann.tom.fyp.basicLife;

import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

public class BasicMap extends SimpleMap {
	
	public BasicMap() {
		super();
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(this);
		for (int i = 0; i < 200; i++) {
			Resource res = r.createResource(ResourceType.APPLE);
			if (res.getX() > getWidth() || res.getX() < 0
					|| res.getY() > getHeight() || res.getY() < 0) {
				throw new IllegalStateException("Bad positon");
			}
			addResource(res);
		}
		// ObstacleFactory o = new ObstacleFactory(this);
		// for (int i = 0; i < 30; i++) {
		// addObstacle(o.randomObstacle());
		// }
		
	}
	
	@Override
	public void resetMap() {
		resourceList.clear();
		initResources();
		for (MapObject i : lifeList) {
			ALife life = (ALife) i;
			placeLife(life);
		}
		
	}
	
	@Override
	public void placeLife(ALife life) {
		life.setX(new Random().nextInt((life.getMap().getWidth() + 1) / 10) * 10);
		life.setY(new Random().nextInt((life.getMap().getHeight() + 1) / 10) * 10);
		life.reset();
	}
	
}
