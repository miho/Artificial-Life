package net.cammann.tom.fyp.core;

import javax.swing.JOptionPane;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.ga.GeneticAlgorithmRunner;
import net.cammann.tom.fyp.gp.GeneticProgramRunner;
import net.cammann.tom.fyp.gui.BestLifeLauncher;
import net.cammann.tom.fyp.gui.LoggingFrame;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Main class to launch GA algorithm.
 * 
 * @author TC
 * @version $Id: $
 */
public final class SimulationLauncher {
	
	/**
	 * 
	 */
	private SimulationLauncher() {
		
	}
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(SimulationLauncher.class);
	
	/**
	 * Starts genetic algorithm for creating ALife.
	 * 
	 * Creates gui to launch fittest life so far and graphs.
	 * 
	 * @param args
	 *            do nothing
	 */
	public static void main(final String[] args) {
		
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		
		EvolutionModule evolutionModule = null;
		final EvolutionFactory factory = new BasicLifeFactory();
		
				
		final Object[] options = { "Genetic Programming", "Genetic Algorithm",
				"Cancel" };
		final int n = JOptionPane.showOptionDialog(null,
				"What algorithm would you like to use?", "Launch Options",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[2]);
		
		if (n == 0) {
			logger.info("Genetic Programming selected.");
			evolutionModule = new GeneticProgramRunner(factory);
		} else if (n == 1) {
			logger.info("Genetic Alogirthm selected.");
			evolutionModule = new GeneticAlgorithmRunner(factory);
		} else {
			
			logger.info("Simulation Cancelled");
			
			System.exit(1);
		}
		
		logger.info("Starting gene lab");
		
		final StatsPackage stats = new StatsPackage();
		
		final EvolutionFactory lf = new BasicLifeFactory();
		
		final BestLifeLauncher bll = new BestLifeLauncher(evolutionModule, lf);
		
		LoggingFrame.getInstance().setVisible("simCon", true);
		factory.setNumOfResources(200);
		factory.setNumOfObstacles(40);
		evolutionModule.setMaxGenerations(60);
		evolutionModule.setPopulationSize(1000);
		logger.info("Generations to run: "+evolutionModule.getNumGenerations());
		// g.setPopulationSize(100);
		
		bll.createAndShowGui();
		stats.startFitnessGraph();
		// stats.startFreqFitnessGraph();
		evolutionModule.addEvolutionCycleListener(new EvolutionCycleListener() {
			
			@Override
			public void startCycle(final EvolutionCycleEvent e) {
				logger.info("Generation: " + e.getGenerationNum());
				logger.info("Highest fitness: " + e.getHighestFitnessInPop());
				
			}
			
			@Override
			public void endCycle(final EvolutionCycleEvent e) {
				logger.info("Finished Generation: " + e.getGenerationNum());
				stats.add(e);
			}
		});
		
		evolutionModule.start();
		// stats.showFreqFitnessGraph();
		if( n == 1)
			stats.showGenerationGeneTable();
		
		logger.trace("Finished gene lab");
		
		createAndShowFromFactory(lf, evolutionModule.getFittestLife());
		
	}
	
	/**
	 * Create and show gui with chromosome.
	 * 
	 * @param factory
	 *            creates map and life
	 * @param life_
	 *            used to create life
	 */
	@SuppressWarnings(value = "deprecation")
	public static void createAndShowFromFactory(final EvolutionFactory factory,
			final ALife life_) {
		
		final EnvironmentMap map = factory.createMap();
		final ALife life = factory.createLife(life_, map);
		logger.info("Life Pos: " + life.getPosition());
		map.addLife(life);
		
		final SimulationFrame sf = new SimulationFrame(map);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				SimulationFrame.createAndShowGUI(sf);
			}
		});
		
	}
}
