package net.cammann.tom.fyp.core;

import java.awt.Point;

/**
 * Low level object to give position on a map.
 * 
 * @author TC
 * @version $Id: $
 */
public class AbstractMapObject implements MapObject {

	/**
	 * Location of object.
	 */
	private Point p = new Point(0, 0);
	/**
	 * Radius of object.
	 */
	protected double radius = 5;

	/** {@inheritDoc} */
	@Override
	public final int getX() {
		return p.x;
	}

	/** {@inheritDoc} */
	@Override
	public final int getY() {
		return p.y;
	}

	/** {@inheritDoc} */
	@Override
	public final Point getPosition() {
		return new Point(p);
	}

	/** {@inheritDoc} */
	@Override
	public final double getRadius() {
		return radius;
	}

	/**
	 * <p>
	 * setPosition.
	 * </p>
	 * 
	 * @param p
	 *            a {@link java.awt.Point} object.
	 */
	public final void setPosition(final Point p) {
		this.p = new Point(p);
	}

	/**
	 * <p>
	 * setPosition.
	 * </p>
	 * 
	 * @param x
	 *            a int.
	 * @param y
	 *            a int.
	 */
	public final void setPosition(final int x, final int y) {
		p.setLocation(x, y);
	}

	/**
	 * <p>
	 * setX.
	 * </p>
	 * 
	 * @param x
	 *            change position
	 */
	public final void setX(final int x) {
		p.x = x;
	}

	/**
	 * <p>
	 * setY.
	 * </p>
	 * 
	 * @param y
	 *            change position
	 */
	public final void setY(final int y) {
		p.y = y;
	}
}
