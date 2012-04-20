package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>MoveTowardsUp class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveTowardsUp extends LifeCommand {
	
	/**
	 * <p>Constructor for MoveTowardsUp.</p>
	 */
	public MoveTowardsUp() {
		super("Move Towards Up");
	}
	
	/** {@inheritDoc} */
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
