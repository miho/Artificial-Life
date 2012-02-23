package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.LifeFactory;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.jgap.IChromosome;

public class NormalLifeFactory implements LifeFactory {
	
	@Override
	public ALife newInstance(int[] genes, EnvironmentMap map) {
		return new BasicLife(genes, map);
	}
	
	@Override
	public ALife newInstance(IChromosome chromo, EnvironmentMap map) {
		return new BasicLife(chromo, map);
	}
	
	@Override
	public SimpleFitnessFunction getFitnessFunction() {
		// TODO Auto-generated method stub
		return new BugFitnessFunction();
	}
	
	@Override
	public ALife nullInstance() {
		// TODO Auto-generated method stub
		return new BasicLife();
	}
	
}
