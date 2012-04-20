package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>DropResourceCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class DropResourceCommand extends LifeCommand {
	
	/**
	 * <p>Constructor for DropResourceCommand.</p>
	 */
	public DropResourceCommand() {
		super("Drop Resource");
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(final Commandable life) {
		life.dropResource();
		
	}
}
