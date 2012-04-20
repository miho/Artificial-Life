package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * <p>EvolutionFactory interface.</p>
 *
 * @author tc
 * @version $Id: $
 */
public interface EvolutionFactory {
	
	/**
	 * Number of instances of the phenotype to exist inside the simulation
	 * during the fintess function
	 *
	 * @return a int.
	 */
	public int getNumClones();
	
	/**
	 * <p>createLife.</p>
	 *
	 * @param life a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 * @return a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	public ALife createLife(ALife life, EnvironmentMap map);
	
	/**
	 * <p>createLife.</p>
	 *
	 * @param genes an array of int.
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 * @return a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	public ALife createLife(int[] genes, EnvironmentMap map);
	
	/**
	 * <p>createLife.</p>
	 *
	 * @param chromo a {@link org.jgap.IChromosome} object.
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 * @return a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	public ALife createLife(IChromosome chromo, EnvironmentMap map);
	
	/**
	 * <p>createLife.</p>
	 *
	 * @param gp a {@link org.jgap.gp.IGPProgram} object.
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 * @return a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	public ALife createLife(IGPProgram gp, EnvironmentMap map);
	
	/**
	 * <p>createMap.</p>
	 *
	 * @return a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 */
	public EnvironmentMap createMap();
	
	/**
	 * <p>getGPFitnessFunction.</p>
	 *
	 * @return a {@link org.jgap.gp.GPFitnessFunction} object.
	 */
	public GPFitnessFunction getGPFitnessFunction();
	
	/**
	 * <p>getFitnessFunction.</p>
	 *
	 * @return a {@link org.jgap.FitnessFunction} object.
	 */
	public FitnessFunction getFitnessFunction();
	
	/**
	 * Create blank unusable instance of the ALife for cloning or other.
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
	 * @return a int.
	 */
	public int getFitnessFunctionRuns();
	
	/**
	 * <p>getLenOfFitFuncRun.</p>
	 *
	 * @return a int.
	 */
	public int getLenOfFitFuncRun();
	
	/**
	 * <p>getNumOfResources.</p>
	 *
	 * @return a int.
	 */
	public int getNumOfResources();
	
	/**
	 * <p>setNumOfResources.</p>
	 *
	 * @param numOfResources a int.
	 */
	public void setNumOfResources(int numOfResources);
	
	/**
	 * <p>getNumOfObstacles.</p>
	 *
	 * @return a int.
	 */
	public int getNumOfObstacles();
	
	/**
	 * <p>setNumOfObstacles.</p>
	 *
	 * @param numOfObstacles a int.
	 */
	public void setNumOfObstacles(int numOfObstacles);
	
	/**
	 * <p>getMapWidth.</p>
	 *
	 * @return a int.
	 */
	public int getMapWidth();
	
	/**
	 * <p>setMapWidth.</p>
	 *
	 * @param mapWidth a int.
	 */
	public void setMapWidth(int mapWidth);
	
	/**
	 * <p>getMapHeight.</p>
	 *
	 * @return a int.
	 */
	public int getMapHeight();
	
	/**
	 * <p>setMapHeight.</p>
	 *
	 * @param mapHeight a int.
	 */
	public void setMapHeight(int mapHeight);
}
