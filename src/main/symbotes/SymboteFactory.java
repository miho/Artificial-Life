package symbotes;

import org.jgap.IChromosome;

import core.ALife;
import core.EnvironmentMap;
import core.LifeFactory;
import core.Resource.ResourceType;
import core.SimpleFitnessFunction;

public class SymboteFactory implements LifeFactory {
	
	private final ResourceType c, d;
	
	public SymboteFactory(ResourceType c, ResourceType d) {
		this.c = c;
		this.d = d;
	}
	
	@Override
	public ALife newInstance(IChromosome chromo, EnvironmentMap map) {
		return new Symbote(chromo, map, c, d);
	}
	
	@Override
	public ALife newInstance(int[] genes, EnvironmentMap map) {
		return new Symbote(genes, map, c, d);
	}
	
	@Override
	public SimpleFitnessFunction getFitnessFunction() {
		return new SymboticFitnessFunction();
	}
	
	@Override
	public ALife nullInstance() {
		return new Symbote();
	}
	
}
