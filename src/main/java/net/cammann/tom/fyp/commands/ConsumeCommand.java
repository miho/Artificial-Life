package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class ConsumeCommand extends LifeCommand {
	
	public ConsumeCommand() {
		super("Consume");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		life.consume();
	}
	
}
