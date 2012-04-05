package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory factory;
	
	public SimpleFitnessFunction(EvolutionFactory fact) {
		this.factory = fact;
		
	}
	
	protected double run(IChromosome chromo) {
		
		double fitness = 0;
		
		int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		EnvironmentMap map = factory.createMap();
		List<ALife> lifeList = new ArrayList<ALife>();
		
		for (int j = 0; j < num_clones; j++) {
			// sc.addLife(fact.createLife(chromo, map));
			ALife life = factory.createLife(chromo, map);
			lifeList.add(life);
			map.addLife(life);
		}
		
		for (int i = 0; i < factory.getLenOfFitFuncRun(); i++) {
			map.incrementTimeFrame();
		}
		
		for (ALife life : lifeList) {
			fitness += computeRawFitness(life);
		}
		
		return fitness / lifeList.size();
		
	}
	
	@Override
	protected double evaluate(IChromosome chromo) {
		
		double fitness = 0;
		
		int num_runs = factory.getFitnessFunctionRuns();
		for (int i = 0; i < num_runs; i++) {
			fitness += run(chromo);
		}
		return fitness / num_runs;
	}
	
	public abstract double computeRawFitness(ALife life);
}
