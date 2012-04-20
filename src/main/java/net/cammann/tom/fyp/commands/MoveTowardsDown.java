package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>MoveTowardsDown class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveTowardsDown extends LifeCommand {
	
	/**
	 * <p>Constructor for MoveTowardsDown.</p>
	 */
	public MoveTowardsDown() {
		super("Move towards down");
	}
	
	/** {@inheritDoc} */
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
