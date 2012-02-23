package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.LifeFactory;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.jgap.IChromosome;

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
