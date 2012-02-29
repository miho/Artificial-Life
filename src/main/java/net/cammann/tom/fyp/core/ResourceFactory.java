package net.cammann.tom.fyp.core;

import java.util.Random;

import net.cammann.tom.fyp.basicLife.Apple;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.symbotes.SymbResource;

public class ResourceFactory {
	int x, y;
	int max_cals, min_cals;
	
	public ResourceFactory(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Resource createResource(ResourceType rt) {
		
		switch (rt) {
			case APPLE:
				Random r = new Random();
				return new Apple(r.nextInt(x / 10) * 10, r.nextInt(y / 10) * 10);
			case CARROT:
				throw new IllegalArgumentException("Carrot not created yet");
				
			case S1:
				return new SymbResource(x, y, ResourceType.S1);
				
			case S2:
				return new SymbResource(x, y, ResourceType.S2);
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
