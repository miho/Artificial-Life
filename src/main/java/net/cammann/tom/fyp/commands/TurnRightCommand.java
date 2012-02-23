package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class TurnRightCommand extends LifeCommand {
	
	public TurnRightCommand() {
		super("Turn Right");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		life.turnRight();
	}
	
}
