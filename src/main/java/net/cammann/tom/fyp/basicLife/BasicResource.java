package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.SimpleResource;

/**
 * <p>BasicResource class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class BasicResource extends SimpleResource {
	
	/**
	 * <p>Constructor for BasicResource.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public BasicResource(final int x, final int y) {
		super(x, y);
	}
	
	/**
	 * <p>Constructor for BasicResource.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param min_cals a int.
	 * @param max_cals a int.
	 */
	public BasicResource(final int x, final int y, final int min_cals,
			final int max_cals) {
		super(x, y, min_cals, max_cals);
	}
}
