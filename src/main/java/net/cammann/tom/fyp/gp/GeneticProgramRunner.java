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
import java.util.List;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionCycleEvent;
import net.cammann.tom.fyp.core.EvolutionCycleListener;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.EvolutionModule;
import net.cammann.tom.fyp.gp.commands.Consume;
import net.cammann.tom.fyp.gp.commands.FoodAhead;
import net.cammann.tom.fyp.gp.commands.MoveForward;
import net.cammann.tom.fyp.gp.commands.TurnLeft;
import net.cammann.tom.fyp.gp.commands.TurnRight;
import net.cammann.tom.fyp.gp.commands.WallAhead;
import net.cammann.tom.fyp.gui.SimulationFrame;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.SubProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.GPPopulation;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.impl.TournamentSelector;
import org.jgap.gp.terminal.Terminal;
import org.jgap.util.NumberKit;
import org.jgap.util.SystemKit;

/**
 * <p>
 * GeneticProgramFrame class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public final class GeneticProgramRunner extends GPProblem implements
		EvolutionModule {

	/**
	 * Logger.
	 */
	private final Logger logger = Logger.getLogger(GeneticProgramRunner.class);

	/**
	 * Save fittest of run to here.
	 */
	private IGPProgram fittest;
	// number of variables to use (output variable is excluded)
	private int numInputVariables;

	private boolean paused = false;

	// CHECKSTYLE.OFF: MagicNumber
	/**
	 * Minimum Depth of initial gp tree.
	 */
	private final int minInitDepth = 2;

	/**
	 * Maximum Depth of initial gp tree.
	 */
	private final int maxInitDepth = 4;

	/**
	 * Population Size of run.
	 */
	private int populationSize = 10000;

	/**
	 * Maximum cross over depth of run.
	 */
	private final int maxCrossoverDepth = 6;

	/**
	 * Initial program creation tries.
	 */
	private final int programCreationMaxTries = 5;

	/**
	 * Number of generations to iterate through.
	 */
	private int numGenerations = 500;

	/**
	 * Verbose output?
	 */
	private final boolean verboseOutput = true;

	/**
	 * Max number of nodes in gp tree.
	 */
	private final int maxNodes = 100;

	/**
	 * Probabilty of using a function as node.
	 */
	private final double functionProb = 0.8d;

	/**
	 * Chance to reproduce.
	 */
	private final float reproductionProb = 0.1f;

	/**
	 * Chance a gp tree will mutate in a generation.
	 */
	private float mutationProb = 0.06f;

	/**
	 * Chance that crossover will occur on gp trees.
	 */
	private final double crossoverProb = 0.9d;

	private final double newChromsPercent = 0.1d;

	private final int tournamentSelectorSize = 0;

	/**
	 * Used for timing run.
	 */
	private long startTime;

	/**
	 * Used for timing run.
	 */
	private long endTime;

	// private final boolean showPopulation = false;

	// private final boolean showSimiliar = false;

	/**
	 * Used to keep evolution cycle listeners.
	 * 
	 * Will fire an event to the listeners when a generation begins or starts.
	 */
	private final List<EvolutionCycleListener> cycleListeners;

	// CHECKSTYLE.ON: MagicNumber

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.add(ecl);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEvolutionCycleListener(final EvolutionCycleListener ecl) {
		cycleListeners.remove(ecl);
	}

	/**
	 * This factory generates the life and maps for the run.
	 */
	private final EvolutionFactory factory;

	/**
	 * <p>
	 * Constructor for GeneticProgramFrame.
	 * </p>
	 * 
	 * @param factory
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public GeneticProgramRunner(final EvolutionFactory factory) {
		cycleListeners = new ArrayList<EvolutionCycleListener>();
		this.factory = factory;
	}

	@Override
	public void startpause() {
		if (paused == true) {
			paused = false;
			return;
		}
		paused = true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method is used for setting up the commands and terminals that can be
	 * used to solve the problem.
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

		/**
		 * Assign the available GP terminals and commands.
		 */
		final CommandGene[] commands = {
				new Consume(conf, CommandGene.DoubleClass),
				new MoveForward(conf, CommandGene.DoubleClass),
				new TurnLeft(conf, CommandGene.DoubleClass),
				new TurnRight(conf, CommandGene.DoubleClass),
				new Add(conf, CommandGene.DoubleClass),
				// new Subtract(conf, CommandGene.DoubleClass),
				// new Multiply(conf, CommandGene.DoubleClass),
				// new OnResource(conf, CommandGene.DoubleClass),
				// new If(conf, CommandGene.DoubleClass),
				// new IfElse(conf, CommandGene.DoubleClass),
				// new LesserThan(conf, CommandGene.DoubleClass),
				// new GreaterThan(conf, CommandGene.DoubleClass),
				// new Terminal(conf, CommandGene.DoubleClass, 3, 3),
				new Terminal(conf, CommandGene.DoubleClass, 1, 1),
				new Terminal(conf, CommandGene.DoubleClass, 0, 0),
				// new Terminal(conf, CommandGene.DoubleClass, 2, 2),
				// new Terminal(conf, CommandGene.DoubleClass, 5, 5),
				new FoodAhead(conf, CommandGene.DoubleClass),
				new WallAhead(conf, CommandGene.DoubleClass),
				// new Orientation(conf, CommandGene.DoubleClass),
				// new MoveTowards(conf, CommandGene.DoubleClass),
				// new Equals(conf, CommandGene.DoubleClass),
				// new SmellResource(conf, CommandGene.DoubleClass),
				// new SubProgram(conf, 5, CommandGene.DoubleClass),
				// new SubProgram(conf, 4, CommandGene.DoubleClass),
				// new SubProgram(conf, 3, CommandGene.DoubleClass),
				new SubProgram(conf, 2, CommandGene.DoubleClass), };
		// Create the node sets
		final int commandLen = commands.length;
		final CommandGene[][] nodeSets = new CommandGene[1][numInputVariables
				+ commandLen];
		// the variables:
		// 1) in the nodeSets matrix
		// 2) as variables (to be used for fitness checking)
		// --------------------------------------------------

		// assign the functions/terminals
		// ------------------------------
		for (int i = 0; i < commandLen; i++) {
			logger.info("function1: " + commands[i]);
			nodeSets[0][i + numInputVariables] = commands[i];
		}
		// ADF functions in the second array in nodeSets

		// Create genotype with initial population. Here, we use the
		// declarations made above:
		// ----------------------------------------------------------
		return GPGenotype.randomInitialGenotype(conf, types, argTypes,
				nodeSets, maxNodes, verboseOutput);

	}

	/**
	 * <p>
	 * initConfig.
	 * </p>
	 * 
	 * @param config
	 *            a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @throws org.jgap.InvalidConfigurationException
	 *             if any.
	 */
	public void initConfig(final GPConfiguration config)
			throws InvalidConfigurationException {

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

	/**
	 * <p>
	 * run.
	 * </p>
	 */
	@Override
	public void start() {
		GPGenotype gp = null;
		try {

			gp = create();

		} catch (final InvalidConfigurationException e) {
			e.printStackTrace();
			logger.fatal("Could not create Genotype", e);
			System.exit(1);
		}

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
		logger.info("Creating initial population");
		logger.info("Mem free: "
				+ SystemKit.niceMemory(SystemKit.getTotalMemoryMB()) + " MB");
		fittest = null;

		double bestFit = -1.0d;
		String bestProgram = "";
		int bestGen = 0;

		int plateau = 0;
		for (int gen = 1; gen <= numGenerations; gen++) {

			if (paused == true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gen--;
				continue;
			}

			GPPopulation pop = gp.getGPPopulation();
			for (final EvolutionCycleListener e : cycleListeners) {
				e.startCycle(new EvolutionCycleEvent(pop, gen));
			}

			gp.evolve(); // evolve one generation

			gp.calcFitness();
			pop = gp.getGPPopulation();
			final IGPProgram thisFittest = pop.determineFittestProgram();
			// thisFittest.setApplicationData(("gen" + gen));
			final ProgramChromosome chrom = thisFittest.getChromosome(0);
			final String program = chrom.toStringNorm(0);
			final double fitness = thisFittest.getFitnessValue();
			if (fittest == null) {
				fittest = thisFittest;
			}

			if (fitness > fittest.getFitnessValue()) {
				myOutputSolution(fittest, numGenerations);
				myOutputSolution(fittest, numGenerations);
				bestGen = gen;
				myOutputSolution(thisFittest, gen);
				bestFit = fitness;
				bestProgram = program;
				fittest = thisFittest;

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
				logger.info("Increase Mutation Rate");
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

		logger.info("\nAll time best (from generation " + bestGen + ")");
		myOutputSolution(fittest, numGenerations);
		logger.info("applicationData: " + fittest.getApplicationData());
		// Create a graphical tree of the best solution's program and write it
		// to
		// a PNG file.
		// --------------------------------------------------------------
		// problem.showTree(gp.getAllTimeBest(), "mathproblem_best.png");

		endTime = System.currentTimeMillis();
		final long elapsedTime = endTime - startTime;
		logger.info("\nTotal time " + elapsedTime + "ms");

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
	 * Outputs the best solution until now at standard output.
	 * 
	 * This is stolen (and somewhat edited) from GPGenotype.outputSolution which
	 * used log4j.
	 * 
	 * @param a_best
	 *            the fittest ProgramChromosome
	 * @author Hakan Kjellerstrand (originally by Klaus Meffert)
	 * @param gen
	 *            a int.
	 */
	public void myOutputSolution(final IGPProgram a_best, final int gen) {
		final String freeMB = SystemKit.niceMemory(SystemKit.getFreeMemoryMB());
		logger.info("Evolving generation " + (gen) + "/" + numGenerations
				+ ", memory free: " + freeMB + " MB");
		if (a_best == null) {
			logger.info("No best solution (null)");
			return;
		}
		final double bestValue = a_best.getFitnessValue();
		if (Double.isInfinite(bestValue)) {
			logger.info("No best solution (infinite)");
			return;
		}
		logger.info("Best solution fitness: "
				+ NumberKit.niceDecimalNumber(bestValue, 2));
		logger.info("Best solution: " + a_best.toStringNorm(0));
		String depths = "";
		final int size = a_best.size();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				depths += " / ";
			}
			depths += a_best.getChromosome(i).getDepth(0);
		}
		if (size == 1) {
			logger.info("Depth of chrom: " + depths);
		} else {
			logger.info("Depths of chroms: " + depths);
		}
	}

	/** {@inheritDoc} */
	@Override
	public ALife getFittestLife() {
		if (fittest == null) {
			return null;
		} else {
			return new ALifeGP(fittest, null);
		}
	}

	@Override
	public int getPopulationSize() {
		return populationSize;
	}

	@Override
	public int getNumGenerations() {
		return numGenerations;
	}

	@Override
	public void setMaxGenerations(final int i) {
		numGenerations = i;

	}

	@Override
	public void setPopulationSize(final int i) {
		populationSize = i;

	}
}
