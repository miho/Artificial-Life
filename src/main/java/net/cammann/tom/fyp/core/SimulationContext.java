package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.gp.BestLifeLauncher;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jgap.IChromosome;

public class SimulationContext {
	private final EnvironmentMap map;
	private Timer timer;
	private boolean isVisual = false;
	// private JPanel panel;
	private JFrame frame;
	private int counter;
	static Logger logger = Logger.getLogger(SimulationContext.class);
	private int simulationRate = 100;
	
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
		
		final SimulationContext sc = new SimulationContext(map);
		
		for (int j = 0; j < lf.getNumClones(); j++) {
			
			sc.addLife(lf.createLife(chromo, map));
		}
		
		sc.initSimulation();
		
		sc.setTimerListener();
		
		final SimulationFrame sf = new SimulationFrame(sc);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(sf, sc);
			}
		});
		
	}
	
	public static void createAndShowGUI(SimulationFrame sf, SimulationContext sc) {
		sf.setVisible(true);
		sc.start();
	}
	
	public SimulationContext(ALife lifeForm, EnvironmentMap map) {
		
		this.map = map;
		map.addLife(lifeForm);
		
		// initSimulation();
	}
	
	public SimulationContext(EnvironmentMap map) {
		
		this.map = map;
		
	}
	
	public void addLife(ALife life) {
		
		map.addLife(life);
		
	}
	
	public EnvironmentMap getMap() {
		return map;
	}
	
	// TODO
	// DO WE NEED INIT SIM? Setup stuff in constructor. setup life using
	// resetLife()?
	public void initSimulation() {
		
		// for (ALife life : bugs) {
		// // TODO
		//
		// // What are these calls doing, should be somewhere else, out of the
		// // view
		//
		// counter = 0;
		// log.debug("Starting energy: " + life.getEnergy());
		// }
		
		map.resetMap();
	}
	
	public Dimension getSizeOfMap() {
		return map.getDimension();
	}
	
	public int getMapWidth() {
		return map.getWidth();
	}
	
	public int getMapHeight() {
		return map.getHeight();
	}
	
	public void RegisterFrame(JFrame frame, JPanel panel) {
		this.isVisual = true;
		// this.panel = panel;
		this.frame = frame;
		
	}
	
	public void moveOnce() {
		map.incrementTimeFrame();
	}
	
	public void setTimerListener() {
		
		timer = new Timer(simulationRate, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("tick.");
				
				counter++;
				
				map.incrementTimeFrame();
				// for (ALife life : bugs) {
				// life.doMove();
				// if (life.getEnergy() < 0) {
				// log.trace("Life is dead");
				// }
				//
				// log.trace("Life X: " + life.getX());
				// log.trace("Life Y: " + life.getY());
				// }
				
				if (isVisual) {
					// panel.repaint();
					logger.trace("About to...");
					frame.repaint();
					logger.trace("Repaint.");
				}
				
			}
		});
		
	}
	
	public void start() {
		timer.start();
	}
	
	public void pause() {
		timer.stop();
	}
	
	public void stop() {
		timer.stop();
		setTimerListener();
		initSimulation();
		
		frame.repaint();
		
	}
	
	public void reset() {
		stop();
		
		start();
	}
	
	public int getMoveCount() {
		return counter;
	}
	
	public void setSimulationRate(int rate) {
		this.simulationRate = rate;
		if (timer.isRunning()) {
			pause();
			setTimerListener();
			start();
		} else {
			setTimerListener();
		}
		
	}
	
	public int getSimulationRate() {
		return simulationRate;
	}
	
	// public void removeLife(ALife i) {
	// bugs.remove(i);
	// }
	//
	// public void removeLife(int i) {
	// bugs.remove(i);
	// }
	//
	// public void setMap(EnvironmentMap _map) {
	// stop();
	// for (ALife life : getLife()) {
	// life.setMap(_map);
	// }
	// map = _map;
	//
	// }
	
	public boolean isStopped() {
		return !timer.isRunning();
	}
	
	public boolean isRunning() {
		return timer.isRunning();
	}
}