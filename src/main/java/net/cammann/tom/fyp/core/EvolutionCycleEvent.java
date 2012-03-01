package net.cammann.tom.fyp.core;

import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPPopulation;

public class EvolutionCycleEvent {
	
	private Population population = null;
	private GPPopulation gpPopulation = null;
	private final int genNum;
	private boolean isGeneticProgram = true;
	
	public EvolutionCycleEvent(Population population, int genNum) {
		this.population = population;
		
		this.genNum = genNum;
	}
	
	public EvolutionCycleEvent(GPPopulation population, int genNum) {
		this.gpPopulation = population;
		this.genNum = genNum;
		isGeneticProgram = true;
	}
	
	public IChromosome getFittestChromosome() {
		return population.determineFittestChromosome();
	}
	
	public IGPProgram getFittestProgram() {
		return gpPopulation.determineFittestProgram();
		
	}
	
	public boolean isGeneticProgram() {
		return isGeneticProgram;
	}
	
	public boolean isGeneticAlgorithm() {
		return !isGeneticProgram;
	}
	
	public GPPopulation getGPPopulation() {
		return gpPopulation;
	}
	
	public Population getPopulation() {
		return population;
	}
	
	public int getGenerationNum() {
		return genNum;
	}
}
