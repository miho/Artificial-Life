package net.cammann.tom.fyp.core;

import java.util.Random;

import net.cammann.tom.fyp.basicLife.BasicResource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.symbotes.SymbResource;

public final class ResourceFactory {
	
	int max_cals, min_cals;
	private final EnvironmentMap map;
	
	public ResourceFactory(final EnvironmentMap map) {
		this.map = map;
	}
	
	public Resource createResource(final ResourceType rt) {
		final Random r = new Random();
		switch (rt) {
			case BASIC:
				final int x = r.nextInt((map.getWidth()) / SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE;
				final int y = r.nextInt(map.getHeight() / SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE;
				if (x > map.getWidth() || x < 0 || y > map.getHeight() || y < 0) {
					throw new IllegalStateException("Bad resource position X: "
							+ x + "Y: " + y);
					// System.exit(0);
				}
				
				return new BasicResource(x, y);
			case CARROT:
				throw new IllegalArgumentException("Carrot not implemented yet");
				
			case S1:
				return new SymbResource(r.nextInt((map.getWidth())
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, r.nextInt(map.getHeight()
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, rt);
				
			case S2:
				return new SymbResource(r.nextInt((map.getWidth())
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, r.nextInt(map.getHeight()
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, rt);
			default:
				throw new IllegalArgumentException("Bad resource type given");
				
		}
		
	}
	
	public void setMaxCal(final int max_cals) {
		this.max_cals = max_cals;
		
	}
	
	public void setMinCal(final int min_cal) {
		this.min_cals = min_cal;
	}
}
