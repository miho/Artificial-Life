package commands;

import core.Commandable;

public class MoveTowardsRight extends LifeCommand {
	
	public MoveTowardsRight() {
		super("Move Towards Right");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		switch (life.getOrientation()) {
			case UP:
				life.turnRight();
				break;
			case RIGHT:

				life.moveForward();
				
				break;
			
			case DOWN:
				life.turnLeft();
				
				break;
			
			case LEFT:
				life.turnLeft();
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
				
		}
		
	}
}
