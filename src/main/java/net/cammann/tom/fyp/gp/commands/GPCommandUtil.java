package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;
import net.cammann.tom.fyp.core.SimpleMap;

/**
 * <p>GPCommandUtil class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class GPCommandUtil {
	
	/**
	 * <p>getPositionAhead.</p>
	 *
	 * @param c a {@link net.cammann.tom.fyp.core.Commandable} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point getPositionAhead(final Commandable c) {
		if (c.getOrientation() == ORIENTATION.UP) {
			return new Point(c.getX(), c.getY() - SimpleMap.STEP_SIZE);
		} else if (c.getOrientation() == ORIENTATION.RIGHT) {
			return new Point(c.getX() + SimpleMap.STEP_SIZE, c.getY());
		} else if (c.getOrientation() == ORIENTATION.DOWN) {
			return new Point(c.getX(), c.getY() + SimpleMap.STEP_SIZE);
		} else {
			return new Point(c.getX() - SimpleMap.STEP_SIZE, c.getY());
		}
	}
	
	/**
	 * <p>getPositionAhead.</p>
	 *
	 * @param c a {@link net.cammann.tom.fyp.core.Commandable} object.
	 * @param steps a int.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point getPositionAhead(final Commandable c, final int steps) {
		if (c.getOrientation() == ORIENTATION.UP) {
			return new Point(c.getX(), c.getY() - SimpleMap.STEP_SIZE * steps);
		} else if (c.getOrientation() == ORIENTATION.RIGHT) {
			return new Point(c.getX() + SimpleMap.STEP_SIZE * steps, c.getY());
		} else if (c.getOrientation() == ORIENTATION.DOWN) {
			return new Point(c.getX(), c.getY() + SimpleMap.STEP_SIZE * steps);
		} else {
			return new Point(c.getX() - SimpleMap.STEP_SIZE * steps, c.getY());
		}
	}
}
