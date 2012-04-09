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
import net.cammann.tom.fyp.core.Resource;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class MapPanel extends JPanel {
	
	EnvironmentMap map;
	
	public MapPanel(EnvironmentMap map) {
		this.map = map;
	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.PINK);
		
		g2.setStroke(new BasicStroke(5));
		Iterator<ALife> it = map.getLifeIterator();
		while (it.hasNext()) {
			Line2D lp = null;
			ALife life = it.next();
			for (Point p : life.getMoveMemory()) {
				Line2D l2 = null;
				if (lp != null) {
					
					Point p2 = new Point(p.x + 10 / 2, p.y + 10 / 2);
					l2 = new Line2D.Double(p2, lp.getP1());
					g2.draw(l2);
					
				} else {
					Point p2 = new Point(p.x + 10 / 2, p.y + 10 / 2);
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
				((Resource) i).draw(g2);
			}
		}
		
		int count = 0;
		it = map.getLifeIterator();
		while (it.hasNext()) {
			ALife life = it.next();
			life.draw(g2);
			g2.setColor(Color.BLACK);
			
			g2.drawString(count + "", life.getX(), life.getY());
			
			g2.drawString("Life Form: " + (count + 1) + " - ",
					map.getWidth() + 30, 60 + count * 50 - 20);
			
			g2.drawString("Current Engery: " + life.getEnergy(),
					map.getWidth() + 30, 75 + count * 50 - 20);
			g2.drawString("Move Count: " + life.MOVE_COUNT,
					map.getWidth() + 30, 90 + count * 50 - 20);
			count++;
		}
		g2.setColor(Color.BLACK);
		
		g2.drawString("Time: " + map.getTimeFrameNo(), map.getWidth() + 30, 20);
		
		g2.drawRect(0, 0, map.getWidth() + 10, map.getHeight() + 10);
		
	}
}
