package symbotes;

import java.util.ArrayList;
import java.util.List;

import org.jgap.IChromosome;

import core.ALife;
import core.EnvironmentMap;
import core.Resource.ResourceType;
import core.SimpleFitnessFunction;

public class SymboticFitnessFunction extends SimpleFitnessFunction {
	
	@Override
	public EnvironmentMap getNewMap() {
		return new SymboticMap(400, 400);
	}
	
	@Override
	public List<ALife> getNewLife(IChromosome chromo, EnvironmentMap map) {
		List<ALife> lifeList = new ArrayList<ALife>();
		Symbote s1 = new Symbote(chromo, map, ResourceType.S1, ResourceType.S2);
		Symbote s2 = new Symbote(chromo, map, ResourceType.S2, ResourceType.S1);
		
		lifeList.add(s1);
		lifeList.add(s2);
		// lifeList.add(s1.clone());
		// lifeList.add(s2.clone());
		// lifeList.add(s1.clone());
		// lifeList.add(s2.clone());
		
		return lifeList;
	}
	
	// @Override
	// protected double evaluate(IChromosome chromo) {
	// double fitness = 0;
	//
	// EnvironmentMap map = getNewMap();
	// SimulationContext sc = new SimulationContext(map);
	//
	// for (ALife i : getNewLife(chromo, map)) {
	//
	// sc.addLife(i);
	// }
	//
	// sc.initSimulation();
	// sc.setVerbosity(0);
	// sc.limitedRun(400);
	//
	// for (ALife life : sc.getLife()) {
	// if (life.getEnergy() > 0) {
	// fitness += ((ABug) life).uniqueMoveCount / 4
	// + (life.getEnergy());
	// } else {
	// fitness += ((ABug) life).uniqueMoveCount / 4;
	//
	// }
	// }
	//
	// return fitness / 4;
	// }
	
}
