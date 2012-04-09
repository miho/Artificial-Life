package net.cammann.tom.fyp.basicLife;

import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;

public class HeavyAndFewMap extends AbstactMap {
	
	public HeavyAndFewMap(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(this);
		r.setMaxCal(2000);
		r.setMinCal(2000);
		
		for (int i = 0; i < 200; i++) {
			addResource(r.createResource(ResourceType.SIMPLE));
			
		}
		
	}
	
		
}
