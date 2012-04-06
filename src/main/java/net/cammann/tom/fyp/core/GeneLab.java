package net.cammann.tom.fyp.core;

import java.util.ArrayList;
import java.util.List;

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
public class GeneLab {
	
	private int popSize;
	private int evolutions;
	private int genNum;
	
	private final EvolutionFactory factory;
	private Configuration conf;
	private Genotype population;
	private final List<EvolutionCycleListener> cycleListeners;
	
	public GeneLab(EvolutionFactory factory) {
		this.factory = factory;
		popSize = 1000;
		
		evolutions = 60;
		
		cycleListeners = new ArrayList<EvolutionCycleListener>();
		initConfig();
	}
	
	private void initConfig() {
		
		try {
			conf = new DefaultConfiguration();
			Chromosome chromo = getChromosome();
			conf.setSampleChromosome(chromo);
			
			FitnessFunction ff = factory.getFitnessFunction();
			
			conf.setFitnessFunction(ff);
			
			conf.setPopulationSize(popSize);
			
			population = Genotype.randomInitialGenotype(conf);
			
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPopulationSize(int popSize) {
		this.popSize = popSize;
		try {
			conf.setPopulationSize(popSize);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPopulationSize() {
		return conf.getPopulationSize() == popSize ? popSize : conf
				.getPopulationSize();
	}
	
	private Chromosome getChromosome() {
		
		int RANGE_OF_COMMANDS = factory.nullInstance().getCommandList().length - 1;
		
		try {
			Gene[] genes = new Gene[29];
			
			// START_ENERGY
			genes[0] = new IntegerGene(conf, 0, 200);
			// MEMORY_LENGTH
			genes[1] = new IntegerGene(conf, 5, 15);
			
			genes[2] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			
			genes[3] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			
			genes[4] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			
			genes[5] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			genes[6] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			genes[7] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			genes[8] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			
			genes[9] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			
			for (int i = 10; i < (12 + 17); i++) {
				genes[i] = new IntegerGene(conf, 0, RANGE_OF_COMMANDS);
			}
			
			Chromosome sampleChromosome = new Chromosome(conf, genes);
			
			return sampleChromosome;
		} catch (InvalidConfigurationException e) {
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
	
	public void setEvolutions(int evolutions) {
		this.evolutions = evolutions;
	}
	
	public void addEvolutionCycleListener(EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);
		
	}
	
	public void removeEvolutionCycleListener(EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}
	
	public void start() {
		genNum = 0;
		for (int i = 0; i < evolutions; i++) {
			for (EvolutionCycleListener e : cycleListeners) {
				e.startCycle(new EvolutionCycleEvent(
						population.getPopulation(), genNum));
			}
			
			population.evolve();
			genNum++;
			for (EvolutionCycleListener e : cycleListeners) {
				e.endCycle(new EvolutionCycleEvent(population.getPopulation(),
						genNum));
			}
		}
		
	}
	
}
