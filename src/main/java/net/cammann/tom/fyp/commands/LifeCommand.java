package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>Abstract LifeCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public abstract class LifeCommand {
	
	protected final String name;
	
	/**
	 * <p>Constructor for LifeCommand.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public LifeCommand(String name) {
		this.name = name;
	}
	
	/**
	 * <p>execute.</p>
	 *
	 * @param life a {@link net.cammann.tom.fyp.core.Commandable} object.
	 */
	public abstract void execute(Commandable life);
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}
}
