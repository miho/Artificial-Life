package net.cammann.tom.fyp.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimulationContext;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class MapPanel extends JPanel {
	
	SimulationContext sc;
	
	public MapPanel(SimulationContext sc) {
		this.sc = sc;
	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.PINK);
		
		g2.setStroke(new BasicStroke(5));
		for (ALife life : sc.getLife()) {
			Line2D lp = null;
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
		
		g2.setColor(Color.GREEN);
		for (Resource i : sc.getMap().getResourceList()) {
			g2.drawImage(i.getImage(), i.getX(), i.getY(), getParent());
		}
		
		for (int i = 0; i < sc.getLife().size(); i++) {
			ALife life = sc.getLife().get(i);
			g2.setColor(Color.BLACK);
			g2.drawImage(life.getImage(), life.getX(), life.getY(), getParent());
			g2.drawString(i + "", life.getX(), life.getY());
			
			g2.drawString("Life Form: " + (i + 1) + " - ",
					sc.getMapWidth() + 30, 60 + i * 50 - 20);
			
			g2.drawString("Current Engery: " + life.getEnergy(),
					sc.getMapWidth() + 30, 75 + i * 50 - 20);
			g2.drawString("Move Count: " + life.MOVE_COUNT,
					sc.getMapWidth() + 30, 90 + i * 50 - 20);
		}
		g2.setColor(Color.BLACK);
		
		g2.drawString("Time: " + sc.getMoveCount(), sc.getMapWidth() + 30, 20);
		
		g2.drawRect(0, 0, sc.getMapWidth() + 10, sc.getMapHeight() + 10);
		
	}
}
