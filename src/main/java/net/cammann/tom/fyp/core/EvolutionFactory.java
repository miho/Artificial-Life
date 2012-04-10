package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public interface EvolutionFactory {
	
	/**
	 * Number of instances of the phenotype to exist inside the simulation
	 * during the fintess function
	 * 
	 * @return
	 */
	public int getNumClones();
	
	public ALife createLife(ALife life, EnvironmentMap map);
	
	public ALife createLife(int[] genes, EnvironmentMap map);
	
	public ALife createLife(IChromosome chromo, EnvironmentMap map);
	
	public ALife createLife(IGPProgram gp, EnvironmentMap map);
	
	public EnvironmentMap createMap();
	
	public GPFitnessFunction getGPFitnessFunction();
	
	public FitnessFunction getFitnessFunction();
	
	/**
	 * Create blank unusable instance of the ALife for cloning or other
	 * 
	 * @return cloneable instance
	 */
	public ALife nullInstance();
	
	/**
	 * Specifies number of runs in fitness function
	 * 
	 * Gives the number of cycles the fitnessfunction should go through before
	 * it returns fitness.
	 * 
	 * @see SimpleFitnessFunction
	 * 
	 * @return
	 */
	public int getFitnessFunctionRuns();
	
	public int getLenOfFitFuncRun();
	
	public int getNumOfResources();
	
	public void setNumOfResources(int numOfResources);
	
	public int getNumOfObstacles();
	
	public void setNumOfObstacles(int numOfObstacles);
	
	public int getMapWidth();
	
	public void setMapWidth(int mapWidth);
	
	public int getMapHeight();
	
	public void setMapHeight(int mapHeight);
}
