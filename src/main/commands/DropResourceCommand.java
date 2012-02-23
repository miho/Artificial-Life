package commands;

import core.Commandable;

public class DropResourceCommand extends LifeCommand {
	
	public DropResourceCommand() {
		super("Drop Resource");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		life.dropResource();
		
	}
}
