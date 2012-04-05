package net.cammann.tom.fyp.gp;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class GPLifeFitFunc extends GPFitnessFunction {
	
	private final EvolutionFactory factory;
	
	public GPLifeFitFunc(EvolutionFactory factory) {
		this.factory = factory;
	}
	
	public double run(IGPProgram gp) {
		double fitness = 0;
		
		int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		EnvironmentMap map = factory.createMap();
		List<ALife> lifeList = new ArrayList<ALife>();
		
		for (int j = 0; j < num_clones; j++) {
			
			ALife life = factory.createLife(gp, map);
			lifeList.add(life);
			map.addLife(life);
		}
		
		for (int i = 0; i < factory.getLenOfFitFuncRun(); i++) {
			map.incrementTimeFrame();
		}
		
		for (ALife life : lifeList) {
			
			double f = computeRawFitness(life);
			
			fitness += f;
			
		}
		
		return fitness / lifeList.size();
		
	}
	
	/**
	 * Lower fitness is better here
	 */
	@Override
	protected double evaluate(IGPProgram gp) {
		
		double fitness = 50000;
		
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