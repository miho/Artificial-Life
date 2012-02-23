package basicLife;

import org.jgap.IChromosome;

import core.ALife;
import core.EnvironmentMap;
import core.LifeFactory;
import core.SimpleFitnessFunction;

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
