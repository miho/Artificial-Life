package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.utils.Logger;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory factory;
	protected final Logger log = new Logger(this.getClass().getName());
	
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
			log.trace("Start nrg: " + life.getEnergy());
		}
		
		for (int i = 0; i < factory.getLenOfFitFuncRun(); i++) {
			map.incrementTimeFrame();
		}
		for (ALife i : lifeList) {
			log.trace("End nrg: " + i.getEnergy());
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
		log.trace("Fitness at end of run: " + fitness);
		return fitness / num_runs;
	}
	
	public abstract double computeRawFitness(ALife life);
}
