package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>ForwardCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class ForwardCommand extends LifeCommand {
	
	/**
	 * <p>Constructor for ForwardCommand.</p>
	 */
	public ForwardCommand() {
		super("Move Forward");
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(Commandable life) {
		life.moveForward();
	}
}
