package net.cammann.tom.fyp.core;

import java.awt.Point;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Resource implements Paintable, Consumable, Plottable {
	public enum ResourceType {
		APPLE, CARROT, POISION, TREE, S1, S2
	}
	
	public abstract boolean canBeCarried();
	
	public abstract boolean isCarried();
	
	public abstract int foodChainValue();
	
	public abstract Point getPosition();
	
	public abstract void setCalories(int calories);
	
	public abstract ResourceType getResourceType();
}
