package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory factory;
	static Logger logger = Logger.getLogger(SimpleFitnessFunction.class);
	
	public SimpleFitnessFunction(final EvolutionFactory fact) {
		this.factory = fact;
		
	}
	
	protected double run(final IChromosome chromo) {
		
		double fitness = 0;
		
		final int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		final EnvironmentMap map = factory.createMap();
		final List<ALife> lifeList = new ArrayList<ALife>();
		
		for (int j = 0; j < num_clones; j++) {
			// sc.addLife(fact.createLife(chromo, map));
			final ALife life = factory.createLife(chromo, map);
			lifeList.add(life);
			map.addLife(life);
			logger.trace("Start nrg: " + life.getEnergy());
		}
		
		for (int i = 0; i < factory.getLenOfFitFuncRun(); i++) {
			map.incrementTimeFrame();
		}
		for (final ALife i : lifeList) {
			logger.trace("End nrg: " + i.getEnergy());
		}
		
		for (final ALife life : lifeList) {
			fitness += computeRawFitness(life);
		}
		
		return fitness / lifeList.size();
		
	}
	
	@Override
	protected double evaluate(final IChromosome chromo) {
		
		double fitness = 0;
		
		final int num_runs = factory.getFitnessFunctionRuns();
		for (int i = 0; i < num_runs; i++) {
			fitness += run(chromo);
		}
		logger.trace("Fitness at end of run: " + fitness);
		return fitness / num_runs;
	}
	
	public abstract double computeRawFitness(ALife life);
}
