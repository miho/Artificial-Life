package net.cammann.tom.fyp.core;

/**
 * High level interface for a consumable object.
 * 
 * @author TC
 * 
 */
public interface Consumable {
	
	/**
	 * Is the object consumable?
	 * 
	 * Currently not used.
	 * 
	 * @return true if consumable.
	 */
	boolean isConsumable();
	
	/**
	 * Number of calories contained in object.
	 * 
	 * @return number of calories
	 */
	int getCalories();
	
	/**
	 * destroy and eat the object.
	 */
	void consume();
}
