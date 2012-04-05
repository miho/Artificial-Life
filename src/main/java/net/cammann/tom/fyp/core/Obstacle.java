package net.cammann.tom.fyp.core;

import java.awt.Graphics2D;
import java.awt.Point;

public class Obstacle implements MapObject {
	
	private final Point p;
	
	private final double radius;
	
	public Obstacle(Point p, double radius) {
		this.p = p;
		this.radius = radius;
	}
	
	public Obstacle(double x, double y, double radius) {
		p = new Point();
		p.setLocation(x, y);
		
		this.radius = radius;
	}
	
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
	
}
