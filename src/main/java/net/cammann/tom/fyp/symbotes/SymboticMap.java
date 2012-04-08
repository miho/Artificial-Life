package net.cammann.tom.fyp.symbotes;

import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.SimpleMap;

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
	
	@Override
	public void placeLife(ALife life) {
		life.setX(new Random().nextInt((life.getMap().getWidth() + 1) / 10) * 10);
		life.setY(new Random().nextInt((life.getMap().getHeight() + 1) / 10) * 10);
		
	}
	
}
