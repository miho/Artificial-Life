package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.basicLife.BasicLife;

import org.apache.log4j.Logger;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

/**
 * <p>GeneLab class.</p>
 *
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public final class GeneLab implements EvolutionModule {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(GeneLab.class);
	/**
	 * Population size to create in GA.
	 */
	private int popSize;
	/**
	 * Maximum Number of evolutions to go through in GA.
	 */
	private int evolutions;
	/**
	 * Current generation number in GA.
	 */
	private int genNum;
	/**
	 * Factory to create life and map from.
	 */
	private final EvolutionFactory factory;
	/**
	 * GA JGAP configuration.
	 */
	private Configuration conf;
	private Genotype population;
	private final List<EvolutionCycleListener> cycleListeners;
	
	/**
	 * <p>Constructor for GeneLab.</p>
	 *
	 * @param factory a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public GeneLab(final EvolutionFactory factory) {
		this.factory = factory;
		popSize = 100;
		
		evolutions = 60;
		
		cycleListeners = new ArrayList<EvolutionCycleListener>();
		initConfig();
	}
	
	private void initConfig() {
		
		try {
			conf = new DefaultConfiguration();
			final Chromosome chromo = getChromosome();
			conf.setSampleChromosome(chromo);
			
			final FitnessFunction ff = factory.getFitnessFunction();
			
			conf.setFitnessFunction(ff);
			
			conf.setPopulationSize(popSize);
			
			population = Genotype.randomInitialGenotype(conf);
			
		} catch (final InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <p>setPopulationSize.</p>
	 *
	 * @param popSize a int.
	 */
	public void setPopulationSize(final int popSize) {
		this.popSize = popSize;
		try {
			conf.setPopulationSize(popSize);
		} catch (final InvalidConfigurationException e) {
			logger.fatal("Could not load configuration");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * <p>getPopulationSize.</p>
	 *
	 * @return a int.
	 */
	public int getPopulationSize() {
		return conf.getPopulationSize() == popSize ? popSize : conf
				.getPopulationSize();
	}
	
	private Chromosome getChromosome() {
		
		final int rangeOfCommands = factory.nullInstance().getCommandList().length - 1;
		
		try {
			final Gene[] genes = new Gene[29];
			
			// START_ENERGY
			genes[0] = new IntegerGene(conf, 0, 200);
			// MEMORY_LENGTH
			genes[1] = new IntegerGene(conf, 5, 15);
			
			genes[2] = new IntegerGene(conf, 0, rangeOfCommands);
			
			genes[3] = new IntegerGene(conf, 0, rangeOfCommands);
			
			genes[4] = new IntegerGene(conf, 0, rangeOfCommands);
			
			genes[5] = new IntegerGene(conf, 0, rangeOfCommands);
			genes[6] = new IntegerGene(conf, 0, rangeOfCommands);
			genes[7] = new IntegerGene(conf, 0, rangeOfCommands);
			genes[8] = new IntegerGene(conf, 0, rangeOfCommands);
			
			genes[9] = new IntegerGene(conf, 0, rangeOfCommands);
			
			for ( int i = 10 ; i < (12 + 17) ; i++ ) {
				genes[i] = new IntegerGene(conf, 0, rangeOfCommands);
			}
			
			final Chromosome sampleChromosome = new Chromosome(conf, genes);
			
			return sampleChromosome;
		} catch (final InvalidConfigurationException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Could not configure gene array");
	}
	
	/**
	 * <p>getBestSolutionSoFar.</p>
	 *
	 * @return a {@link org.jgap.IChromosome} object.
	 */
	public IChromosome getBestSolutionSoFar() {
		return population.getFittestChromosome();
	}
	
	/**
	 * <p>Getter for the field <code>evolutions</code>.</p>
	 *
	 * @return a int.
	 */
	public int getEvolutions() {
		return evolutions;
	}
	
	/**
	 * <p>Setter for the field <code>evolutions</code>.</p>
	 *
	 * @param evolutions a int.
	 */
	public void setEvolutions(final int evolutions) {
		this.evolutions = evolutions;
	}
	
	/**
	 * <p>addEvolutionCycleListener.</p>
	 *
	 * @param ecl a {@link net.cammann.tom.fyp.core.EvolutionCycleListener} object.
	 */
	public void addEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);
		
	}
	
	/**
	 * <p>removeEvolutionCycleListener.</p>
	 *
	 * @param ecl a {@link net.cammann.tom.fyp.core.EvolutionCycleListener} object.
	 */
	public void removeEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}
	
	/**
	 * <p>start.</p>
	 */
	public void start() {
		genNum = 0;
		for ( int i = 0 ; i < evolutions ; i++ ) {
			for ( final EvolutionCycleListener e : cycleListeners ) {
				e.startCycle(new EvolutionCycleEvent(
						population.getPopulation(), genNum));
			}
			
			population.evolve();
			genNum++;
			for ( final EvolutionCycleListener e : cycleListeners ) {
				e.endCycle(new EvolutionCycleEvent(population.getPopulation(),
						genNum));
			}
		}
		
	}
	
	/** {@inheritDoc} */
	@Override
	public ALife getFittestLife() {
		if (getBestSolutionSoFar() == null) {
			return null;
		} else {
			return new BasicLife(getBestSolutionSoFar(), null);
		}
	}
	
}
