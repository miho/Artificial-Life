package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.SimpleResource;

public class Apple extends SimpleResource {
	
	public Apple(int x, int y) {
		super(x, y);
	}
	
	public Apple(int x, int y, int min_cals, int max_cals) {
		super(x, y, min_cals, max_cals);
	}
}
