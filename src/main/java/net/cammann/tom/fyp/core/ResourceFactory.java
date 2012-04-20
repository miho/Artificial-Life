package net.cammann.tom.fyp.core;

import java.util.Random;

import net.cammann.tom.fyp.basicLife.BasicResource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.symbotes.SymbResource;

/**
 * Factory object for producing resources.
 *
 * @author TC
 * @version $Id: $
 */
public final class ResourceFactory {
	
	/**
	 * Min,max calories to set on resource.
	 */
	private int maxCalories, minCalories;
	
	/**
	 * Map reference for setting position of resource.
	 */
	private final EnvironmentMap map;
	
	/**
	 * Create resource factory for this map size.
	 *
	 * Uses size to initialise position of resource
	 *
	 * @param map
	 *            Map size to use.
	 */
	public ResourceFactory(final EnvironmentMap map) {
		this.map = map;
		this.maxCalories = 40;
		this.minCalories = 30;
	}
	
	/**
	 * Create a resource for the map.
	 *
	 * Does not add to the map, only uses size given by map to initialize
	 * positon of resource.
	 *
	 * @param rt
	 *            resource type to create.
	 * @return new type of resource with random position on map.
	 */
	public Resource createResource(final ResourceType rt) {
		final Random r = new Random();
		switch (rt) {
			case BASIC:
				final int x = r.nextInt((map.getWidth()) / SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE;
				final int y = r.nextInt(map.getHeight() / SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE;
				if (x > map.getWidth() || x < 0 || y > map.getHeight() || y < 0) {
					throw new IllegalStateException("Bad resource position X: "
							+ x + "Y: " + y);
					// System.exit(0);
				}
				
				return new BasicResource(x, y, minCalories, maxCalories);
			case CARROT:
				throw new IllegalArgumentException("Carrot not implemented yet");
				
			case S1:
				return new SymbResource(r.nextInt((map.getWidth())
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, r.nextInt(map.getHeight()
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, rt);
				
			case S2:
				return new SymbResource(r.nextInt((map.getWidth())
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, r.nextInt(map.getHeight()
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE, rt);
			default:
				throw new IllegalArgumentException("Bad resource type given");
				
		}
		
	}
	
	/**
	 * Set minimum calories for resource creation.
	 *
	 * @param maxCals
	 *            to set as max
	 */
	public void setMaxCalories(final int maxCals) {
		this.maxCalories = maxCals;
		
	}
	
	/**
	 * Set minimum calories for resource creation.
	 *
	 * @param minCals
	 *            to set as min
	 */
	public void setMinCalories(final int minCals) {
		if (minCals > maxCalories) {
			throw new IllegalArgumentException(
					"Min calories cannot be set to greater than max calories");
		}
		this.minCalories = minCals;
	}
}
