package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.List;

/**
 * <p>
 * Abstract ALife class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public interface ALife extends Cloneable, Commandable, Paintable, Consumer,
		MapObject {

	/**
	 * Tells the life to make its move for that turn.
	 * 
	 * Interface for the life to have its 'turn', most likely delegated to a
	 * brain or more complex system.
	 */
	void doMove();

	/**
	 * Sets the new position of the life form to p.
	 * 
	 * @param p
	 *            where to move life form too.
	 */
	void setPosition(Point p);

	/**
	 * Set new x position of life.
	 * 
	 * @param x
	 *            where to set life x position.
	 */
	void setX(int x);

	/**
	 * Set new y position of life.
	 * 
	 * @param y
	 *            where to set life y position
	 */
	void setY(int y);

	/**
	 * <p>
	 * isHoldingResource.
	 * </p>
	 * 
	 * @return true if holding resource
	 */
	@Beta
	boolean isHoldingResource();

	/**
	 * <p>
	 * hasMoveInMemory.
	 * </p>
	 * 
	 * @param position
	 *            to check for in memory
	 * @return true if is in memory. (Memory often has range)
	 */
	boolean hasMoveInMemory(Point position);

	/**
	 * <p>
	 * Getter for the field <code>moveMemory</code>.
	 * </p>
	 * 
	 * @return whole memory list.
	 */
	List<Point> getMoveMemory();

	/**
	 * Append move to memory.
	 * 
	 * If memory is limited could cut off oldest position. Uses queue syste.
	 * FIFO.
	 * 
	 * @param position
	 *            point to add
	 */
	void addMoveToMemory(Point position);

	/**
	 * <p>
	 * getPositionAhead.
	 * </p>
	 * 
	 * @return using orientation get position one step ahead of life
	 */
	Point getPositionAhead();

	/**
	 * <p>
	 * getPositionAhead.
	 * </p>
	 * 
	 * @param steps
	 *            how many steps ahead to get position of
	 * @return using orientation get position 'x' steps ahead
	 */
	Point getPositionAhead(int steps);

	/**
	 * Set the brain!!
	 * 
	 * @param brain
	 *            to set
	 */
	void setBrain(final Brain brain);

	/**
	 * <p>
	 * Getter for the field <code>brain</code>.
	 * </p>
	 * 
	 * @return brain in use.
	 */
	Brain getBrain();

	/**
	 * <p>
	 * getStartEnergy.
	 * </p>
	 * 
	 * @return gives the start energy for the life form
	 */
	int getStartEnergy();

	/**
	 * <p>
	 * getMemoryLength.
	 * </p>
	 * 
	 * @return get memory length. Return -1 if no limit.
	 */
	int getMemoryLength();

	/**
	 * <p>
	 * getGene.
	 * </p>
	 * 
	 * @param gene
	 *            ordinal of gene position
	 * @return value associated with this ordinal value
	 */
	int getGene(final int gene);

	/**
	 * <p>
	 * getGene.
	 * </p>
	 * 
	 * @param gene
	 *            type
	 * @return get value associated with gene type
	 */
	int getGene(final GENE_TYPE gene);

	/**
	 * <p>
	 * Setter for the field <code>energy</code>.
	 * </p>
	 * 
	 * @param energy
	 *            to set
	 */
	void setEnergy(final int energy);

	/**
	 * Saved me time.
	 * 
	 * this.energy -= decrementBy;
	 * 
	 * @param decrementBy
	 *            amount to remove off of energy.
	 */
	void decrementEnegery(final int decrementBy);

	/**
	 * <p>
	 * Getter for the field <code>genes</code>.
	 * </p>
	 * 
	 * @return full copy array of abstracted genes
	 */
	int[] getGenes();

	/**
	 * <p>
	 * Setter for the field <code>orientation</code>.
	 * </p>
	 * 
	 * @param orientation
	 *            set new
	 */
	void setOrientation(final ORIENTATION orientation);

	/**
	 * Alters orienation on map.
	 * 
	 * @param o
	 *            direction to change orientation too.
	 */
	void setOrientation(final int o);

	/**
	 * Reset the life form values, ie memory, energy.
	 * 
	 * Does not reset position.
	 */
	void reset();

	/**
	 * Helper method.
	 * 
	 * energy += i;
	 * 
	 * @param i
	 *            to add on to energy.
	 */
	void incrementEnergy(final int i);

	/**
	 * Allows Alife to be cloned.
	 * 
	 * @return new clone of ALife
	 */
	Object clone();

	/**
	 * Gives the number of moves made by the ALife during this life.
	 * 
	 * @return number of moves made
	 */
	int getMoveCount();

}
