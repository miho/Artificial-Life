package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>TurnLeftCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class TurnLeftCommand extends LifeCommand {
	
	/**
	 * <p>Constructor for TurnLeftCommand.</p>
	 */
	public TurnLeftCommand() {
		super("Turn Left");
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(final Commandable life) {
		life.turnLeft();
	}
	
}
