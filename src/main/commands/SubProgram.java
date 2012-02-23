package commands;

import java.util.ArrayList;
import java.util.List;

import core.Commandable;

public class SubProgram extends LifeCommand {
	
	List<LifeCommand> commands = new ArrayList<LifeCommand>();
	
	public SubProgram(String name, LifeCommand... lifeCommands) {
		super(name);
		for (LifeCommand i : lifeCommands) {
			this.commands.add(i);
		}
	}
	
	@Override
	public void execute(Commandable life) {
		for (LifeCommand i : commands) {
			i.execute(life);
		}
		
	}
	
}
