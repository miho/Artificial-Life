package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.List;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class ALife implements Cloneable, Commandable, Paintable,
		MapObject, Consumer {
	
	public int MOVE_COUNT = 0;
	protected int x, y;
	protected int energy;
	protected List<Point> moveMemory;
	protected int[] genes;
	protected Brain brain;
	protected EnvironmentMap map;
	protected Resource holding;
	
	protected ORIENTATION orientation;
	
	/**
	 * Tells the life to make its move for that turn.
	 * 
	 * Interface for the life to have its 'turn', most likely delegated to a
	 * brain or more complex system.
	 * 
	 */
	public abstract void doMove();
	
	public abstract boolean isHoldingResource();
	
	public abstract boolean hasMoveInMemory(Point position);
	
	public abstract void setMoveMemory(List<Point> moveMemory);
	
	public abstract List<Point> getMoveMemory();
	
	public abstract void addMoveToMemory(Point p);
	
	public abstract Point getPositionAhead();
	
	public abstract Point getPositionAhead(int steps);
	
	public void setBrain(final Brain brain) {
		this.brain = brain;
	}
	
	public Brain getBrain() {
		return brain;
	}
	
	public abstract int getMemoryLength();
	
	/**
	 * 
	 * @param gene
	 * @return
	 */
	public int getGene(final int gene) {
		return genes[gene];
	}
	
	/**
	 * @return x
	 */
	@Override
	public int getX() {
		return x;
	}
	
	/**
	 * @return y
	 */
	@Override
	public int getY() {
		return y;
	}
	
	public int getGene(final GENE_TYPE gene) {
		return getGene(gene.ordinal());
	}
	
	/**
	 * @param energy
	 */
	public void setEnergy(final int energy) {
		this.energy = energy;
	}
	
	/**
	 * @return Energy level
	 */
	@Override
	public int getEnergy() {
		return energy;
	}
	
	/**
	 * @return position
	 */
	@Override
	public Point getPosition() {
		
		return new Point(x, y);
	}
	
	// TODO remove?
	public void setPosition(final Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	/**
	 * @param x
	 */
	
	public void setX(final int x) {
		this.x = x;
	}
	
	/**
	 * @param y
	 */
	
	public void setY(final int y) {
		
		this.y = y;
	}
	
	/**
	 * Saved me time.
	 * 
	 * @param decrementBy
	 */
	public void decrementEnegery(final int decrementBy) {
		this.energy -= decrementBy;
		
	}
	
	public int[] getGenes() {
		return genes;
	}
	
	/**
	 * @param orientation
	 */
	public void setOrientation(final ORIENTATION orientation) {
		this.orientation = orientation;
	}
	
	public void setOrientation(final int o) {
		if (o < 0 || o > 3) {
			throw new IllegalArgumentException("Cannot set orientation to: "
					+ o);
		}
		this.orientation = ORIENTATION.values()[o];
	}
	
	/**
	 * @return orientation
	 */
	@Override
	public ORIENTATION getOrientation() {
		return orientation;
	}
	
	/**
	 * Turns the life form left.
	 * 
	 * Will rotate the the life form to the left, respective to where it is
	 * looking forward.
	 * 
	 */
	@Override
	public abstract void turnLeft();
	
	/**
	 * Turns the life form right.
	 * 
	 * Will rotate the the life form to the right, respective to where it is
	 * looking forward.
	 * 
	 */
	@Override
	public abstract void turnRight();
	
	/**
	 * Moves the life form forward.
	 * 
	 * Hopefully in the direction that it is facing.
	 * 
	 * @param map
	 */
	
	public void setMap(final EnvironmentMap map) {
		this.map = map;
	}
	
	@Override
	public EnvironmentMap getMap() {
		return map;
	}
	
	public abstract void reset();
	
	@Override
	public abstract ALife clone();
	
	@Override
	public boolean consume() {
		return map.consumeResource(this);
	}
	
	public void incrementEnergy(final int i) {
		energy += i;
		
	}
}
