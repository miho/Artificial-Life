package net.cammann.tom.fyp.core;

/**
 * <p>Consumer interface.</p>
 *
 * @author tc
 * @version $Id: $
 */
public interface Consumer {
	
	/**
	 * <p>canConsumeResource.</p>
	 *
	 * @param r a {@link net.cammann.tom.fyp.core.Resource} object.
	 * @return a boolean.
	 */
	public boolean canConsumeResource(Resource r);
	
	/**
	 * <p>consumeResource.</p>
	 *
	 * @param r a {@link net.cammann.tom.fyp.core.Resource} object.
	 * @return a boolean.
	 */
	public boolean consumeResource(Resource r);
	
	/**
	 * <p>getEnergy.</p>
	 *
	 * @return a int.
	 */
	public int getEnergy();
}
