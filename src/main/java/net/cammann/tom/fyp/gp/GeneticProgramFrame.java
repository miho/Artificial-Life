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

import net.cammann.tom.fyp.basicLife.BasicMap;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimulationContext;
import net.cammann.tom.fyp.gp.commands.Consume;
import net.cammann.tom.fyp.gp.commands.FoodAhead;
import net.cammann.tom.fyp.gp.commands.MoveForward;
import net.cammann.tom.fyp.gp.commands.MoveTowards;
import net.cammann.tom.fyp.gp.commands.OnResource;
import net.cammann.tom.fyp.gp.commands.Orientation;
import net.cammann.tom.fyp.gp.commands.SmellResource;
import net.cammann.tom.fyp.gp.commands.TurnLeft;
import net.cammann.tom.fyp.gp.commands.TurnRight;
import net.cammann.tom.fyp.gp.commands.WallAhead;
import net.cammann.tom.fyp.gui.SimulationFrame;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Equals;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.If;
import org.jgap.gp.function.IfElse;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.GPPopulation;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.impl.TournamentSelector;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;
import org.jgap.util.NumberKit;
import org.jgap.util.SystemKit;

public class GeneticProgramFrame extends GPProblem {
	private transient static Logger LOGGER = Logger
			.getLogger(GeneticProgramFrame.class);
	
	/*
	 * public variables which may be changed by configuration file
	 */
	public static IGPProgram fittest;
	// number of variables to use (output variable is excluded)
	public static int numInputVariables;
	
	// the variables to use (of size numInputVariables)
	public static Variable[] variables;
	
	// variable name
	public static String[] variableNames;
	
	// index of the output variable
	public static Integer outputVariable; // default last
	
	public static int[] ignoreVariables; // TODO
	
	// constants
	public static ArrayList<Double> constants = new ArrayList<Double>();
	
	// size of data
	public static int numRows;
	
	// the data (as Double)
	// Note: the last row is the output variable per default
	protected static Double[][] data;
	
	// If we have found a perfect solution.
	public static boolean foundPerfect = false;
	
	// standard GP parameters
	public static int minInitDepth = 2;
	
	public static int maxInitDepth = 8;
	
	public static int populationSize = 1000;
	
	public static int maxCrossoverDepth = 8;
	
	public static int programCreationMaxTries = 5;
	
	public static int numEvolutions = 300;
	
	public static boolean verboseOutput = true;
	
	public static int maxNodes = 60;
	
	public static double functionProb = 0.8d;
	
	public static float reproductionProb = 0.1f; // float
	
	public static float mutationProb = 0.1f; // float
	
	public static double crossoverProb = 0.9d;
	
	public static float dynamizeArityProb = 0.08f; // float
	
	public static double newChromsPercent = 0.3d;
	
	public static int tournamentSelectorSize = 0;
	
	// lower/upper ranges for the Terminal
	public static double lowerRange = -10.0d;
	
	public static double upperRange = 10.0d;
	
	// Should the terminal be a wholenumber or not?
	public static boolean terminalWholeNumbers = true;
	
	public static String returnType = "DoubleClass"; // not used yet
	
	public static String presentation = "";
	
	// Using ADF
	
	// list of functions (as strings)
	public static String[] functions = { "Multiply", "Divide", "Add",
			"Subtract" };
	
	// Should we punish length of solutions?
	// Note: Very simplistic version.
	// public static boolean punishLength = false;
	
	// if the values are too small we may want to scale
	// the error
	public static double scaleError = -1.0d;
	
	// "bumping" is when we found a "perfect solution" and
	// want to see more "perfect solutions"
	public static boolean bumpPerfect = false;
	
	// the limit for which we should show all (different) solutions
	public static Double bumpValue = 0.0000;
	
	// checks for already shown solution when bumping
	private static HashMap<String, Integer> foundSolutions = new HashMap<String, Integer>();
	
	// timing
	public static long startTime;
	
	public static long endTime;
	
	// if > 0.0d -> stop if the fitness is below or equal
	// this value. TODO!
	public static double stopCriteria = -1.0d;
	
	public static boolean showPopulation = false;
	
	public static boolean showSimiliar = false;
	
	public GeneticProgramFrame(GPConfiguration a_conf)
			throws InvalidConfigurationException {
		super(a_conf);
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
		GPConfiguration conf = getGPConfiguration();
		// At first, we define the return type of the GP program.
		// ------------------------------------------------------
		// Then, we define the arguments of the GP parts. Normally, only for
		// ADF's
		// there is a specification here, otherwise it is empty as in first
		// case.
		// -----------------------------------------------------------------------
		Class<?>[] types;
		Class<?>[][] argTypes;
		
		types = new Class[] { CommandGene.DoubleClass };
		argTypes = new Class[][] { {} };
		
		// Next, we define the set of available GP commands and terminals to
		// use.
		// Please see package org.jgap.gp.function and org.jgap.gp.terminal
		// You can easily add commands and terminals of your own.
		// ----------------------------------------------------------------------
		
		CommandGene[] commands = { new Consume(conf, CommandGene.DoubleClass),
				new MoveForward(conf, CommandGene.DoubleClass),
				new TurnLeft(conf, CommandGene.DoubleClass),
				new TurnRight(conf, CommandGene.DoubleClass),
				new Add(conf, CommandGene.DoubleClass),
				new Subtract(conf, CommandGene.DoubleClass),
				new Multiply(conf, CommandGene.DoubleClass),
				new OnResource(conf, CommandGene.DoubleClass),
				new If(conf, CommandGene.DoubleClass),
				new IfElse(conf, CommandGene.DoubleClass),
				new LesserThan(conf, CommandGene.DoubleClass),
				new GreaterThan(conf, CommandGene.DoubleClass),
				new Consume(conf, CommandGene.DoubleClass),
				new Terminal(conf, CommandGene.DoubleClass, 0, 0),
				new Terminal(conf, CommandGene.DoubleClass, 1, 1),
				new Terminal(conf, CommandGene.DoubleClass, 5, 5),
				new Terminal(conf, CommandGene.DoubleClass, 2, 2),
				new FoodAhead(conf, CommandGene.DoubleClass),
				new WallAhead(conf, CommandGene.DoubleClass),
				new Orientation(conf, CommandGene.DoubleClass),
				new MoveTowards(conf, CommandGene.DoubleClass),
				new Equals(conf, CommandGene.DoubleClass),
				new SmellResource(conf, CommandGene.DoubleClass) };
		// Create the node sets
		int command_len = commands.length;
		CommandGene[][] nodeSets = new CommandGene[1][numInputVariables
				+ command_len];
		// the variables:
		// 1) in the nodeSets matrix
		// 2) as variables (to be used for fitness checking)
		// --------------------------------------------------
		variables = new Variable[numInputVariables];
		int variableIndex = 0;
		for (int i = 0; i < numInputVariables + 1; i++) {
			String variableName = variableNames[i];
			if (i != outputVariable) {
				if (variableNames != null && variableNames.length > 0) {
					variableName = variableNames[i];
				}
				variables[variableIndex] = Variable.create(conf, variableName,
						CommandGene.DoubleClass);
				nodeSets[0][variableIndex] = variables[variableIndex];
				System.out.println("input variable: "
						+ variables[variableIndex]);
				variableIndex++;
			}
		}
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
		// this is experimental
		// return GPGenotype.randomInitialGenotype(conf, types, argTypes,
		// nodeSets,
		// minDepths,maxDepths, maxNodes, fullModeAllowed,verboseOutput);
		
	}
	
	//
	// Transpose matrix
	// ----------------
	public static Double[][] transposeMatrix(Double[][] m) {
		int r = m.length;
		int c = m[0].length;
		Double[][] t = new Double[c][r];
		for (int i = 0; i < r; ++i) {
			for (int j = 0; j < c; ++j) {
				t[j][i] = m[i][j];
			}
		}
		return t;
	} // end transposeMatrix
	
	/*
	 * makeCommands: makes the CommandGene array given the function listed in
	 * the configurations file
	 * ------------------------------------------------------------
	 */

	/**
	 * Starts the example.
	 * 
	 * @author Hakan Kjellerstrand
	 */
	public static void main(String[] args) throws Exception {
		// Use the log4j configuration
		// Log to stdout instead of file
		// -----------------------------
		// org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
		LOGGER.addAppender(new ConsoleAppender(new SimpleLayout(), "System.out"));
		//
		// Read a configuration file, or not...
		
		// Default problem
		// Fibonacci series, with three input variables to make it
		// somewhat harder.
		// -------------------------------------------------------
		numRows = 21;
		numInputVariables = 0;
		// Note: The last array is the output array
		
		presentation = "TC ALife";
		
		// Present the problem
		// -------------------
		System.out.println("Presentation: " + presentation);
		if (outputVariable == null) {
			outputVariable = numInputVariables;
		}
		if (variableNames == null) {
			variableNames = new String[numInputVariables + 1];
			for (int i = 0; i < numInputVariables + 1; i++) {
				variableNames[i] = "V" + i;
			}
		}
		System.out.println("output_variable: " + variableNames[outputVariable]
				+ " (index: " + outputVariable + ")");
		// Setup the algorithm's parameters.
		// ---------------------------------
		GPConfiguration config = new GPConfiguration();
		// We use a delta fitness evaluator because we compute a defect rate,
		// not
		// a point score!
		// ----------------------------------------------------------------------
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
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
		config.setFitnessFunction(new GeneticProgramFrame.FormulaFitnessFunction());
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
		GPProblem problem = new GeneticProgramFrame(config);
		// Create the genotype of the problem, i.e., define the GP commands and
		// terminals that can be used, and constrain the structure of the GP
		// program.
		// --------------------------------------------------------------------
		GPGenotype gp = problem.create();
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
		new GPVisual((GeneticProgramFrame) problem);
		double bestFit = -1.0d;
		String bestProgram = "";
		int bestGen = 0;
		HashMap<String, Integer> similiar = null;
		if (showSimiliar) {
			similiar = new HashMap<String, Integer>();
		}
		for (int gen = 1; gen <= numEvolutions; gen++) {
			gp.evolve(); // evolve one generation
			System.out.println("Generation: " + gen);
			gp.calcFitness();
			GPPopulation pop = gp.getGPPopulation();
			IGPProgram thisFittest = pop.determineFittestProgram();
			// TODO: Here I would like to have the correlation coefficient etc
			thisFittest.setApplicationData(("gen" + gen));
			ProgramChromosome chrom = thisFittest.getChromosome(0);
			String program = chrom.toStringNorm(0);
			double fitness = thisFittest.getFitnessValue();
			if (showSimiliar || showPopulation) {
				if (showPopulation) {
					System.out.println("Generation " + gen
							+ " (show whole population, sorted)");
				}
				pop.sortByFitness();
				for (IGPProgram p : pop.getGPPrograms()) {
					double fit = p.getFitnessValue();
					if (showSimiliar && fit <= bestFit) {
						String prog = p.toStringNorm(0);
						if (!similiar.containsKey(prog)) {
							similiar.put(prog, 1);
						} else {
							similiar.put(prog, similiar.get(prog) + 1);
						}
					}
					if (showPopulation) {
						String prg = p.toStringNorm(0);
						int sz = p.size();
						System.out.println("\tprogram: " + prg + " fitness: "
								+ fit);
					}
				}
			}
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
				// Ensure that the best solution is in the population.
				// gp.addFittestProgram(thisFittest);
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
		long elapsedTime = endTime - startTime;
		System.out.println("\nTotal time " + elapsedTime + "ms");
		if (showSimiliar) {
			System.out.println("\nAll solutions with the best fitness ("
					+ bestFit + "):");
			// TODO: These should be sorted by values.
			for (String p : similiar.keySet()) {
				System.out.println(p + " (" + similiar.get(p) + ")");
			}
		}
		
		EnvironmentMap map = new BasicMap();
		
		final SimulationContext sc = new SimulationContext(map);
		
		sc.addLife(new ALifeGP(fittest, map));
		
		sc.initSimulation();
		sc.setVerbosity(0);
		sc.setTimerListener();
		
		final SimulationFrame sf = new SimulationFrame(sc);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SimulationContext.createAndShowGUI(sf, sc);
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
	public static class FormulaFitnessFunction extends GPFitnessFunction {
		@Override
		protected double evaluate(final IGPProgram a_subject) {
			return computeRawFitness(a_subject);
		}
		
		public double computeRawFitness(final IGPProgram ind) {
			
			double fitness = 100000;
			
			for (int i = 0; i < 3; i++) {
				
				EnvironmentMap map = new BasicMap();
				SimulationContext sc = new SimulationContext(map);
				
				sc.addLife(new ALifeGP(ind, map));
				
				sc.initSimulation();
				sc.setVerbosity(0);
				sc.limitedRun(200);
				
				for (ALife life : sc.getLife()) {
					
					fitness -= (life.getEnergy());
					
					fitness -= ((ABug) life).uniqueMoveCount / 10;
					
				}
			}
			if (fitness > 0) {
				return (fitness);
			}
			return 0;
			
		}
	}
	
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
	public static void myOutputSolution(IGPProgram a_best, int gen) {
		String freeMB = SystemKit.niceMemory(SystemKit.getFreeMemoryMB());
		System.out.println("Evolving generation " + (gen) + "/" + numEvolutions
				+ ", memory free: " + freeMB + " MB");
		if (a_best == null) {
			System.out.println("No best solution (null)");
			return;
		}
		double bestValue = a_best.getFitnessValue();
		if (Double.isInfinite(bestValue)) {
			System.out.println("No best solution (infinite)");
			return;
		}
		System.out.println("Best solution fitness: "
				+ NumberKit.niceDecimalNumber(bestValue, 2));
		System.out.println("Best solution: " + a_best.toStringNorm(0));
		String depths = "";
		int size = a_best.size();
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
}