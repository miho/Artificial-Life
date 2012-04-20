package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>TurnRightCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class TurnRightCommand extends LifeCommand {
	
	/**
	 * <p>Constructor for TurnRightCommand.</p>
	 */
	public TurnRightCommand() {
		super("Turn Right");
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(final Commandable life) {
		life.turnRight();
	}
	
}
