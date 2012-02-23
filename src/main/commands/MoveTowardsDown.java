package commands;

import core.Commandable;

public class MoveTowardsDown extends LifeCommand {
	
	public MoveTowardsDown() {
		super("Move towards down");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		switch (life.getOrientation()) {
			case UP:
				life.turnLeft();
				break;
			case RIGHT:
				life.turnRight();
				break;
			
			case DOWN:

				life.moveForward();
				
				break;
			case LEFT:
				life.turnLeft();
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
				
		}
		
	}
	
}
