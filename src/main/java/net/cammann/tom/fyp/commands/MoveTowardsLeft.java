package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class MoveTowardsLeft extends LifeCommand {
	
	public MoveTowardsLeft() {
		super("Move Towards Left");
	}
	
	@Override
	public void execute(final Commandable life) {
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
