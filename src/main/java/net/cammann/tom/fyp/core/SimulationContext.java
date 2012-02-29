package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.cammann.tom.fyp.basicLife.NormalLifeFactory;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.stats.StatsPackage;
import net.cammann.tom.fyp.utils.Logger;

import org.jgap.IChromosome;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class SimulationContext {
	private final List<ALife> bugs;
	private EnvironmentMap map;
	private Timer timer;
	private boolean isVisual = false;
	// private JPanel panel;
	private JFrame frame;
	private int counter;
	private final Logger log;
	private int simulationRate = 100;
	
	public static void main(String args[]) {
		System.out.println("Starting gene lab");
		
		final StatsPackage stats = new StatsPackage();
		
		LifeFactory lf = new NormalLifeFactory();
		
		GeneLab g = new GeneLab(lf);
		g.addEvolutionCycleListener(new EvolutionCycleListener() {
			
			@Override
			public void startCycle(EvolutionCycleEvent e) {
				System.out.println("Starting cycle: " + e.getCycleNo());
				System.out.println("Highest fitness: "
						+ e.getPopulation().determineFittestChromosome()
								.getFitnessValue());
				
			}
			
			@Override
			public void endCycle(EvolutionCycleEvent e) {
				System.out.println("Finished cycle: " + e.getCycleNo());
				stats.add(e.getPopulation(), e.getGenerationNum());
			}
		});
		
		g.start();
		// stats.showFreqFitnessGraph();
		stats.showFitnessGraph();
		stats.showGenerationGeneTable();
		
		System.out.println("Finished gene lab");
		
		runFromFactory(lf, g.getBestSolutionSoFar());
		
	}
	
	public static void runFromFactory(LifeFactory lf, IChromosome chromo) {
		
		EnvironmentMap map = lf.getFitnessFunction().getNewMap();
		
		final SimulationContext sc = new SimulationContext(map);
		
		for (ALife i : lf.getFitnessFunction().getNewLife(chromo, map)) {
			sc.addLife(i);
		}
		sc.initSimulation();
		sc.setVerbosity(5);
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
		
		bugs = new ArrayList<ALife>();
		bugs.add(lifeForm);
		this.map = map;
		log = new Logger("SimContxt");
		// initSimulation();
	}
	
	public SimulationContext(EnvironmentMap map) {
		bugs = new ArrayList<ALife>();
		this.map = map;
		log = new Logger("SimContxt");
	}
	
	public void addLife(ALife life) {
		bugs.add(life);
	}
	
	public List<ALife> getLife() {
		return bugs;
	}
	
	public EnvironmentMap getMap() {
		return map;
	}
	
	public void initSimulation() {
		
		for (ALife life : bugs) {
			
			map.initLife(life);
			life.setEnergy(1000);
			life.setOrientation(ORIENTATION.UP);
			life.setMoveMemory(new ArrayList<Point>());
			
			counter = 0;
			log.debug("Starting energy: " + life.getEnergy());
		}
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
		for (ALife life : bugs) {
			life.doMove();
		}
	}
	
	public void setTimerListener() {
		
		timer = new Timer(simulationRate, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.trace("tick.");
				
				counter++;
				for (ALife life : bugs) {
					life.doMove();
					if (life.getEnergy() < 0) {
						log.trace("Life is dead");
					}
					
					log.trace("Life X: " + life.getX());
					log.trace("Life Y: " + life.getY());
				}
				
				if (isVisual) {
					// panel.repaint();
					log.trace("About to...");
					frame.repaint();
					log.trace("Repaint.");
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
		setVerbosity(log.getVerbosity());
	}
	
	public void reset() {
		stop();
		
		start();
	}
	
	public void limitedRun(final int numRuns) {
		counter = 0;
		while (!(counter > numRuns)) {
			for (ALife life : bugs) {
				if (life.getEnergy() < 0) {
					// log.trace("Life is dead");
				}
				if (life.MOVE_COUNT > numRuns) {
					break;
				}
				life.doMove();
				
			}
			counter++;
			if (isVisual) {
				// panel.repaint();
				frame.repaint();
				// System.out.println("Repainting...");
			}
			// System.out.println("Life X: " + life.getX());
			// System.out.println("Life Y: " + life.getY());
			
		}
		
	}
	
	public int getMoveCount() {
		return counter;
	}
	
	public void setVerbosity(int level) {
		log.setVerbosity(level);
		for (ALife life : bugs) {
			if (life != null) {
				life.setVerbosity(level);
			}
		}
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
	
	public void removeLife(ALife i) {
		bugs.remove(i);
	}
	
	public void removeLife(int i) {
		bugs.remove(i);
	}
	
	public void setMap(EnvironmentMap _map) {
		stop();
		for (ALife life : getLife()) {
			life.setMap(_map);
		}
		map = _map;
		
	}
}