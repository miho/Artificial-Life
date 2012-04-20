package net.cammann.tom.fyp.utils;

/**
 * <p>ListChangeEvent class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class ListChangeEvent {
	
	private final Object o;
	private final int index;
	
	/**
	 * <p>Constructor for ListChangeEvent.</p>
	 *
	 * @param o a {@link java.lang.Object} object.
	 * @param index a int.
	 */
	public ListChangeEvent(Object o, int index) {
		this.o = o;
		this.index = index;
	}
	
	/**
	 * <p>Getter for the field <code>index</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * <p>getElement.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getElement() {
		return o;
	}
	
}
