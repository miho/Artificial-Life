package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class ConsumeCommand extends LifeCommand {
	
	public ConsumeCommand() {
		super("Consume");
	}
	
	@Override
	public void execute(final Commandable life) {
		life.consume();
	}
	
}
