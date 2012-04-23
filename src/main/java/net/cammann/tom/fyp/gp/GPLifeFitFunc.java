package net.cammann.tom.fyp.gp;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;

import org.apache.log4j.Logger;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * <p>
 * GPLifeFitFunc class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class GPLifeFitFunc extends GPFitnessFunction {
	
	/**
	 * Logger.
	 */
	private final Logger logger = Logger.getLogger(GPLifeFitFunc.class);
	
	private final EvolutionFactory factory;
	
	/**
	 * <p>
	 * Constructor for GPLifeFitFunc.
	 * </p>
	 * 
	 * @param factory
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public GPLifeFitFunc(final EvolutionFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * <p>
	 * run.
	 * </p>
	 * 
	 * @param gp
	 *            a {@link org.jgap.gp.IGPProgram} object.
	 * @return a double.
	 */
	public double run(final IGPProgram gp) {
		
		double fitness = 0;
		
		final int num_clones = factory.getNumClones();
		
		// TODO add checks on num_runs and clones
		
		final EnvironmentMap map = factory.createMap();
		final List<ALife> lifeList = new ArrayList<ALife>();
		
		for ( int j = 0 ; j < num_clones ; j++ ) {
			
			final ALife life = factory.createLife(gp, map);
			lifeList.add(life);
			map.placeLife(life);
			if (!map.addLife(life)) {
				logger.error("Failed to add life at pos: " + life.getPosition());
			}
		}
		
		for ( int i = 0 ; i < factory.getLenOfFitFuncRun() ; i++ ) {
			map.incrementTimeFrame();
		}
		
		logger.trace("Life NRG: " + map.getLifeIterator().next().getEnergy());
		
		logger.trace("Finished run: " + map.getTimeFrameNo());
		
		for ( final ALife life : lifeList ) {
			
			final double f = computeRawFitness(life);
			
			if (f > 0) {
				fitness += f;
			} else {
				// fitness += ((ABug) life).getEnergy();
				fitness += 0;
			}
			
		}
		
		return fitness / lifeList.size();
		
	}
	
	/**
	 * {@inheritDoc}.
	 * 
	 * Lower fitness is better here
	 */
	@Override
	protected double evaluate(final IGPProgram gp) {
		// logger.info("Starting eval run");
		double fitness = 0;
		
		final int num_runs = factory.getFitnessFunctionRuns();
		for ( int i = 0 ; i < num_runs ; i++ ) {
			fitness += run(gp);
			
		}
		return fitness / num_runs;
	}
	
	/**
	 * Higher fitness equals fitter here.
	 * 
	 * @param life
	 *            a {@link net.cammann.tom.fyp.core.ALife} object.
	 * @return a double.
	 */
	public double computeRawFitness(final ALife life) {
		return (life.getEnergy());
	}
	
}
