package net.cammann.tom.fyp.core;

import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPPopulation;

/**
 * <p>EvolutionCycleEvent class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class EvolutionCycleEvent {
	
	private Population population = null;
	private GPPopulation gpPopulation = null;
	private final int genNum;
	private boolean isGeneticProgram = true;
	
	/**
	 * <p>Constructor for EvolutionCycleEvent.</p>
	 *
	 * @param population a {@link org.jgap.Population} object.
	 * @param genNum a int.
	 */
	public EvolutionCycleEvent(final Population population, final int genNum) {
		this.population = population;
		
		this.genNum = genNum;
	}
	
	/**
	 * <p>Constructor for EvolutionCycleEvent.</p>
	 *
	 * @param population a {@link org.jgap.gp.impl.GPPopulation} object.
	 * @param genNum a int.
	 */
	public EvolutionCycleEvent(final GPPopulation population, final int genNum) {
		this.gpPopulation = population;
		this.genNum = genNum;
		isGeneticProgram = true;
	}
	
	/**
	 * <p>getFittestChromosome.</p>
	 *
	 * @return a {@link org.jgap.IChromosome} object.
	 */
	public IChromosome getFittestChromosome() {
		return population.determineFittestChromosome();
	}
	
	/**
	 * <p>getFittestProgram.</p>
	 *
	 * @return a {@link org.jgap.gp.IGPProgram} object.
	 */
	public IGPProgram getFittestProgram() {
		return gpPopulation.determineFittestProgram();
		
	}
	
	/**
	 * <p>isGeneticProgram.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGeneticProgram() {
		return isGeneticProgram;
	}
	
	/**
	 * <p>isGeneticAlgorithm.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGeneticAlgorithm() {
		return !isGeneticProgram;
	}
	
	/**
	 * <p>getGPPopulation.</p>
	 *
	 * @return a {@link org.jgap.gp.impl.GPPopulation} object.
	 */
	public GPPopulation getGPPopulation() {
		return gpPopulation;
	}
	
	/**
	 * <p>Getter for the field <code>population</code>.</p>
	 *
	 * @return a {@link org.jgap.Population} object.
	 */
	public Population getPopulation() {
		return population;
	}
	
	/**
	 * <p>getGenerationNum.</p>
	 *
	 * @return a int.
	 */
	public int getGenerationNum() {
		return genNum;
	}
}
