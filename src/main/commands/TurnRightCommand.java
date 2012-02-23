package commands;

import core.Commandable;

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
