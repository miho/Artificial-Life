package net.cammann.tom.fyp.core;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Brain {
	
	protected ALife life;
	
	public Brain(final ALife life) {
		this.life = life;
		
	}
	
	public abstract int think();
	
}
