package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory fact;
	
	public SimpleFitnessFunction(EvolutionFactory fact) {
		this.fact = fact;
		
	}
	
	@Override
	protected double evaluate(IChromosome chromo) {
		SimulationContext sc = null;
		double fitness = 0;
		
		int num_runs = fact.getFitnessFunctionRuns();
		int num_clones = fact.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		for (int i = 0; i < num_runs; i++) {
			
			EnvironmentMap map = fact.createMap();
			sc = new SimulationContext(map);
			
			for (int j = 0; j < num_clones; j++) {
				sc.addLife(fact.createLife(chromo, map));
			}
			
			sc.initSimulation();
			sc.setVerbosity(0);
			sc.limitedRun(500);
			
			// TODO will not take into account
			for (ALife life : sc.getLife()) {
				if (life.getEnergy() > 0) {
					fitness += ((ABug) life).uniqueMoveCount
							+ (life.getEnergy() / 8);
				} else {
					fitness += ((ABug) life).uniqueMoveCount;
					
				}
			}
			
		}
		return (fitness / sc.getLife().size()) / num_runs;
	}
}
