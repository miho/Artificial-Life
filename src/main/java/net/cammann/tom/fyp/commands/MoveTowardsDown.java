package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public final class MoveTowardsDown extends LifeCommand {
	
	public MoveTowardsDown() {
		super("Move towards down");
	}
	
	@Override
	public void execute(final Commandable life) {
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
