package net.cammann.tom.fyp.basicLife;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.jgap.IChromosome;

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
