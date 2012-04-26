package net.cammann.tom.fyp.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * <p>Obstacle class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class Obstacle extends AbstractMapObject {

	private static Image img = null;

	/**
	 * <p>Constructor for Obstacle.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @param radius a double.
	 */
	public Obstacle(final Point p, final double radius) {
		setPosition(p);
		this.radius = radius;
	}

	/**
	 * <p>Constructor for Obstacle.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 * @param radius a double.
	 */
	public Obstacle(final double x, final double y, final double radius) {

		setPosition((int) x, (int) y);

		this.radius = radius;
	}

	private Image getObstacleImage() {
		if (img == null) {

			final BufferedImage b2 = new BufferedImage(10, 10,
					BufferedImage.TYPE_INT_ARGB);

			final Graphics2D g2 = b2.createGraphics();
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(0, 0, 10, 10));

			img = b2;
			return img;

		} else {
			return img;
		}

	}

	/**
	 * <p>draw.</p>
	 *
	 * @param g2 a {@link java.awt.Graphics2D} object.
	 */
	public void draw(final Graphics2D g2) {
//		logger.info("Drawing..");
		g2.drawImage(getObstacleImage(), getX(), getY(), null);

	}
}
