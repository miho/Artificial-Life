package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class DropResourceCommand extends LifeCommand {
	
	public DropResourceCommand() {
		super("Drop Resource");
	}
	
	@Override
	public void execute(final Commandable life) {
		life.dropResource();
		
	}
}
