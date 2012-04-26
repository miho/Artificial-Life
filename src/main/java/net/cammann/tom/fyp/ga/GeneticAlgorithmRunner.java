package net.cammann.tom.fyp.ga;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EvolutionCycleEvent;
import net.cammann.tom.fyp.core.EvolutionCycleListener;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.EvolutionModule;

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
 * <p>
 * GeneLab class.
 * </p>
 * 
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public final class GeneticAlgorithmRunner implements EvolutionModule {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger
			.getLogger(GeneticAlgorithmRunner.class);
	/**
	 * Population size to create in GA.
	 */
	private int popSize;
	/**
	 * Maximum Number of generations to go through in GA.
	 */
	private int generations;
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
	/**
	 * Population object.
	 */
	private Genotype population;
	
	/**
	 * Used to keep evolution cycle listeners.
	 * 
	 * Will fire an event to the listeners when a generation begins or starts.
	 */
	private final List<EvolutionCycleListener> cycleListeners;
	
	/**
	 * <p>
	 * Constructor for GeneLab.
	 * </p>
	 * 
	 * @param factory
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public GeneticAlgorithmRunner(final EvolutionFactory factory) {
		this.factory = factory;
		popSize = 1000;
		
		generations = 60;
		
		cycleListeners = new ArrayList<EvolutionCycleListener>();
		initConfig();
	}
	
	/**
	 * Sets the normal configuration for a GA run.
	 */
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
	 * <p>
	 * setPopulationSize.
	 * </p>
	 * 
	 * @param popSize
	 *            size of the population for GA run.
	 */
	@Override
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
	
	/** {@inheritDoc} */
	@Override
	public int getPopulationSize() {
		return conf.getPopulationSize();
	}
	
	/**
	 * Sets up the genes for the population.
	 * 
	 * @return the Chromosome for the population
	 */
	private Chromosome getChromosome() {
		
		final int rangeOfCommands = factory.nullInstance().getCommandList().length - 1;
		
		logger.info("Functions available: ");
		for ( final LifeCommand i : factory.nullInstance().getCommandList() ) {
			logger.info("Gene: " + i.toString());
		}
		logger.info(factory.nullInstance().getCommandList().length
				+ " function(s)");
		
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
	 * <p>
	 * getBestSolutionSoFar.
	 * </p>
	 * 
	 * @return a {@link org.jgap.IChromosome} object.
	 */
	public IChromosome getBestSolutionSoFar() {
		return population.getFittestChromosome();
	}
	
	/**
	 * <p>
	 * Setter for the field <code>evolutions</code>.
	 * </p>
	 * 
	 * @param evolutions
	 *            a int.
	 */
	@Override
	public void setMaxGenerations(final int evolutions) {
		this.generations = evolutions;
	}
	
	/** {@inheritDoc} */
	@Override
	public void addEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);
		
	}
	
	/** {@inheritDoc} */
	@Override
	public void removeEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}
	
	/** {@inheritDoc} */
	@Override
	public void start() {
		genNum = 0;
		for ( int i = 0 ; i < generations ; i++ ) {
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
	
	/** {@inheritDoc} */
	@Override
	public int getNumGenerations() {
		return generations;
	}
	
}
