package core;

import java.awt.Point;

import commands.LifeCommand;

public interface Commandable {
	public enum ORIENTATION {
		UP, RIGHT, LEFT, DOWN
	}
	
	public void moveForward();
	
	public void turnLeft();
	
	public void turnRight();
	
	public ORIENTATION getOrientation();
	
	public boolean dropResource();
	
	public boolean pickUpResource();
	
	public LifeCommand[] getCommandList();
	
	public boolean consume();
	
	public Point getPosition();
}
