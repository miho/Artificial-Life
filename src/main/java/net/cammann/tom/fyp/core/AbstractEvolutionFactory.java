package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * Skeleton class for producing an Evolution factory
 * 
 * @author TC
 * @version $Id: $
 */
public abstract class AbstractEvolutionFactory implements EvolutionFactory {

	protected int num_fit_func_cycles = 3;
	protected int num_fit_func_alife_clones = 1;
	protected int fit_func_run_len = 400;
	protected int numOfResources = 400;
	protected int numOfObstacles = 0;
	protected int mapWidth = 600;
	protected int mapHeight = 400;

	/** {@inheritDoc} */
	@Override
	public abstract ALife createLife(ALife life, EnvironmentMap map);

	/** {@inheritDoc} */
	@Override
	public abstract ALife createLife(int[] genes, EnvironmentMap map);

	/** {@inheritDoc} */
	@Override
	public abstract ALife createLife(IChromosome chromo, EnvironmentMap map);

	/** {@inheritDoc} */
	@Override
	public abstract ALife createLife(IGPProgram gp, EnvironmentMap map);

	/** {@inheritDoc} */
	@Override
	public abstract EnvironmentMap createMap();

	/** {@inheritDoc} */
	@Override
	public abstract GPFitnessFunction getGPFitnessFunction();

	/** {@inheritDoc} */
	@Override
	public abstract FitnessFunction getFitnessFunction();

	/** {@inheritDoc} */
	@Override
	public final int getLenOfFitFuncRun() {
		return fit_func_run_len;
	}

	/** {@inheritDoc} */
	@Override
	public final int getNumClones() {
		return num_fit_func_alife_clones;
	}

	/** {@inheritDoc} */
	@Override
	public final int getFitnessFunctionRuns() {
		return num_fit_func_cycles;
	}

	/** {@inheritDoc} */
	@Override
	public final int getNumOfResources() {
		return numOfResources;
	}

	/** {@inheritDoc} */
	@Override
	public final void setNumOfResources(final int numOfResources) {
		if (numOfResources < 0) {
			throw new IllegalArgumentException(
					"num resource cannot be less than 0");
		}
		this.numOfResources = numOfResources;
	}

	/** {@inheritDoc} */
	@Override
	public final int getNumOfObstacles() {
		return numOfObstacles;
	}

	/** {@inheritDoc} */
	@Override
	public final void setNumOfObstacles(final int numOfObstacles) {
		if (numOfObstacles < 0) {
			throw new IllegalArgumentException(
					"num obstaces cannot be less than 0");
		}
		this.numOfObstacles = numOfObstacles;
	}

	/** {@inheritDoc} */
	@Override
	public final int getMapWidth() {
		return mapWidth;
	}

	/** {@inheritDoc} */
	@Override
	public final void setMapWidth(final int mapWidth) {
		if (mapWidth < 0) {
			throw new IllegalArgumentException(
					"map width cannot be less than 0");
		}
		this.mapWidth = mapWidth;
	}

	/** {@inheritDoc} */
	@Override
	public final int getMapHeight() {
		return mapHeight;
	}

	/** {@inheritDoc} */
	@Override
	public final void setMapHeight(final int mapHeight) {
		if (mapHeight < 0) {
			throw new IllegalArgumentException(
					"map height cannot be less than 0");
		}
		this.mapHeight = mapHeight;
	}

	@Override
	public void setFitnessFunctionRuns(int r) {
		num_fit_func_cycles = r;
	}

	@Override
	public void setLenOfFitFuncRun(int r) {
		fit_func_run_len = r;
	}

}
