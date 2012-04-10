package net.cammann.tom.fyp.basicLife;

import java.lang.reflect.Field;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEvolutionFactory;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.gp.ALifeGP;
import net.cammann.tom.fyp.gp.GPLifeFitFunc;

import org.apache.log4j.Logger;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * Factory to produce basic map, basic life and fitness function.
 * 
 * Used by gene lab to create lifes!!
 * 
 * @author TC
 * 
 */
public final class BasicLifeFactory extends AbstractEvolutionFactory {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(BasicLife.class);
	
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
		return new BasicFitnessFunction(this);
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
		final ALife secondLife = life.clone();
		secondLife.reset();
		
		try {
			final Field mapField = ALife.class.getDeclaredField("map");
			mapField.setAccessible(true);
			
			mapField.set(secondLife, map);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.fatal("Nauty reflection failed");
		}
		
		return secondLife;
	}
	
	@Override
	public ALife nullInstance() {
		return new BasicLife();
	}
	
}
