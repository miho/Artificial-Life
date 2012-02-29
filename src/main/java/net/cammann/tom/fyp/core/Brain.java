package net.cammann.tom.fyp.core;

import net.cammann.tom.fyp.utils.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Brain {
	
	public static final int STEP = 10;
	
	protected ALife life;
	protected Logger log;
	
	public Brain(ALife life) {
		this.life = life;
		log = new Logger("Mover");
	}
	
	public void setVerbosity(int level) {
		log.setVerbosity(level);
	}
	
	public abstract int think();
	
}
