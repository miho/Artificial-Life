package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

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
