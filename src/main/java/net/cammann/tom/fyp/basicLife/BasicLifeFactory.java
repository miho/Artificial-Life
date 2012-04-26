package net.cammann.tom.fyp.basicLife;

import java.lang.reflect.Field;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEvolutionFactory;
import net.cammann.tom.fyp.core.AbstractLife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.ga.BasicFitnessFunction;
import net.cammann.tom.fyp.ga.BasicLife;
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
 * @version $Id: $
 */
public final class BasicLifeFactory extends AbstractEvolutionFactory {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(BasicLife.class);

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final int[] genes, final EnvironmentMap map) {
		return new BasicLife(genes, map);
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final IChromosome chromo, final EnvironmentMap map) {
		return new BasicLife(chromo, map);
	}

	/** {@inheritDoc} */
	@Override
	public FitnessFunction getFitnessFunction() {
		return new BasicFitnessFunction(this);
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final IGPProgram gp, final EnvironmentMap map) {
		return new ALifeGP(gp, map);
	}

	/** {@inheritDoc} */
	@Override
	public EnvironmentMap createMap() {
		return new BasicMap(mapWidth, mapHeight, numOfResources, numOfObstacles);
	}

	/** {@inheritDoc} */
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		return new GPLifeFitFunc(this);
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final ALife life, final EnvironmentMap map) {
		final ALife secondLife = (ALife) life.clone();
		secondLife.reset();

		try {
			final Field mapField = AbstractLife.class.getDeclaredField("map");
			mapField.setAccessible(true);

			mapField.set(secondLife, map);

		} catch (final Exception e) {
			logger.error(e.getStackTrace(), e);
			return null;
		}

		return secondLife;
	}

	/** {@inheritDoc} */
	@Override
	public ALife nullInstance() {
		return new BasicLife();
	}

}
