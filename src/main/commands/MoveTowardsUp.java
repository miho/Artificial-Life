package commands;

import core.Commandable;

public class MoveTowardsUp extends LifeCommand {
	
	public MoveTowardsUp() {
		super("Move Towards Up");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		switch (life.getOrientation()) {
			case UP:
				life.moveForward();
				break;
			case RIGHT:
				life.turnLeft();
				break;
			
			case DOWN:
				life.turnRight();
				break;
			
			case LEFT:
				life.turnRight();
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
				
		}
		
	}
	
}
