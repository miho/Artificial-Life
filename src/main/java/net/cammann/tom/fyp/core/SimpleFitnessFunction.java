package net.cammann.tom.fyp.core;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory fact;
	
	public SimpleFitnessFunction(EvolutionFactory fact) {
		this.fact = fact;
		
	}
	
	protected double run(IChromosome chromo) {
		SimulationContext sc = null;
		double fitness = 0;
		
		int num_clones = fact.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		EnvironmentMap map = fact.createMap();
		sc = new SimulationContext(map);
		
		for (int j = 0; j < num_clones; j++) {
			sc.addLife(fact.createLife(chromo, map));
		}
		
		sc.initSimulation();
		sc.setVerbosity(0);
		sc.limitedRun(fact.getLenOfFitFuncRun());
		
		for (ALife life : sc.getLife()) {
			fitness += computeRawFitness(life);
		}
		
		return fitness / sc.getLife().size();
		
	}
	
	@Override
	protected double evaluate(IChromosome chromo) {
		
		double fitness = 0;
		
		int num_runs = fact.getFitnessFunctionRuns();
		for (int i = 0; i < num_runs; i++) {
			fitness += run(chromo);
		}
		return fitness / num_runs;
	}
	
	public abstract double computeRawFitness(ALife life);
}
