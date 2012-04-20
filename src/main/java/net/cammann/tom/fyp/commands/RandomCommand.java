package net.cammann.tom.fyp.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>RandomCommand class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class RandomCommand extends LifeCommand {
	List<LifeCommand> commands = new ArrayList<LifeCommand>();
	
	/**
	 * <p>Constructor for RandomCommand.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param lifeCommands a {@link net.cammann.tom.fyp.commands.LifeCommand} object.
	 */
	public RandomCommand(String name, LifeCommand... lifeCommands) {
		super(name);
		for (LifeCommand i : lifeCommands) {
			this.commands.add(i);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(Commandable life) {
		commands.get(new Random().nextInt(commands.size())).execute(life);
	}
	
}
