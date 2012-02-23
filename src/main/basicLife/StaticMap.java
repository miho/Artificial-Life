package basicLife;

import java.util.ArrayList;
import java.util.List;

import core.Resource;
import core.Resource.ResourceType;
import core.ResourceFactory;
import core.SimpleMap;

public class StaticMap extends SimpleMap {
	
	public static List<Resource> RESOURCE_LIST;
	
	@Override
	public void resetMap() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initResources() {
		if (RESOURCE_LIST == null) {
			RESOURCE_LIST = new ArrayList<Resource>();
			ResourceFactory r = new ResourceFactory(getWidth(), getHeight());
			for (int i = 0; i < 200; i++) {
				RESOURCE_LIST.add(r.createResource(ResourceType.APPLE));
			}
		}
		for (Resource r : RESOURCE_LIST) {
			addResource(r);
		}
		
	}
}
