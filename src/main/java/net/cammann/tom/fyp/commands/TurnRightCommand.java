package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class TurnRightCommand extends LifeCommand {
	
	public TurnRightCommand() {
		super("Turn Right");
	}
	
	@Override
	public void execute(final Commandable life) {
		life.turnRight();
	}
	
}
