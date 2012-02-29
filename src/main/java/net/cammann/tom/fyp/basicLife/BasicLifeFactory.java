package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.gp.ALifeGP;
import net.cammann.tom.fyp.gp.GPLifeFitFunc;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class BasicLifeFactory implements EvolutionFactory {
	
	private final int NUM_FIT_FUNC_CYCLES = 2;
	private final int NUM_CLONES = 1;
	
	@Override
	public ALife createLife(int[] genes, EnvironmentMap map) {
		return new BasicLife(genes, map);
	}
	
	@Override
	public ALife createLife(IChromosome chromo, EnvironmentMap map) {
		return new BasicLife(chromo, map);
	}
	
	@Override
	public FitnessFunction getFitnessFunction() {
		return new BugFitnessFunction(this);
	}
	
	@Override
	public ALife nullInstance() {
		return new BasicLife();
	}
	
	@Override
	public ALife createLife(IGPProgram gp, EnvironmentMap map) {
		return new ALifeGP(gp, map);
	}
	
	@Override
	public EnvironmentMap createMap() {
		return new BasicMap();
	}
	
	@Override
	public int getNumClones() {
		return NUM_CLONES;
	}
	
	@Override
	public int getFitnessFunctionRuns() {
		return NUM_FIT_FUNC_CYCLES;
	}
	
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		return new GPLifeFitFunc(this);
	}
}
