package net.cammann.tom.fyp.core;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Brain {

	public static final int STEP = 10;

	protected ALife life;

	public Brain(ALife life) {
		this.life = life;

	}

	public abstract int think();

}
