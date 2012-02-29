package net.cammann.tom.fyp.gp;

import net.cammann.tom.fyp.basicLife.BasicMap;
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
	
	@Override
	protected double evaluate(final IGPProgram a_subject) {
		return computeRawFitness(a_subject);
	}
	
	public double computeRawFitness(final IGPProgram ind) {
		
		double fitness = 100000;
		
		for (int i = 0; i < factory.getFitnessFunctionRuns(); i++) {
			
			EnvironmentMap map = new BasicMap();
			// EnvironmentMap map = new BasicMap();
			SimulationContext sc = new SimulationContext(map);
			for (int j = 0; j < factory.getNumClones(); j++) {
				sc.addLife(factory.createLife(ind, map));
			}
			sc.initSimulation();
			sc.setVerbosity(0);
			sc.limitedRun(400);
			
			for (ALife life : sc.getLife()) {
				
				fitness -= (life.getEnergy());
				
				// fitness -= ((ABug) life).uniqueMoveCount / 10;
				
			}
		}
		if (fitness > 0) {
			return (fitness);
		}
		return 0;
		
	}
}