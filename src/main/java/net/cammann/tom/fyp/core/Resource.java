package net.cammann.tom.fyp.core;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Resource implements Paintable, MapObject {
	
	public enum ResourceType {
		SIMPLE, CARROT, POISION, TREE, S1, S2
	}
	
	public abstract boolean canBeCarried();
	
	public abstract boolean isCarried();
	
	public abstract int foodChainValue();
	
	public abstract void setCalories(int calories);
	
	public abstract double getCalories();
	
	public abstract ResourceType getResourceType();
}
