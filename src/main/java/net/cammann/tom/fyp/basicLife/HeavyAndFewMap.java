package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;

public class HeavyAndFewMap extends AbstactMap {
	
	private static final int CALORIE_VALUE = 2000;
	private final int numResources;
	
	public HeavyAndFewMap(final int width, final int height,
			final int numResources) {
		super(width, height);
		this.numResources = numResources;
	}
	
	@Override
	public void initResources() {
		final ResourceFactory r = new ResourceFactory(this);
		r.setMaxCal(CALORIE_VALUE);
		r.setMinCal(CALORIE_VALUE);
		
		for (int i = 0; i < numResources; i++) {
			addResource(r.createResource(ResourceType.BASIC));
			
		}
		
	}
	
}
