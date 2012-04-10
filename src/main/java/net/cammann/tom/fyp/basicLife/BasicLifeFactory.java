package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEvolutionFactory;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.gp.ALifeGP;
import net.cammann.tom.fyp.gp.GPLifeFitFunc;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class BasicLifeFactory extends AbstractEvolutionFactory {
	
	@Override
	public ALife createLife(final int[] genes, final EnvironmentMap map) {
		return new BasicLife(genes, map);
	}
	
	@Override
	public ALife createLife(final IChromosome chromo, final EnvironmentMap map) {
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
	public ALife createLife(final IGPProgram gp, final EnvironmentMap map) {
		return new ALifeGP(gp, map);
	}
	
	@Override
	public EnvironmentMap createMap() {
		return new BasicMap(mapWidth, mapHeight, numOfResources, numOfObstacles);
	}
	
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		return new GPLifeFitFunc(this);
	}
	
	@Override
	public ALife createLife(final ALife life, final EnvironmentMap map) {
		final ALife second_life = life.clone();
		second_life.reset();
		second_life.setMap(map);
		return second_life;
	}
}
