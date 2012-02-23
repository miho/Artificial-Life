package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public class MoveTowardsLeft extends LifeCommand {
	
	public MoveTowardsLeft() {
		super("Move Towards Left");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Commandable life) {
		switch (life.getOrientation()) {
			case UP:
				life.turnLeft();
				break;
			case RIGHT:
				life.turnLeft();
				break;
			
			case DOWN:
				life.turnRight();
				break;
			
			case LEFT:

				life.moveForward();
				
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
				
		}
		
	}
	
}
