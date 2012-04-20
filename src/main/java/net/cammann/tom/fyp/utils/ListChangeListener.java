package net.cammann.tom.fyp.utils;

/**
 * <p>ListChangeListener interface.</p>
 *
 * @author tc
 * @version $Id: $
 */
public interface ListChangeListener {
	/**
	 * <p>dataAdded.</p>
	 *
	 * @param e a {@link net.cammann.tom.fyp.utils.ListChangeEvent} object.
	 */
	public void dataAdded(ListChangeEvent e);
	
	/**
	 * <p>dataRemoved.</p>
	 *
	 * @param e a {@link net.cammann.tom.fyp.utils.ListChangeEvent} object.
	 */
	public void dataRemoved(ListChangeEvent e);
	
	/**
	 * <p>dataChanged.</p>
	 *
	 * @param e a {@link net.cammann.tom.fyp.utils.ListChangeEvent} object.
	 */
	public void dataChanged(ListChangeEvent e);
	
}
