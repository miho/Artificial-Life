package net.cammann.tom.fyp.gp;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.SimulationContext;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class GPLifeFitFunc extends GPFitnessFunction {
	
	private final EvolutionFactory factory;
	
	public GPLifeFitFunc(EvolutionFactory factory) {
		this.factory = factory;
	}
	
	public double run(IGPProgram gp) {
		SimulationContext sc = null;
		double fitness = 0;
		
		int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		EnvironmentMap map = factory.createMap();
		sc = new SimulationContext(map);
		
		for (int j = 0; j < num_clones; j++) {
			sc.addLife(factory.createLife(gp, map));
		}
		
		sc.initSimulation();
		sc.setVerbosity(0);
		sc.limitedRun(100);
		
		for (ALife life : sc.getLife()) {
			
			double f = computeRawFitness(life);
			
			fitness += f;
			
		}
		
		return fitness / sc.getLife().size();
		
	}
	
	/**
	 * Lower fitness is better here
	 */
	@Override
	protected double evaluate(IGPProgram gp) {
		
		double fitness = 100000;
		
		int num_runs = factory.getFitnessFunctionRuns();
		for (int i = 0; i < num_runs; i++) {
			fitness -= run(gp);
		}
		return fitness / num_runs;
	}
	
	/**
	 * Higher fitness equals fitter here.
	 * 
	 * @param life
	 * @return
	 */
	
	public double computeRawFitness(ALife life) {
		return (life.getEnergy());
	}
	
}