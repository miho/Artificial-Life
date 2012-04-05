package net.cammann.tom.fyp.core;

import java.util.Random;

import net.cammann.tom.fyp.basicLife.Apple;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.symbotes.SymbResource;

public class ResourceFactory {
	int max_cals, min_cals;
	private final EnvironmentMap map;
	
	public ResourceFactory(EnvironmentMap map) {
		this.map = map;
	}
	
	public Resource createResource(ResourceType rt) {
		Random r = new Random();
		switch (rt) {
			case APPLE:

				return new Apple(r.nextInt((map.getWidth()) / 10) * 10,
						r.nextInt(map.getHeight() / 10) * 10);
			case CARROT:
				throw new IllegalArgumentException("Carrot not implemented yet");
				
			case S1:
				return new SymbResource(map,
						r.nextInt((map.getWidth()) / 10) * 10, r.nextInt(map
								.getHeight() / 10) * 10, rt);
				
			case S2:
				return new SymbResource(map,
						r.nextInt((map.getWidth()) / 10) * 10, r.nextInt(map
								.getHeight() / 10) * 10, rt);
			default:
				throw new IllegalArgumentException("Bad resource type given");
				
		}
		
	}
	
	public void setMaxCal(int max_cals) {
		this.max_cals = max_cals;
		
	}
	
	public void setMinCal(int min_cal) {
		this.min_cals = min_cal;
	}
}
