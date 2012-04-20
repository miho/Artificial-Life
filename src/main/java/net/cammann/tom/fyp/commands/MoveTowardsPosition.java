package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

/**
 * <p>MoveTowardsPosition class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class MoveTowardsPosition extends LifeCommand {
	private final int x, y;
	
	/**
	 * <p>Constructor for MoveTowardsPosition.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public MoveTowardsPosition(int x, int y) {
		super("Move to: (" + x + "," + y + ")");
		this.x = x;
		this.y = y;
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute(Commandable life) {
		int dx = x - life.getPosition().x;
		int dy = y - life.getPosition().y;
		
		if (dx == 0 && dy == 0) {
			return;
		} else if (dx == 0) {
			if (dy < 0) {
				new MoveTowardsUp().execute(life);
			} else {
				new MoveTowardsDown().execute(life);
			}
			
		} else if (dy == 0) {
			if (dx < 0) {
				new MoveTowardsLeft().execute(life);
			} else {
				new MoveTowardsRight().execute(life);
			}
		} else {
			if (dy > dx) {
				if (dy < 0) {
					new MoveTowardsUp().execute(life);
				} else {
					new MoveTowardsDown().execute(life);
				}
			} else {
				if (dx < 0) {
					new MoveTowardsLeft().execute(life);
				} else {
					new MoveTowardsRight().execute(life);
				}
			}
		}
		
	}
}
