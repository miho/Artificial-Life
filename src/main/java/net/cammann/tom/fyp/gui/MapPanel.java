package net.cammann.tom.fyp.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.Iterator;

import javax.swing.JPanel;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;

import org.apache.log4j.Logger;

/**
 * <p>
 * MapPanel class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public final class MapPanel extends JPanel {

	private final EnvironmentMap map;

	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(MapPanel.class);

	/**
	 * <p>
	 * Constructor for MapPanel.
	 * </p>
	 * 
	 * @param map
	 *            a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 */
	public MapPanel(final EnvironmentMap map) {
		this.map = map;
	}

	/** {@inheritDoc} */
	@Override
	// CHECKSTYLE.OFF: MagicNumber
	public void paint(final Graphics g) {

		final Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.PINK);

		g2.setStroke(new BasicStroke(5));
		Iterator<ALife> it = map.getLifeIterator();
		while (it.hasNext()) {
			Line2D lp = null;
			final ALife life = it.next();
			for (final Point p : life.getMoveMemory()) {
				Line2D l2 = null;
				if (lp != null) {

					final Point p2 = new Point(p.x + 10 / 2, p.y + 10 / 2);
					l2 = new Line2D.Double(p2, lp.getP1());
					g2.draw(l2);

				} else {
					final Point p2 = new Point(p.x + 10 / 2, p.y + 10 / 2);
					l2 = new Line2D.Double(p2, p2);
				}
				lp = l2;

			}

		}

		g2.setStroke(new BasicStroke(1));

		Iterator<MapObject> iMap = map.getResourceIterator();
		if (iMap.hasNext()) {
			MapObject i = iMap.next();
			for (; iMap.hasNext(); i = iMap.next()) {
				((Resource) i).draw(g2);
			}
		}
		iMap = map.getObstacleIterator();
		if (iMap.hasNext()) {
			MapObject i = iMap.next();
			for (; iMap.hasNext(); i = iMap.next()) {
				((Obstacle) i).draw(g2);
				// logger.info("adding obst");
			}
		}

		int count = 1;
		it = map.getLifeIterator();
		while (it.hasNext()) {
			final ALife life = it.next();
			life.draw(g2);
			g2.setColor(Color.BLACK);

			g2.drawString(count + "", life.getX(), life.getY());

			g2.drawString("Life Form: " + (count) + " - ", map.getWidth() + 30,
					60 + count * 50 - 20);

			g2.drawString("Current Engery: " + life.getEnergy(),
					map.getWidth() + 30, 75 + count * 50 - 20);
			g2.drawString("Move Count: " + life.getMoveCount(),
					map.getWidth() + 30, 90 + count * 50 - 20);
			count++;
		}
		g2.setColor(Color.BLACK);

		g2.drawString("Time: " + map.getTimeFrameNo(), map.getWidth() + 30, 20);

		g2.drawRect(0, 0, map.getWidth() + 10, map.getHeight() + 10);

	}
	// CHECKSTYLE.ON: MagicNumber
}
