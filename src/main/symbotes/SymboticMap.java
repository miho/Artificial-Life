package symbotes;

import core.SimpleMap;

public class SymboticMap extends SimpleMap {
	
	public SymboticMap(int width, int height) {
		this.height = height;
		this.width = width;
		
	}
	
	@Override
	public void resetMap() {
		resourceList.clear();
	}
	
	@Override
	public void initResources() {
		// DO nothing
		resourceList.clear();
	}
}
