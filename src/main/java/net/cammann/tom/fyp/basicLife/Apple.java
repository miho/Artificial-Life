package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.SimpleResource;

public class Apple extends SimpleResource {
	
	public Apple(final int x, final int y) {
		super(x, y);
	}
	
	public Apple(final int x, final int y, final int min_cals,
			final int max_cals) {
		super(x, y, min_cals, max_cals);
	}
}
