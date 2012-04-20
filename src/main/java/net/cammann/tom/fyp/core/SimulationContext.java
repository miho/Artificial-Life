package net.cammann.tom.fyp.core;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.gui.BestLifeLauncher;
import net.cammann.tom.fyp.gui.LoggingFrame;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jgap.IChromosome;

/**
 * Main class to launch GA algorithm.
 * 
 * @author TC
 * @version $Id: $
 */
public class SimulationContext {

	/**
	 * 
	 */
	private SimulationContext() {

	}

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(SimulationContext.class);

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

		logger.info("Starting gene lab");

		final StatsPackage stats = new StatsPackage();

		final EvolutionFactory lf = new BasicLifeFactory();

		final GeneLab g = new GeneLab(lf);

		LoggingFrame.getInstance().setVisible("simCOn", true);

		g.setEvolutions(10);
		// g.setPopulationSize(100);
		final BestLifeLauncher bll = new BestLifeLauncher(g, lf);
		bll.createAndShowGui();
		stats.startFitnessGraph();
		// stats.startFreqFitnessGraph();
		g.addEvolutionCycleListener(new EvolutionCycleListener() {

			@Override
			public void startCycle(final EvolutionCycleEvent e) {
				logger.info("Generation: " + e.getGenerationNum());
				logger.info("Highest fitness: "
						+ e.getPopulation().determineFittestChromosome()
								.getFitnessValue());

			}

			@Override
			public void endCycle(final EvolutionCycleEvent e) {
				logger.info("Finished Generation: " + e.getGenerationNum());
				stats.add(e.getPopulation(), e.getGenerationNum());

			}
		});

		g.start();
		// stats.showFreqFitnessGraph();

		stats.showGenerationGeneTable();

		logger.trace("Finished gene lab");

		createAndShowFromFactory(lf, g.getBestSolutionSoFar());

	}

	/**
	 * Create and show gui with chromosome.
	 * 
	 * @param factory
	 *            creates map and life
	 * @param chromo
	 *            used to create life
	 */
	@SuppressWarnings("deprecation")
	public static void createAndShowFromFactory(final EvolutionFactory factory,
			final IChromosome chromo) {

		final EnvironmentMap map = factory.createMap();
		ALife life = factory.createLife(chromo, map);
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
