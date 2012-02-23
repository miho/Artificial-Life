package basicLife;

import core.Resource.ResourceType;
import core.ResourceFactory;
import core.SimpleMap;

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
