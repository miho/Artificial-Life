package net.cammann.tom.fyp.core;

import java.awt.Point;

import net.cammann.tom.fyp.commands.LifeCommand;

/**
 * <p>Commandable interface.</p>
 *
 * @author tc
 * @version $Id: $
 */
public interface Commandable {
	
	public enum ORIENTATION {
		UP, RIGHT, LEFT, DOWN
	}
	
	/**
	 * <p>moveForward.</p>
	 */
	public void moveForward();
	
	/**
	 * <p>turnLeft.</p>
	 */
	public void turnLeft();
	
	/**
	 * <p>turnRight.</p>
	 */
	public void turnRight();
	
	/**
	 * <p>getOrientation.</p>
	 *
	 * @return a {@link net.cammann.tom.fyp.core.Commandable.ORIENTATION} object.
	 */
	public ORIENTATION getOrientation();
	
	/**
	 * <p>dropResource.</p>
	 *
	 * @return a boolean.
	 */
	public boolean dropResource();
	
	/**
	 * <p>pickUpResource.</p>
	 *
	 * @return a boolean.
	 */
	public boolean pickUpResource();
	
	/**
	 * <p>getCommandList.</p>
	 *
	 * @return an array of {@link net.cammann.tom.fyp.commands.LifeCommand} objects.
	 */
	public LifeCommand[] getCommandList();
	
	/**
	 * <p>consume.</p>
	 *
	 * @return a boolean.
	 */
	public boolean consume();
	
	/**
	 * <p>getPosition.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	public Point getPosition();
	
	/**
	 * <p>getY.</p>
	 *
	 * @return a int.
	 */
	public int getY();
	
	/**
	 * <p>getX.</p>
	 *
	 * @return a int.
	 */
	public int getX();
	
	/**
	 * <p>getMap.</p>
	 *
	 * @return a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 */
	public EnvironmentMap getMap();
}
