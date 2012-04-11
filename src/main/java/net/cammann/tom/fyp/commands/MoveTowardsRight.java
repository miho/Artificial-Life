package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class MoveTowardsRight extends LifeCommand {
	
	public MoveTowardsRight() {
		super("Move Towards Right");
	}
	
	@Override
	public void execute(final Commandable life) {
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
