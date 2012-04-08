package net.cammann.tom.fyp.core;

import java.awt.Point;

public interface Plottable {
	
	public int getX();
	
	public int getY();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setPosition(Point p);
	
	public Point getPosition();
}