package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 *
 */
public abstract class ALife extends AbstractMapObject implements Cloneable,
		Commandable, Paintable, Consumer {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(ALife.class);

	/**
	 * Move counter. After each move increment.
	 */
	public int moveCount = 0;

	/**
	 * Energy level of life.
	 */
	protected int energy;
	/**
	 * List of previous moves.
	 */
	protected List<Point> moveMemory;
	/**
	 * Extracted gene array.
	 */
	protected int[] genes;
	/**
	 * Brain object, that does thinking for ALife.
	 */
	protected Brain brain;
	/**
	 * Map object for referencing, sight, smell etc.
	 */
	protected EnvironmentMap map;
	/**
	 * Currently not used, could be used to hold a resource and move it.
	 */
	@Beta
	protected Resource holding;
	/**
	 * Orienation of ALife on map. (UP,LEFT,RIGHT,DOWN)
	 *
	 * @see ORIENTATION
	 */
	protected ORIENTATION orientation;

	/**
	 * Tells the life to make its move for that turn.
	 *
	 * Interface for the life to have its 'turn', most likely delegated to a
	 * brain or more complex system.
	 *
	 */
	public abstract void doMove();

	/**
	 *
	 * @return true if holding resource
	 */
	@Beta
	public abstract boolean isHoldingResource();

	/**
	 *
	 * @param position
	 *            to check for in memory
	 * @return true if is in memory. (Memory often has range)
	 */
	public abstract boolean hasMoveInMemory(Point position);

	/**
	 *
	 * @return whole memory list.
	 */
	// TODO should this be abstract
	public abstract List<Point> getMoveMemory();

	/**
	 * Append move to memory.
	 *
	 * If memory is limited could cut off oldest position. Uses queue syste.
	 * FIFO.
	 *
	 * @param position
	 *            point to add
	 */
	public abstract void addMoveToMemory(Point position);

	/**
	 *
	 * @return using orientation get position one step ahead of life
	 */
	public abstract Point getPositionAhead();

	/**
	 *
	 * @param steps
	 *            how many steps ahead to get position of
	 * @return using orientation get position 'x' steps ahead
	 */
	public abstract Point getPositionAhead(int steps);

	/**
	 * Set the brain!!
	 *
	 * @param brain
	 *            to set
	 */
	public final void setBrain(final Brain brain) {
		this.brain = brain;
	}

	/**
	 *
	 * @return brain in use.
	 */
	public final Brain getBrain() {
		return brain;
	}

	/**
	 *
	 * @return gives the start energy for the life form
	 */
	protected abstract int getStartEnergy();

	/**
	 *
	 * @return get memory length. Return -1 if no limit.
	 */
	public abstract int getMemoryLength();

	/**
	 *
	 * @param gene
	 *            ordinal of gene position
	 * @return value associated with this ordinal value
	 */
	public final int getGene(final int gene) {
		return genes[gene];
	}

	/**
	 *
	 * @param gene
	 *            type
	 * @return get value associated with gene type
	 */
	public final int getGene(final GENE_TYPE gene) {
		return getGene(gene.ordinal());
	}

	/**
	 * @param energy
	 *            to set
	 */
	public final void setEnergy(final int energy) {
		this.energy = energy;
	}

	/**
	 * @return Energy level
	 */
	@Override
	public final int getEnergy() {
		return energy;
	}

	/**
	 *
	 * @param p
	 *            point
	 */
	// TODO remove

	/**
	 * Saved me time.
	 *
	 * this.energy -= decrementBy;
	 *
	 * @param decrementBy
	 *            amount to remove off of energy.
	 */
	public final void decrementEnegery(final int decrementBy) {
		this.energy -= decrementBy;

	}

	/**
	 *
	 * @return full array of abstracted genes
	 */
	public final int[] getGenes() {
		return genes;
	}

	/**
	 * @param orientation
	 *            set new
	 */
	public final void setOrientation(final ORIENTATION orientation) {
		this.orientation = orientation;
	}

	/**
	 * Alters orienation on map.
	 *
	 * @param o
	 *            direction to change orientation too.
	 */
	public final void setOrientation(final int o) {
		if (o < 0 || o > ORIENTATION.values().length - 1) {
			throw new IllegalArgumentException("Cannot set orientation to: "
					+ o);
		}
		this.orientation = ORIENTATION.values()[o];
	}

	/**
	 * @return orientation current life facing direction
	 */
	@Override
	public final ORIENTATION getOrientation() {
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

	@Override
	public final EnvironmentMap getMap() {
		return map;
	}

	/**
	 * Reset the life form values, ie memory, energy.
	 *
	 * Does not reset position.
	 */
	public abstract void reset();

	@Override
	public abstract ALife clone();

	@Override
	public final boolean consume() {
		return map.consumeResource(this);
	}

	/**
	 * Helper method.
	 *
	 * energy += i;
	 *
	 * @param i
	 *            to add on to energy.
	 */
	public final void incrementEnergy(final int i) {
		energy += i;

	}

}
