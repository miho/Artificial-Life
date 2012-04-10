package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

import net.cammann.tom.fyp.basicLife.BasicLife;

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
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public final class GeneLab implements EvolutionModule {
	
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
	
	public void setPopulationSize(final int popSize) {
		this.popSize = popSize;
		try {
			conf.setPopulationSize(popSize);
		} catch (final InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public IChromosome getBestSolutionSoFar() {
		return population.getFittestChromosome();
	}
	
	public int getEvolutions() {
		return evolutions;
	}
	
	public void setEvolutions(final int evolutions) {
		this.evolutions = evolutions;
	}
	
	public void addEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);
		
	}
	
	public void removeEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}
	
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
	
	@Override
	public ALife getFittestLife() {
		if (getBestSolutionSoFar() == null) {
			return null;
		} else {
			return new BasicLife(getBestSolutionSoFar(), null);
		}
	}
	
}
