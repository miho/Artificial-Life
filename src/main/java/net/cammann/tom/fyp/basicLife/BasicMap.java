package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

public class BasicMap extends SimpleMap {
	
	public BasicMap() {
		super();
	}
	
	@Override
	public void initResources() {
		ResourceFactory r = new ResourceFactory(getWidth(), getHeight());
		for (int i = 0; i < 200; i++) {
			addResource(r.createResource(ResourceType.APPLE));
		}
		
	}
	
	@Override
	public void resetMap() {
		resourceList.clear();
		initResources();
		
	}
	
}
