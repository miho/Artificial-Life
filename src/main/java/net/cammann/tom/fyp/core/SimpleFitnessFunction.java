package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * <p>
 * Abstract SimpleFitnessFunction class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public abstract class SimpleFitnessFunction extends FitnessFunction {
	
	private final EvolutionFactory factory;
	static Logger logger = Logger.getLogger(SimpleFitnessFunction.class);
	
	/**
	 * <p>
	 * Constructor for SimpleFitnessFunction.
	 * </p>
	 * 
	 * @param fact
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public SimpleFitnessFunction(final EvolutionFactory fact) {
		this.factory = fact;
		
	}
	
	/**
	 * <p>
	 * run.
	 * </p>
	 * 
	 * @param chromo
	 *            a {@link org.jgap.IChromosome} object.
	 * @return a double.
	 */
	protected double run(final IChromosome chromo) {
		
		double fitness = 0;
		
		final int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		final EnvironmentMap map = factory.createMap();
		final List<ALife> lifeList = new ArrayList<ALife>();
		
		for ( int j = 0 ; j < num_clones ; j++ ) {
			// sc.addLife(fact.createLife(chromo, map));
			final ALife life = factory.createLife(chromo, map);
			lifeList.add(life);
			map.placeLife(life);
			int cnt = 0;
			while(!map.addLife(life)) {
				map.placeLife(life);
				cnt++;
				if(cnt > 10)
					logger.info("Failed to add life");
			}
			logger.trace("Start nrg: " + life.getEnergy());
		}
		
		for ( int i = 0 ; i < factory.getLenOfFitFuncRun() ; i++ ) {
			map.incrementTimeFrame();
		}
		for ( final ALife i : lifeList ) {
			logger.trace("End nrg: " + i.getEnergy());
		}
		
		for ( final ALife life : lifeList ) {
			fitness += computeRawFitness(life);
		}
		
		return fitness / lifeList.size();
		
	}
	
	/** {@inheritDoc} */
	@Override
	protected double evaluate(final IChromosome chromo) {
		
		double fitness = 0;
		
		final int num_runs = factory.getFitnessFunctionRuns();
		for ( int i = 0 ; i < num_runs ; i++ ) {
			fitness += run(chromo);
		}
		logger.trace("Fitness at end of run: " + fitness);
		return fitness / num_runs;
	}
	
	/**
	 * <p>
	 * computeRawFitness.
	 * </p>
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @return a double.
	 */
	public abstract double computeRawFitness(ALife life);
}
