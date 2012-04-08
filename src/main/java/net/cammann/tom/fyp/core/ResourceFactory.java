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
			case SIMPLE:
				int x = r.nextInt((map.getWidth()) / 10) * 10;
				int y = r.nextInt(map.getHeight() / 10) * 10;
				if (x > map.getWidth() || x < 0 || y > map.getHeight() || y < 0) {
					throw new IllegalStateException("Bad resource position X: "
							+ x + "Y: " + y);
					// System.exit(0);
				}
				
				return new Apple(x, y);
			case CARROT:
				throw new IllegalArgumentException("Carrot not implemented yet");
				
			case S1:
				return new SymbResource(r.nextInt((map.getWidth()) / 10) * 10,
						r.nextInt(map.getHeight() / 10) * 10, rt);
				
			case S2:
				return new SymbResource(r.nextInt((map.getWidth()) / 10) * 10,
						r.nextInt(map.getHeight() / 10) * 10, rt);
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
