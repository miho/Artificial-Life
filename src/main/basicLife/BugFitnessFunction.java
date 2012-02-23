package basicLife;

import java.util.ArrayList;
import java.util.List;

import org.jgap.IChromosome;

import core.ALife;
import core.EnvironmentMap;
import core.SimpleFitnessFunction;

/**
 * 
 * @author Tom Cammann
 * 
 */
public class BugFitnessFunction extends SimpleFitnessFunction {
	
	@Override
	public EnvironmentMap getNewMap() {
		return new BasicMap();
	}
	
	@Override
	public List<ALife> getNewLife(IChromosome chromo, EnvironmentMap map) {
		List<ALife> lifeList = new ArrayList<ALife>();
		BasicLife base = new BasicLife(chromo, map);
		
		lifeList.add(base);
		// lifeList.add(base.clone());
		// lifeList.add(base.clone());
		// lifeList.add(base.clone());
		
		return lifeList;
	}
}
