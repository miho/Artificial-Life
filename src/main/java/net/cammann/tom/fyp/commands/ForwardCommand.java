package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class ForwardCommand extends LifeCommand {
	
	public ForwardCommand() {
		super("Move Forward");
	}
	
	@Override
	public void execute(Commandable life) {
		life.moveForward();
	}
}
