package core;

import org.jgap.IChromosome;

public interface LifeFactory {
	
	public ALife newInstance(int[] genes, EnvironmentMap map);
	
	public ALife newInstance(IChromosome chromo, EnvironmentMap map);
	
	public SimpleFitnessFunction getFitnessFunction();
	
	public ALife nullInstance();
}
