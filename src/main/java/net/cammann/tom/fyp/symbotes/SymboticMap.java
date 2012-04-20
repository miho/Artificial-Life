package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.core.AbstractEnvironmentMap;

/**
 * <p>
 * SymboticMap class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class SymboticMap extends AbstractEnvironmentMap {

	/**
	 * <p>
	 * Constructor for SymboticMap.
	 * </p>
	 * 
	 * @param width
	 *            a int.
	 * @param height
	 *            a int.
	 */
	public SymboticMap(final int width, final int height) {
		super(width, height);

	}

	/** {@inheritDoc} */
	@Override
	public void initResources() {
		// DO nothing
		resourceList.clear();
	}

	@Override
	protected void initObstacles() {
		// TODO Auto-generated method stub

	}

}
