package net.cammann.tom.fyp.core;

/**
 * <p>
 * GENE_TYPE class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public enum GENE_TYPE {
	/**
	 * Starting energy for life form.
	 */
	START_ENGERY,
	/**
	 * Length of memory.
	 */
	MEMORY_LENGTH,
	/**
	 * Range life can see food.
	 */
	SEE_FOOD_RANGE,
	/**
	 * Action to take on.
	 */
	SEE_FOOD_ACTION,
	/**
	 * Range that Life can be seen.
	 */
	SEE_LIFE_RANGE,
	/**
	 * What action to take when life is seen.
	 */
	SEE_LIFE_ACTION,
	/**
	 * How far away a wall or obstacle can bee seen.
	 */
	SEE_WALL_RANGE,
	/**
	 * Action to take when obstacle or wall is encountered.
	 */
	SEE_WALL_ACTION,
	/**
	 * How far away food/resource is smelled (or how nearby resource can be when
	 * it is detected).
	 */
	FOOD_NEARBY_RANGE,
	/**
	 * What action to take when food is smelled nearby.
	 */
	FOOD_NEARBY_ACTION,
	/**
	 * What action to take when life form is on top of food resource.
	 */
	ON_FOOD_ACTION

}
