package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimpleResource;

public class Apple extends SimpleResource {
	
	public Apple(EnvironmentMap map, int x, int y) {
		super(map, x, y);
	}
	
	public Apple(EnvironmentMap map, int x, int y, int min_cals, int max_cals) {
		super(map, x, y, min_cals, max_cals);
	}
}
