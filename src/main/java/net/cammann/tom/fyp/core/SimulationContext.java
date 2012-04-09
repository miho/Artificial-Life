package net.cammann.tom.fyp.core;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.gp.BestLifeLauncher;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jgap.IChromosome;

public class SimulationContext {
	
	static Logger logger = Logger.getLogger(SimulationContext.class);
	
	public static void main(String args[]) {
		
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		
		logger.trace("Starting gene lab");
		
		final StatsPackage stats = new StatsPackage();
		
		EvolutionFactory lf = new BasicLifeFactory();
		
		GeneLab g = new GeneLab(lf);
		g.setEvolutions(100);
		// g.setPopulationSize(100);
		BestLifeLauncher bll = new BestLifeLauncher(g, lf);
		bll.createAndShowGui();
		stats.startFitnessGraph();
		// stats.startFreqFitnessGraph();
		g.addEvolutionCycleListener(new EvolutionCycleListener() {
			
			@Override
			public void startCycle(EvolutionCycleEvent e) {
				logger.trace("Generation: " + e.getGenerationNum());
				logger.trace("Highest fitness: "
						+ e.getPopulation().determineFittestChromosome()
								.getFitnessValue());
				
			}
			
			@Override
			public void endCycle(EvolutionCycleEvent e) {
				logger.trace("Finished Generation: " + e.getGenerationNum());
				stats.add(e.getPopulation(), e.getGenerationNum());
				
			}
		});
		
		g.start();
		// stats.showFreqFitnessGraph();
		
		stats.showGenerationGeneTable();
		
		logger.trace("Finished gene lab");
		
		createAndShowFromFactory(lf, g.getBestSolutionSoFar());
		
	}
	
	public static void createAndShowFromFactory(EvolutionFactory lf,
			IChromosome chromo) {
		
		EnvironmentMap map = lf.createMap();
		
		// final SimulationContext sc = new SimulationContext(map);
		
		for (int j = 0; j < lf.getNumClones(); j++) {
			
			// map.addLife(lf.createLife(chromo, map));
		}
		
		// sc.initSimulation();
		
		// sc.setTimerListener();
		
		final SimulationFrame sf = new SimulationFrame(map);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SimulationFrame.createAndShowGUI(sf);
			}
		});
		
	}
	
}