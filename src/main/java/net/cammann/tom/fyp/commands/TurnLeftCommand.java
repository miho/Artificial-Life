package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class TurnLeftCommand extends LifeCommand {
	
	public TurnLeftCommand() {
		super("Turn Left");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		life.turnLeft();
	}
	
}
