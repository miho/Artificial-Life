package net.cammann.tom.fyp.core;

import org.jgap.IChromosome;
import org.jgap.Population;

public class EvolutionCycleEvent {
	
	private final Population population;
	
	private final int genNum;
	
	public EvolutionCycleEvent(Population population, int genNum) {
		this.population = population;
		
		this.genNum = genNum;
	}
	
	public IChromosome getFittestChromosome() {
		return population.determineFittestChromosome();
	}
	
	public Population getPopulation() {
		return population;
	}
	
	public int getGenerationNum() {
		return genNum;
	}
}
