package commands;

import core.Commandable;

public class ForwardCommand extends LifeCommand {
	
	public ForwardCommand() {
		super("Move Forward");
	}
	
	@Override
	public void execute(Commandable life) {
		life.moveForward();
	}
}
