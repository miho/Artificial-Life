package net.cammann.tom.fyp.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.cammann.tom.fyp.core.Commandable;

public class RandomCommand extends LifeCommand {
	List<LifeCommand> commands = new ArrayList<LifeCommand>();
	
	public RandomCommand(String name, LifeCommand... lifeCommands) {
		super(name);
		for (LifeCommand i : lifeCommands) {
			this.commands.add(i);
		}
	}
	
	@Override
	public void execute(Commandable life) {
		commands.get(new Random().nextInt(commands.size())).execute(life);
	}
	
}
