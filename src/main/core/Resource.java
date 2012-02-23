package core;

import java.awt.Image;
import java.awt.Point;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public interface Resource {
	public enum ResourceType {
		APPLE, CARROT, POISION, TREE, S1, S2
	}
	
	public boolean canBeCarried();
	
	public boolean isCarried();
	
	public int foodChainValue();
	
	public boolean isConsumable();
	
	public Image getImage();
	
	public Point getPosition();
	
	public void setCalories(int calories);
	
	public int getCalories();
	
	public int getX();
	
	public int getY();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setPosition(Point p);
	
	public ResourceType getResourceType();
}
