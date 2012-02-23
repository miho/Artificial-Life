package core;

import org.jgap.IChromosome;
import org.jgap.Population;

public class EvolutionCycleEvent {
	
	private final Population population;
	private final int cycleNo;
	private final int genNum;
	
	public EvolutionCycleEvent(Population population, int cycleNo, int genNum) {
		this.population = population;
		this.cycleNo = cycleNo;
		this.genNum = genNum;
	}
	
	public IChromosome getFittestChromosome() {
		return population.determineFittestChromosome();
	}
	
	public Population getPopulation() {
		return population;
	}
	
	public int getCycleNo() {
		return cycleNo;
	}
	
	public int getGenerationNum() {
		return genNum;
	}
}
