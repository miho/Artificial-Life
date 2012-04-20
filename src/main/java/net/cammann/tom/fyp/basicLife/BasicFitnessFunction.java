package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractLife;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.apache.log4j.Logger;

/**
 * <p>
 * BasicFitnessFunction class.
 * </p>
 * 
 * @author Tom Cammann
 * @version $Id: $
 */
public class BasicFitnessFunction extends SimpleFitnessFunction {

	static Logger logger = Logger.getLogger(SimpleFitnessFunction.class);

	/**
	 * <p>
	 * Constructor for BasicFitnessFunction.
	 * </p>
	 * 
	 * @param fact
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public BasicFitnessFunction(final EvolutionFactory fact) {
		super(fact);
	}

	/** {@inheritDoc} */
	@Override
	public double computeRawFitness(final ALife life) {
		double fitness = 0;
		if (life.getEnergy() > 0) {
			fitness += ((AbstractLife) life).getEnergy();
		} else {
			// fitness += ((ABug) life).getEnergy();
			fitness += 0;
		}
		logger.trace("Moves: " + life.getMoveCount());
		logger.trace("Raw fit: " + fitness);
		return fitness;

	}
}
