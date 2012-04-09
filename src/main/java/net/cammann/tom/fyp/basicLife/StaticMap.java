package net.cammann.tom.fyp.basicLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.AbstactMap;

public class StaticMap extends AbstactMap {
	
	public static List<Resource> RESOURCE_LIST;
	
	private static int x = -1;
	private static int y = -1;
	
	public StaticMap() {
		super(300, 300);
	}
	
	@Override
	public void initResources() {
		if (RESOURCE_LIST == null) {
			RESOURCE_LIST = new ArrayList<Resource>();
			ResourceFactory r = new ResourceFactory(this);
			for (int i = 0; i < 50; i++) {
				RESOURCE_LIST.add(r.createResource(ResourceType.SIMPLE));
			}
		}
		for (Resource r : RESOURCE_LIST) {
			addResource(r);
		}
		
	}
	
	@Override
	public void placeLife(ALife life) {
		if (x == -1) {
			x = new Random().nextInt((life.getMap().getWidth() + 1) / 10) * 10;
			y = new Random().nextInt((life.getMap().getHeight() + 1) / 10) * 10;
		}
		life.setX(x);
		life.setY(y);
	}
}
