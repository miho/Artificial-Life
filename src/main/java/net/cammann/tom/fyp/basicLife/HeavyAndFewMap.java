package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;

public class HeavyAndFewMap extends AbstactMap {
	
	public HeavyAndFewMap(final int width, final int height) {
		super(width, height);
	}
	
	@Override
	public void initResources() {
		final ResourceFactory r = new ResourceFactory(this);
		r.setMaxCal(2000);
		r.setMinCal(2000);
		
		for (int i = 0; i < 200; i++) {
			addResource(r.createResource(ResourceType.SIMPLE));
			
		}
		
	}
	
}
