package net.cammann.tom.fyp.basicLife;

import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

public class HeavyAndFewMap extends SimpleMap {
	
	@Override
	public void resetMap() {
		resourceList.clear();
		initResources();
		
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(this);
		r.setMaxCal(2000);
		r.setMinCal(2000);
		
		for (int i = 0; i < 200; i++) {
			addResource(r.createResource(ResourceType.APPLE));
			
		}
		
	}
	
	@Override
	public void placeLife(ALife life) {
		life.setX(new Random().nextInt((life.getMap().getWidth() + 1) / 10) * 10);
		life.setY(new Random().nextInt((life.getMap().getHeight() + 1) / 10) * 10);
		
	}
	
}
