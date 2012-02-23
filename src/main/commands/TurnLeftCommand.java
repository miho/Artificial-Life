package commands;

import core.Commandable;

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
