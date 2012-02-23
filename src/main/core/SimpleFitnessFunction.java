package core;

import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public abstract class SimpleFitnessFunction extends FitnessFunction {
	public static final int NUM_RUNS = 1;
	
	public abstract EnvironmentMap getNewMap();
	
	public abstract List<ALife> getNewLife(IChromosome chromo,
			EnvironmentMap map);
	
	@SuppressWarnings("unused")
	@Override
	protected double evaluate(IChromosome chromo) {
		SimulationContext sc = null;
		double fitness = 0;
		
		if (NUM_RUNS < 1) {
			
			throw new IllegalArgumentException(
					"NUM RUNS has to be greater than 0");
		}
		
		for (int i = 0; i < NUM_RUNS; i++) {
			EnvironmentMap map = getNewMap();
			sc = new SimulationContext(map);
			
			for (ALife j : getNewLife(chromo, map)) {
				
				sc.addLife(j);
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
		return (fitness / sc.getLife().size()) / NUM_RUNS;
	}
}
