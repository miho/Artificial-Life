package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>ConsumeCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class ConsumeCommand extends LifeCommand {
	
	/**
	 * <p>Constructor for ConsumeCommand.</p>
	 */
	public ConsumeCommand() {
		super("Consume");
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(final Commandable life) {
		life.consume();
	}
	
}
