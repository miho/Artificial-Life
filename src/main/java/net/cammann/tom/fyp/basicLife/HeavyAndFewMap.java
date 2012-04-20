package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;

/**
 * <p>
 * HeavyAndFewMap class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class HeavyAndFewMap extends AbstactMap {

	private static final int CALORIE_VALUE = 2000;
	private final int numResources;

	/**
	 * <p>
	 * Constructor for HeavyAndFewMap.
	 * </p>
	 * 
	 * @param width
	 *            a int.
	 * @param height
	 *            a int.
	 * @param numResources
	 *            a int.
	 */
	public HeavyAndFewMap(final int width, final int height,
			final int numResources) {
		super(width, height);
		this.numResources = numResources;
	}

	/** {@inheritDoc} */
	@Override
	public void initResources() {
		final ResourceFactory r = new ResourceFactory(this);
		r.setMaxCalories(CALORIE_VALUE);
		r.setMinCalories(CALORIE_VALUE);

		for (int i = 0; i < numResources; i++) {
			addResource(r.createResource(ResourceType.BASIC));
		}

	}

	/** {@inheritDoc} */
	@Override
	protected void initObstacles() {
		// No obstacles

	}

}
