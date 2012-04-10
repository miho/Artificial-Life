package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;
import net.cammann.tom.fyp.core.SimpleMap;

public class GPCommandUtil {
	
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
