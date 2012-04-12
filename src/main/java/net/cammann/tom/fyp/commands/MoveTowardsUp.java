package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class MoveTowardsUp extends LifeCommand {
	
	public MoveTowardsUp() {
		super("Move Towards Up");
	}
	
	@Override
	public void execute(final Commandable life) {
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
