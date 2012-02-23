package commands;

import core.Commandable;

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
