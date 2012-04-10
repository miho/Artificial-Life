package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.core.AbstactMap;

public class SymboticMap extends AbstactMap {
	
	public SymboticMap(final int width, final int height) {
		super(width, height);
		
	}
	
	@Override
	public void initResources() {
		// DO nothing
		resourceList.clear();
	}
	
}
