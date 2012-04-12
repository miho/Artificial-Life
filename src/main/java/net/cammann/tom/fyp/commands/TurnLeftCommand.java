package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class TurnLeftCommand extends LifeCommand {
	
	public TurnLeftCommand() {
		super("Turn Left");
	}
	
	@Override
	public void execute(final Commandable life) {
		life.turnLeft();
	}
	
}
