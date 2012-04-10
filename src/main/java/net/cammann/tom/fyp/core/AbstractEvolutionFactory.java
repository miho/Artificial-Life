package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * Skeleton class for producing an Evolution factory
 * 
 * @author TC
 * 
 */
public abstract class AbstractEvolutionFactory implements EvolutionFactory {
	
	protected int num_fit_func_cycles = 1;
	protected int num_fit_func_alife_clones = 1;
	protected int fit_func_run_len = 400;
	protected int numOfResources = 100;
	protected int numOfObstacles = 0;
	protected int mapWidth = 400;
	protected int mapHeight = 200;
	
	@Override
	public abstract ALife createLife(ALife life, EnvironmentMap map);
	
	@Override
	public abstract ALife createLife(int[] genes, EnvironmentMap map);
	
	@Override
	public abstract ALife createLife(IChromosome chromo, EnvironmentMap map);
	
	@Override
	public abstract ALife createLife(IGPProgram gp, EnvironmentMap map);
	
	@Override
	public abstract EnvironmentMap createMap();
	
	@Override
	public abstract GPFitnessFunction getGPFitnessFunction();
	
	@Override
	public abstract FitnessFunction getFitnessFunction();
	
	@Override
	public final int getLenOfFitFuncRun() {
		return fit_func_run_len;
	}
	
	@Override
	public final int getNumClones() {
		return num_fit_func_alife_clones;
	}
	
	@Override
	public final int getFitnessFunctionRuns() {
		return num_fit_func_cycles;
	}
	
	@Override
	public final int getNumOfResources() {
		return numOfResources;
	}
	
	@Override
	public final void setNumOfResources(final int numOfResources) {
		if (numOfResources < 0) {
			throw new IllegalArgumentException(
					"num resource cannot be less than 0");
		}
		this.numOfResources = numOfResources;
	}
	
	@Override
	public final int getNumOfObstacles() {
		return numOfObstacles;
	}
	
	@Override
	public final void setNumOfObstacles(final int numOfObstacles) {
		if (numOfObstacles < 0) {
			throw new IllegalArgumentException(
					"num obstaces cannot be less than 0");
		}
		this.numOfObstacles = numOfObstacles;
	}
	
	@Override
	public final int getMapWidth() {
		return mapWidth;
	}
	
	@Override
	public final void setMapWidth(final int mapWidth) {
		if (mapWidth < 0) {
			throw new IllegalArgumentException(
					"map width cannot be less than 0");
		}
		this.mapWidth = mapWidth;
	}
	
	@Override
	public final int getMapHeight() {
		return mapHeight;
	}
	
	@Override
	public final void setMapHeight(final int mapHeight) {
		if (mapHeight < 0) {
			throw new IllegalArgumentException(
					"map height cannot be less than 0");
		}
		this.mapHeight = mapHeight;
	}
	
}
