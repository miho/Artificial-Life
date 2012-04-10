package net.cammann.tom.fyp.core;

/**
 * Structure for a resource that can be used on a map.
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Resource extends AbstractMapObject implements Paintable {
	
	/**
	 * Simple enumeation for some resource types.
	 * 
	 * @author TC
	 * 
	 */
	public enum ResourceType {
		/**
		 * simple enum of types.
		 */
		BASIC, CARROT, POISION, TREE, S1, S2
	}
	
	/**
	 * Specifies whether the resource can be picked up or not.
	 * 
	 * Possibly implement this feature in the future.
	 * 
	 * @return true if can be carried.
	 */
	@Beta
	public abstract boolean canBeCarried();
	
	/**
	 * 
	 * @return If the resource is currently being carried true.
	 */
	public abstract boolean isCarried();
	
	/**
	 * 
	 * @param calories
	 *            number of calories contained in resource
	 */
	public abstract void setCalories(int calories);
	
	/**
	 * 
	 * @return calories contained in resouce
	 */
	public abstract double getCalories();
	
	/**
	 * Return type of resource.
	 * 
	 * @return type
	 */
	public abstract ResourceType getResourceType();
}
