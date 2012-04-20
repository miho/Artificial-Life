package net.cammann.tom.fyp.commands;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>SubProgram class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class SubProgram extends LifeCommand {
	
	List<LifeCommand> commands = new ArrayList<LifeCommand>();
	
	/**
	 * <p>Constructor for SubProgram.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param lifeCommands a {@link net.cammann.tom.fyp.commands.LifeCommand} object.
	 */
	public SubProgram(String name, LifeCommand... lifeCommands) {
		super(name);
		for (LifeCommand i : lifeCommands) {
			this.commands.add(i);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(Commandable life) {
		for (LifeCommand i : commands) {
			i.execute(life);
		}
		
	}
	
}
