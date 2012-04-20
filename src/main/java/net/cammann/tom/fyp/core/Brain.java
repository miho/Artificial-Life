package net.cammann.tom.fyp.core;

/**
 * <p>Abstract Brain class.</p>
 *
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public abstract class Brain {
	
	protected ALife life;
	
	/**
	 * <p>Constructor for Brain.</p>
	 *
	 * @param life a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	public Brain(final ALife life) {
		this.life = life;
		
	}
	
	/**
	 * <p>think.</p>
	 *
	 * @return a int.
	 */
	public abstract int think();
	
}
