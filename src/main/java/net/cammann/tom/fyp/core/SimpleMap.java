package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class SimpleMap implements EnvironmentMap {
	
	protected int height;
	protected int width;
	protected final HashedResourceMap resourceList;
	protected static final int DEFAULT_WIDTH = 800;
	protected static final int DEFAULT_HEIGHT = 300;
	
	public SimpleMap() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public SimpleMap(int width, int height) {
		this.height = height;
		this.width = width;
		resourceList = new HashedResourceMap();
		initResources();
		
	}
	
	@Override
	public abstract void placeLife(ALife life);
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
		
	}
	
	@Override
	public Dimension getDimension() {
		return new Dimension(width, height);
		
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public boolean hasResource(int x, int y) {
		return this.hasResource(new Point(x, y));
	}
	
	@Override
	public boolean hasResource(Point p) {
		
		return resourceList.hasResource(p) ? true : false;
	}
	
	@Override
	public Resource getResource(Point p) {
		return resourceList.getResource(p);
	}
	
	@Override
	public Resource getResource(int x, int y) {
		Point p = new Point(x, y);
		return this.getResource(p);
	}
	
	@Override
	public boolean removeResource(int x, int y) {
		return this.removeResource(new Point(x, y));
	}
	
	// could change to return resource.
	// only remove one resource (not stacked)
	@Override
	public boolean removeResource(Point p) {
		return resourceList.removeResource(p);
	}
	
	@Override
	public void addResource(Resource r) {
		resourceList.addResource(r);
		
	}
	
	@Override
	public HashedResourceMap getResourceList() {
		return resourceList;
	}
	
	@Override
	public boolean removeResource(Resource r) {
		return resourceList.removeResource(r);
	}
	
}
