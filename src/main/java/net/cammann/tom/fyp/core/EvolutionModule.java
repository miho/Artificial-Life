package net.cammann.tom.fyp.core;

/**
 * <p>
 * EvolutionModule interface.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public interface EvolutionModule {
	
	/**
	 * <p>
	 * getFittestLife.
	 * </p>
	 * 
	 * @return a {@link net.cammann.tom.fyp.core.ALife} object.
	 */
	ALife getFittestLife();
	
	/**
	 * <p>
	 * addEvolutionCycleListener.
	 * </p>
	 * 
	 * @param ecl
	 *            a {@link net.cammann.tom.fyp.core.EvolutionCycleListener}
	 *            object.
	 */
	void addEvolutionCycleListener(final EvolutionCycleListener ecl);
	
	/**
	 * <p>
	 * removeEvolutionCycleListener.
	 * </p>
	 * 
	 * @param ecl
	 *            a {@link net.cammann.tom.fyp.core.EvolutionCycleListener}
	 *            object.
	 */
	void removeEvolutionCycleListener(final EvolutionCycleListener ecl);
	
	/**
	 * <p>
	 * start
	 * </p>
	 * 
	 * Start the Evolutionary Computation.
	 */
	void start();
	
	/**
	 * <p>
	 * getPopulationSize.
	 * </p>
	 * 
	 * @return The starting size of the population for the evolutionary
	 *         computation.
	 */
	int getPopulationSize();
	
	/**
	 * <p>
	 * getNumGenerations.
	 * </p>
	 * 
	 * @return the maximum number of generations the evolutionary computation
	 *         will cycle through.
	 * 
	 */
	int getNumGenerations();
	
	/**
	 * <p>
	 * setMaxGenerations.
	 * </p>
	 * 
	 * Set the maximum number of generations the evolutionary algorithm should
	 * iterate through.
	 * 
	 * @param i
	 *            maxGenerations
	 */
	void setMaxGenerations(int i);
	
	/**
	 * <p>
	 * getPopulationSize.
	 * </p>
	 * 
	 * Gives the size of the starting population in the evolutionary algorithm.
	 * 
	 * @param i
	 *            popsize
	 * 
	 */
	void setPopulationSize(int i);
	
}
