/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package net.cammann.tom.fyp.gp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionCycleEvent;
import net.cammann.tom.fyp.core.EvolutionCycleListener;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.EvolutionModule;
import net.cammann.tom.fyp.gp.commands.Consume;
import net.cammann.tom.fyp.gp.commands.FoodAhead;
import net.cammann.tom.fyp.gp.commands.OnResource;
import net.cammann.tom.fyp.gp.commands.Orientation;
import net.cammann.tom.fyp.gp.commands.SmellResource;
import net.cammann.tom.fyp.gp.commands.WallAhead;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Equals;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.If;
import org.jgap.gp.function.IfElse;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.SubProgram;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.GPPopulation;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.impl.TournamentSelector;
import org.jgap.gp.terminal.Terminal;
import org.jgap.util.NumberKit;
import org.jgap.util.SystemKit;

public class GeneticProgramFrame extends GPProblem implements EvolutionModule {
	
	private transient static Logger LOGGER = Logger
			.getLogger(GeneticProgramFrame.class);
	
	/*
	 * public variables which may be changed by configuration file
	 */
	private IGPProgram fittest;
	// number of variables to use (output variable is excluded)
	private int numInputVariables;
	
	// CHECKSTYLE.OFF: MagicNumber
	// standard GP parameters
	private final int minInitDepth = 2;
	
	private final int maxInitDepth = 10;
	
	private final int populationSize = 1000;
	
	private final int maxCrossoverDepth = 10;
	
	private final int programCreationMaxTries = 5;
	
	private final int numEvolutions = 500;
	
	private final boolean verboseOutput = true;
	
	private final int maxNodes = 3000;
	
	private final double functionProb = 0.8d;
	
	private final float reproductionProb = 0.1f; // float
	
	private float mutationProb = 0.06f; // float
	
	private final double crossoverProb = 0.9d;
	
	private final float dynamizeArityProb = 0.08f; // float
	
	private final double newChromsPercent = 0.1d;
	
	private final int tournamentSelectorSize = 0;
	
	// timing
	private long startTime;
	
	private long endTime;
	
	// if > 0.0d -> stop if the fitness is below or equal
	// this value. TODO!
	private final double stopCriteria = -1.0d;
	
	private final boolean showPopulation = false;
	
	private final boolean showSimiliar = false;
	private final List<EvolutionCycleListener> cycleListeners;
	
	// CHECKSTYLE.ON: MagicNumber
	
	public void addEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);
		
	}
	
	public void removeEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}
	
	private final EvolutionFactory factory;
	
	public GeneticProgramFrame(final EvolutionFactory factory) {
		cycleListeners = new ArrayList<EvolutionCycleListener>();
		this.factory = factory;
	}
	
	public static void main(final String args[]) {
		
		final EvolutionFactory factory = new BasicLifeFactory();
		
		final GeneticProgramFrame gpf = new GeneticProgramFrame(factory);
		
		final BestLifeLauncher launcherFrame = new BestLifeLauncher(gpf,
				factory);
		launcherFrame.createAndShowGui();
		
		final StatsPackage stats = new StatsPackage();
		
		gpf.addEvolutionCycleListener(new EvolutionCycleListener() {
			
			@Override
			public void startCycle(final EvolutionCycleEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endCycle(final EvolutionCycleEvent e) {
				stats.add(e.getGPPopulation(), e.getGenerationNum());
				
			}
		});
		
		stats.startFitnessGraph();
		
		gpf.run();
	}
	
	/**
	 * This method is used for setting up the commands and terminals that can be
	 * used to solve the problem.
	 * 
	 * @return GPGenotype
	 * @throws InvalidConfigurationException
	 */
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		final GPConfiguration conf = new GPConfiguration();
		setGPConfiguration(conf);
		initConfig(conf);
		Class<?>[] types;
		Class<?>[][] argTypes;
		
		types = new Class[] { CommandGene.DoubleClass };
		argTypes = new Class[][] { {} };
		
		// Next, we define the set of available GP commands and terminals to
		// use.
		// Please see package org.jgap.gp.function and org.jgap.gp.terminal
		// You can easily add commands and terminals of your own.
		// ----------------------------------------------------------------------
		
		final CommandGene[] commands = {
				new Consume(conf, CommandGene.DoubleClass),
				// new MoveForward(conf, CommandGene.DoubleClass),
				// new TurnLeft(conf, CommandGene.DoubleClass),
				// new TurnRight(conf, CommandGene.DoubleClass),
				new Add(conf, CommandGene.DoubleClass),
				new Subtract(conf, CommandGene.DoubleClass),
				new Multiply(conf, CommandGene.DoubleClass),
				new OnResource(conf, CommandGene.DoubleClass),
				new If(conf, CommandGene.DoubleClass),
				new IfElse(conf, CommandGene.DoubleClass),
				new LesserThan(conf, CommandGene.DoubleClass),
				new GreaterThan(conf, CommandGene.DoubleClass),
				new Terminal(conf, CommandGene.DoubleClass, 3, 3),
				new Terminal(conf, CommandGene.DoubleClass, 1, 1),
				new Terminal(conf, CommandGene.DoubleClass, 0, 0),
				new Terminal(conf, CommandGene.DoubleClass, 2, 2),
				new Terminal(conf, CommandGene.DoubleClass, 5, 5),
				new FoodAhead(conf, CommandGene.DoubleClass),
				new WallAhead(conf, CommandGene.DoubleClass),
				new Orientation(conf, CommandGene.DoubleClass),
				// new MoveTowards(conf, CommandGene.DoubleClass),
				new Equals(conf, CommandGene.DoubleClass),
				new SmellResource(conf, CommandGene.DoubleClass),
				// new SubProgram(conf, 5, CommandGene.DoubleClass),
				// new SubProgram(conf, 4, CommandGene.DoubleClass),
				new SubProgram(conf, 3, CommandGene.DoubleClass),
				new SubProgram(conf, 2, CommandGene.DoubleClass), };
		// Create the node sets
		final int command_len = commands.length;
		final CommandGene[][] nodeSets = new CommandGene[1][numInputVariables
				+ command_len];
		// the variables:
		// 1) in the nodeSets matrix
		// 2) as variables (to be used for fitness checking)
		// --------------------------------------------------
		
		// assign the functions/terminals
		// ------------------------------
		for (int i = 0; i < command_len; i++) {
			System.out.println("function1: " + commands[i]);
			nodeSets[0][i + numInputVariables] = commands[i];
		}
		// ADF functions in the second array in nodeSets
		
		// Create genotype with initial population. Here, we use the
		// declarations made above:
		// ----------------------------------------------------------
		return GPGenotype.randomInitialGenotype(conf, types, argTypes,
				nodeSets, maxNodes, verboseOutput);
		
	}
	
	/*
	 * makeCommands: makes the CommandGene array given the function listed in
	 * the configurations file
	 * ------------------------------------------------------------
	 */

	public IGPProgram getFittest() {
		return fittest;
	}
	
	public void initConfig(final GPConfiguration config)
			throws InvalidConfigurationException {
		
		// We use a delta fitness evaluator because we compute a defect rate,
		// not
		// a point score!
		// -------------------------------------------------------
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setFitnessFunction(factory.getGPFitnessFunction());
		config.setMaxInitDepth(maxInitDepth);
		config.setPopulationSize(populationSize);
		// Default selectionMethod is is TournamentSelector(3)
		if (tournamentSelectorSize > 0) {
			config.setSelectionMethod(new TournamentSelector(
					tournamentSelectorSize));
		}
		
		/**
		 * The maximum depth of an individual resulting from crossover.
		 */
		config.setMaxCrossoverDepth(maxCrossoverDepth);
		
		/**
		 * @param a_strict
		 *            true: throw an error during evolution in case a situation
		 *            is detected where no function or terminal of a required
		 *            type is declared in the GPConfiguration; false: don't
		 *            throw an error but try a completely different combination
		 *            of functions and terminals
		 */
		// config.setStrictProgramCreation(true);
		config.setStrictProgramCreation(false);
		// Default from GPConfiguration.java
		
		/**
		 * In crossover: If random number (0..1) < this value, then choose a
		 * function otherwise a terminal.
		 */
		config.setFunctionProb(functionProb);
		/**
		 * The probability that a reproduction operation is chosen during
		 * evolution. Must be between 0.0d and 1.0d. crossoverProb +
		 * reproductionProb must equal 1.0d.
		 */
		config.setReproductionProb(reproductionProb);
		/**
		 * The probability that a node is mutated during growing a program.
		 */
		config.setMutationProb(mutationProb);
		/**
		 * The probability that the arity of a node is changed during growing a
		 * program.
		 */
		config.setDynamizeArityProb(dynamizeArityProb);
		/**
		 * Percentage of the population that will be filled with new individuals
		 * during evolution. Must be between 0.0d and 1.0d.
		 */
		config.setNewChromsPercent(newChromsPercent);
		/**
		 * The minimum depth of an individual when the world is created.
		 */
		config.setMinInitDepth(minInitDepth);
		/**
		 * If m_strictProgramCreation is false: Maximum number of tries to
		 * construct a valid program.
		 */
		config.setProgramCreationMaxTries(programCreationMaxTries);
	}
	
	public void run() {
		GPGenotype gp = null;
		try {
			// Use the log4j configuration
			// Log to stdout instead of file
			// -----------------------------
			// org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
			
			LOGGER.addAppender(new ConsoleAppender(new SimpleLayout(),
					"System.out"));
			//
			
			gp = create();
			
		} catch (final InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		// Setup the algorithm's parameters.
		// ---------------------------------
		
		// Create the genotype of the problem, i.e., define the GP commands and
		// terminals that can be used, and constrain the structure of the GP
		// program.
		// --------------------------------------------------------------------
		
		// gp.setVerboseOutput(true);
		gp.setVerboseOutput(false);
		startTime = System.currentTimeMillis();
		// Start the computation with maximum 800 evolutions.
		// if a satisfying result is found (fitness value almost 0), JGAP stops
		// earlier automatically.
		// --------------------------------------------------------------------
		// gp.evolve(numEvolutions);
		
		//
		// I'm rolling my own to to be able to control output better etc.
		//
		System.out.println("Creating initial population");
		System.out.println("Mem free: "
				+ SystemKit.niceMemory(SystemKit.getTotalMemoryMB()) + " MB");
		fittest = null;
		
		double bestFit = -1.0d;
		String bestProgram = "";
		int bestGen = 0;
		HashMap<String, Integer> similiar = null;
		if (showSimiliar) {
			similiar = new HashMap<String, Integer>();
		}
		int plateau = 0;
		for (int gen = 1; gen <= numEvolutions; gen++) {
			GPPopulation pop = gp.getGPPopulation();
			for (final EvolutionCycleListener e : cycleListeners) {
				e.startCycle(new EvolutionCycleEvent(pop, gen));
			}
			
			gp.evolve(); // evolve one generation
			if (gen % 20 == 0) {
				System.out.println("Generation: " + gen);
			}
			gp.calcFitness();
			pop = gp.getGPPopulation();
			final IGPProgram thisFittest = pop.determineFittestProgram();
			// TODO: Here I would like to have the correlation coefficient etc
			thisFittest.setApplicationData(("gen" + gen));
			final ProgramChromosome chrom = thisFittest.getChromosome(0);
			final String program = chrom.toStringNorm(0);
			final double fitness = thisFittest.getFitnessValue();
			// if (showSimiliar || showPopulation) {
			// if (showPopulation) {
			// System.out.println("Generation " + gen
			// + " (show whole population, sorted)");
			// }
			// pop.sortByFitness();
			// for (IGPProgram p : pop.getGPPrograms()) {
			// double fit = p.getFitnessValue();
			// if (showSimiliar && fit <= bestFit) {
			// String prog = p.toStringNorm(0);
			// if (!similiar.containsKey(prog)) {
			// similiar.put(prog, 1);
			// } else {
			// similiar.put(prog, similiar.get(prog) + 1);
			// }
			// }
			// if (showPopulation) {
			// String prg = p.toStringNorm(0);
			// int sz = p.size();
			// System.out.println("\tprogram: " + prg + " fitness: "
			// + fit);
			// }
			// }
			// }
			//
			// Yes, I have to think more about this....
			// Right now a program is printed if it has
			// better fitness value than the former best solution.
			
			// if (gen % 25 == 0) {
			// myOutputSolution(fittest, gen);
			// }
			if (bestFit < 0.0d || fitness < bestFit) {
				bestGen = gen;
				myOutputSolution(thisFittest, gen);
				bestFit = fitness;
				bestProgram = program;
				fittest = thisFittest;
				if (showSimiliar) {
					// reset the hash
					similiar.clear(); // = new HashMap<String,Integer>();
				}
				// CHECKSTYLE.OFF: MagicNumber
				plateau = 0;
				getGPConfiguration().setMutationProb(0.1f);
				mutationProb = 0.1f;
				
				// Ensure that the best solution is in the population.
				// gp.addFittestProgram(thisFittest);
			} else {
				plateau++;
				
			}
			
			if (plateau > 15 && gen > 30 && mutationProb < 0.3) {
				System.out.println("Increase Mutation Rate");
				mutationProb *= 1.2;
				getGPConfiguration().setMutationProb(mutationProb);
				
				plateau = 0;
			}
			// CHECKSTYLE.ON: MagicNumber
			for (final EvolutionCycleListener e : cycleListeners) {
				e.endCycle(new EvolutionCycleEvent(pop, gen));
			}
			
		}
		
		// Print the best solution so far to the console.
		// ----------------------------------------------
		// gp.outputSolution(gp.getAllTimeBest());
		
		System.out.println("\nAll time best (from generation " + bestGen + ")");
		myOutputSolution(fittest, numEvolutions);
		System.out.println("applicationData: " + fittest.getApplicationData());
		// Create a graphical tree of the best solution's program and write it
		// to
		// a PNG file.
		// ----------------------------------------------------------------------
		// problem.showTree(gp.getAllTimeBest(), "mathproblem_best.png");
		
		endTime = System.currentTimeMillis();
		final long elapsedTime = endTime - startTime;
		System.out.println("\nTotal time " + elapsedTime + "ms");
		if (showSimiliar) {
			System.out.println("\nAll solutions with the best fitness ("
					+ bestFit + "):");
			// TODO: These should be sorted by values.
			for (final String p : similiar.keySet()) {
				System.out.println(p + " (" + similiar.get(p) + ")");
			}
		}
		
		final EnvironmentMap map = factory.createMap();
		
		map.addLife(factory.createLife(fittest, map));
		
		final SimulationFrame sf = new SimulationFrame(map);
		// sc.initSimulation();
		sf.setTimerListener();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				SimulationFrame.createAndShowGUI(sf);
			}
		});
		
	}
	
	/**
	 * Fitness function for evaluating the produced fomulas, represented as GP
	 * programs. The fitness is computed by calculating the result (Y) of the
	 * function/formula for integer inputs 0 to 20 (X). The sum of the
	 * differences between expected Y and actual Y is the fitness, the lower the
	 * better (as it is a defect rate here).
	 */
	
	/**
	 * Outputs the best solution until now at standard output.
	 * 
	 * This is stolen (and somewhat edited) from GPGenotype.outputSolution which
	 * used log4j.
	 * 
	 * @param a_best
	 *            the fittest ProgramChromosome
	 * 
	 * @author Hakan Kjellerstrand (originally by Klaus Meffert)
	 */
	public void myOutputSolution(final IGPProgram a_best, final int gen) {
		final String freeMB = SystemKit.niceMemory(SystemKit.getFreeMemoryMB());
		System.out.println("Evolving generation " + (gen) + "/" + numEvolutions
				+ ", memory free: " + freeMB + " MB");
		if (a_best == null) {
			System.out.println("No best solution (null)");
			return;
		}
		final double bestValue = a_best.getFitnessValue();
		if (Double.isInfinite(bestValue)) {
			System.out.println("No best solution (infinite)");
			return;
		}
		System.out.println("Best solution fitness: "
				+ NumberKit.niceDecimalNumber(bestValue, 2));
		System.out.println("Best solution: " + a_best.toStringNorm(0));
		String depths = "";
		final int size = a_best.size();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				depths += " / ";
			}
			depths += a_best.getChromosome(i).getDepth(0);
		}
		if (size == 1) {
			System.out.println("Depth of chrom: " + depths);
		} else {
			System.out.println("Depths of chroms: " + depths);
		}
	}
	
	@Override
	public ALife getFittestLife() {
		if (getFittest() == null) {
			return null;
		} else {
			return new ALifeGP(getFittest(), null);
		}
	}
}
