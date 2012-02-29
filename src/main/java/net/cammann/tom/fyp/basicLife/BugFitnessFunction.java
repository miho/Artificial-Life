package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

/**
 * 
 * @author Tom Cammann
 * 
 */
public class BugFitnessFunction extends SimpleFitnessFunction {
	
	public BugFitnessFunction(EvolutionFactory fact) {
		super(fact);
	}
	
	@Override
	public double computeRawFitness(ALife life) {
		double fitness = 0;
		if (life.getEnergy() > 0) {
			fitness += ((ABug) life).uniqueMoveCount / 2 + (life.getEnergy());
		} else {
			fitness += ((ABug) life).uniqueMoveCount;
			
		}
		return fitness;
		
	}
	
}
