package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.basicLife.BugFitnessFunction;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class SymboteFactory implements EvolutionFactory {
	
	private final ResourceType c, d;
	
	public SymboteFactory(ResourceType c, ResourceType d) {
		this.c = c;
		this.d = d;
	}
	
	public SymboteFactory() {
		this.c = ResourceType.S1;
		this.d = ResourceType.S2;
	}
	
	@Override
	public ALife createLife(IChromosome chromo, EnvironmentMap map) {
		return new Symbote(chromo, map, c, d);
	}
	
	@Override
	public ALife createLife(int[] genes, EnvironmentMap map) {
		return new Symbote(genes, map, c, d);
	}
	
	@Override
	public SimpleFitnessFunction getFitnessFunction() {
		return new BugFitnessFunction(this);
	}
	
	@Override
	public ALife nullInstance() {
		return new Symbote();
	}
	
	@Override
	public int getNumClones() {
		return 2;
	}
	
	@Override
	public ALife createLife(IGPProgram gp, EnvironmentMap map) {
		
		return null;
	}
	
	@Override
	public EnvironmentMap createMap() {
		return new SymboticMap(400, 400);
	}
	
	@Override
	public int getFitnessFunctionRuns() {
		return 1;
	}
	
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getLenOfFitFuncRun() {
		return 400;
	}
	
	@Override
	public ALife createLife(ALife life, EnvironmentMap map) {
		ALife second_life = life.clone();
		// TODO NOT FIXED
		return null;
	}
	
}
