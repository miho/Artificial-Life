package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Brain;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;

public class GPCommandUtil {
	
	public static Point getPositionAhead(Commandable c) {
		if (c.getOrientation() == ORIENTATION.UP) {
			return new Point(c.getX(), c.getY() - Brain.STEP);
		} else if (c.getOrientation() == ORIENTATION.RIGHT) {
			return new Point(c.getX() + Brain.STEP, c.getY());
		} else if (c.getOrientation() == ORIENTATION.DOWN) {
			return new Point(c.getX(), c.getY() + Brain.STEP);
		} else {
			return new Point(c.getX() - Brain.STEP, c.getY());
		}
	}
	
	public static Point getPositionAhead(Commandable c, int steps) {
		if (c.getOrientation() == ORIENTATION.UP) {
			return new Point(c.getX(), c.getY() - Brain.STEP * steps);
		} else if (c.getOrientation() == ORIENTATION.RIGHT) {
			return new Point(c.getX() + Brain.STEP * steps, c.getY());
		} else if (c.getOrientation() == ORIENTATION.DOWN) {
			return new Point(c.getX(), c.getY() + Brain.STEP * steps);
		} else {
			return new Point(c.getX() - Brain.STEP * steps, c.getY());
		}
	}
}
