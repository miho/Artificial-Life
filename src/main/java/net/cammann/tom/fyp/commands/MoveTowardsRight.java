package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>MoveTowardsRight class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveTowardsRight extends LifeCommand {
	
	/**
	 * <p>Constructor for MoveTowardsRight.</p>
	 */
	public MoveTowardsRight() {
		super("Move Towards Right");
	}
	
	/** {@inheritDoc} */
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
