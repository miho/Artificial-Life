package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>MoveTowardsLeft class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveTowardsLeft extends LifeCommand {
	
	/**
	 * <p>Constructor for MoveTowardsLeft.</p>
	 */
	public MoveTowardsLeft() {
		super("Move Towards Left");
	}
	
	/** {@inheritDoc} */
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
