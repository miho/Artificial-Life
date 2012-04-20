package net.cammann.tom.fyp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

import net.cammann.tom.fyp.basicLife.BasicLife;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Beta;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimpleMap;
import net.cammann.tom.fyp.utils.MapUtils;

import org.apache.log4j.Logger;

/**
 * <p>SimulationFrame class.</p>
 *
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public class SimulationFrame {
	
	private final EnvironmentMap map;
	/**
	 * Time frames per second for simulation in slow mode.
	 */
	private static final int SLOW_SIMULATION_RATE = 500;
	/**
	 * Time frames per second for simulation in medium mode.
	 */
	private static final int MED_SIMULATION_RATE = 100;
	/**
	 * Time frames per second for simulation in fast mode.
	 */
	private static final int FAST_SIMULATION_RATE = 10;
	/**
	 * Time frames per second for simulation in very fast mode.
	 */
	private static final int V_FAST_SIMULATION_RATE = 1;
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(SimulationFrame.class);
	
	/**
	 * Remove life menu.
	 */
	private final JMenu removeLife = new JMenu("Remove Genotype");
	/**
	 * Clone life menu.
	 */
	private final JMenu cloneLife = new JMenu("Clone Genotype");
	
	/**
	 * Check box for whether logging frame is visibile or not.
	 */
	private JCheckBoxMenuItem showLoggingFrame;
	
	private Timer timer = null;
	private JPanel mapPanel = null;
	private int simulationRate;
	
	private final JFrame mainFrame;
	/** Constant <code>loggingFrame</code> */
	public static LoggingFrame loggingFrame = null;
	
	/**
	 * <p>Constructor for SimulationFrame.</p>
	 *
	 * @param map a {@link net.cammann.tom.fyp.core.EnvironmentMap} object.
	 */
	public SimulationFrame(final EnvironmentMap map) {
		this.map = map;
		
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTimerListener();
		
		mapPanel = new MapPanel(map);
		mainFrame = new JFrame();
		mainFrame.setJMenuBar(createJMenuBar());
		mainFrame.setContentPane(mapPanel);
		mainFrame.setSize(map.getWidth() + 200, map.getHeight() + 100);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		loggingFrame = new LoggingFrame(this);
		mainFrame.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (isStopped()) {
						start();
					} else {
						pause();
					}
					
				}
				if (e.getKeyCode() == KeyEvent.VK_F) {
					moveOnce();
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					stop();
				}
				if (e.getKeyCode() == KeyEvent.VK_C) {
					start();
				}
				if (e.getKeyCode() == KeyEvent.VK_X) {
					mainFrame.dispose();
				}
				
			}
		});
	}
	
	private JMenuBar createJMenuBar() {
		final JMenuBar bar = new JMenuBar();
		
		JMenu menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_S);
		
		final JMenuItem start = new JMenuItem("Start");
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				start();
				
			}
		});
		
		menu.add(start);
		final JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				pause();
				
			}
		});
		
		menu.add(pause);
		
		final JMenuItem stop = new JMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				stop();
				
			}
		});
		stop.setMnemonic(KeyEvent.VK_S);
		menu.add(stop);
		
		menu.addSeparator();
		
		menu.add(new JLabel("Simulation Speed"));
		
		final ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem item = new JRadioButtonMenuItem("Super Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setSimulationRate(V_FAST_SIMULATION_RATE);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setSimulationRate(FAST_SIMULATION_RATE);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Normal");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setSimulationRate(MED_SIMULATION_RATE);
			}
		});
		item.setSelected(true);
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Slow");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setSimulationRate(SLOW_SIMULATION_RATE);
			}
		});
		
		group.add(item);
		menu.add(item);
		
		menu.addSeparator();
		final JMenuItem exit = new JMenuItem("Exit");
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		menu.add(exit);
		
		bar.add(menu);
		
		menu = new JMenu("View");
		
		showLoggingFrame = new JCheckBoxMenuItem("Show Logger");
		showLoggingFrame.setSelected(false);
		showLoggingFrame.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					hideLogFrame();
				} else {
					showLogFrame();
				}
			}
		});
		menu.add(showLoggingFrame);
		bar.add(menu);
		
		menu = new JMenu("Life");
		
		final JMenuItem exportItem = new JMenuItem("Export Geneotype");
		
		exportItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				// TODO Auto-generated method stub
				// Save to csv
			}
		});
		
		menu.add(exportItem);
		
		final JMenuItem addLifeItem = new JMenuItem("Add Geneotype");
		addLifeItem.addActionListener(new ActionListener() {
			
			@Override
			@Beta
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub
				// Load from csv
				// or enter string of genes
				// sc.stop();
				final int[] genes = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
						13, 14, 15, 16, 17, 18, 19, 19, 19, 19, 19, 19, 19, 19,
						19, 19 };
				
				final ALife life = new BasicLife(genes, map);
				life.setX(new Random().nextInt((map.getWidth() + 1)
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE);
				life.setY(new Random().nextInt((map.getHeight() + 2)
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE);
				// map.addLife(life);
				// TODO add back in.
				
				addLifeToMenu(life);
			}
		});
		menu.add(addLifeItem);
		
		// TODO add back in
		// for (final ALife i : sc.getLife()) {
		// addLifeToMenu(i);
		// }
		
		menu.add(cloneLife);
		menu.add(removeLife);
		
		bar.add(menu);
		
		menu = new JMenu("Map");
		
		final JMenuItem exportMap = new JMenuItem("Export Map");
		exportMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final String home = System.getProperty("user.home");
				final JFileChooser jfc = new JFileChooser(home);
				final int r = jfc.showSaveDialog(mainFrame);
				if (r == JFileChooser.APPROVE_OPTION) {
					final File file = jfc.getSelectedFile();
					MapUtils.saveMap(file, map);
				}
			}
		});
		
		menu.add(exportMap);
		final JMenuItem importMap = new JMenuItem("Import Map");
		importMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final String home = System.getProperty("user.home");
				final JFileChooser jfc = new JFileChooser(home);
				final int r = jfc.showOpenDialog(mainFrame);
				if (r == JFileChooser.APPROVE_OPTION) {
					final File file = jfc.getSelectedFile();
					// TODO add back in
					// sc.setMap(MapUtils.LoadMap(file));
				}
			}
		});
		menu.add(importMap);
		// final JMenuItem mapEdit = new JMenuItem("Edit Map");
		// TODO implement this.
		bar.add(menu);
		
		return bar;
		
	}
	
	private void addLifeToMenu(final ALife life) {
		
		final int numItems = removeLife.getItemCount();
		final JMenuItem cloneItem = new JMenuItem("Life " + numItems);
		final JMenuItem removeItem = new JMenuItem("Life " + numItems);
		
		cloneItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				final ALife clone = life.clone();
				clone.setX(new Random().nextInt((map.getWidth() + 1)
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE);
				clone.setY(new Random().nextInt((map.getHeight() + 2)
						/ SimpleMap.STEP_SIZE)
						* SimpleMap.STEP_SIZE);
				// map.addLife(clone);
				// TODO ADD BACK IN
				addLifeToMenu(clone);
				if (!removeLife.isEnabled()) {
					cloneLife.setEnabled(true);
					removeLife.setEnabled(true);
				}
			}
		});
		cloneLife.add(cloneItem);
		
		removeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO add this back in
				// sc.removeLife(life);
				cloneLife.remove(cloneItem);
				removeLife.remove(removeItem);
				
				if (removeLife.getItemCount() == 0) {
					removeLife.setEnabled(false);
					cloneLife.setEnabled(false);
				}
			}
		});
		removeLife.add(removeItem);
		
		if (!removeLife.isEnabled()) {
			cloneLife.setEnabled(true);
			removeLife.setEnabled(true);
		}
	}
	
	/**
	 * <p>hideLogFrame.</p>
	 */
	public void hideLogFrame() {
		loggingFrame.setVisible(false);
		showLoggingFrame.setSelected(false);
	}
	
	/**
	 * <p>showLogFrame.</p>
	 */
	public void showLogFrame() {
		loggingFrame.setVisible(true);
		showLoggingFrame.setSelected(true);
	}
	
	/**
	 * <p>setTimerListener.</p>
	 */
	public void setTimerListener() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				logger.trace("tick.");
				
				map.incrementTimeFrame();
				
				logger.trace("About to...");
				mainFrame.repaint();
				logger.trace("Repaint.");
				
			}
		});
		
	}
	
	/**
	 * <p>isStopped.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isStopped() {
		return !timer.isRunning();
	}
	
	/**
	 * <p>moveOnce.</p>
	 */
	public void moveOnce() {
		map.incrementTimeFrame();
	}
	
	/**
	 * <p>start.</p>
	 */
	public void start() {
		timer.start();
	}
	
	/**
	 * <p>pause.</p>
	 */
	public void pause() {
		timer.stop();
	}
	
	/**
	 * <p>stop.</p>
	 */
	public void stop() {
		logger.trace("current timeframeNo: " + getMoveCount());
		timer.stop();
		logger.trace("Stopping sim");
		map.resetMap();
		logger.trace("Reset map");
		logger.trace("current timeframeNo: " + getMoveCount());
		mainFrame.repaint();
		setTimerListener();
	}
	
	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		stop();
		
		start();
	}
	
	/**
	 * <p>getMoveCount.</p>
	 *
	 * @return a int.
	 */
	public int getMoveCount() {
		return map.getTimeFrameNo();
	}
	
	/**
	 * <p>Setter for the field <code>simulationRate</code>.</p>
	 *
	 * @param rate a int.
	 */
	public void setSimulationRate(final int rate) {
		this.simulationRate = rate;
		if (timer.isRunning()) {
			pause();
			setTimerListener();
			start();
		} else {
			setTimerListener();
		}
		
	}
	
	/**
	 * <p>Getter for the field <code>simulationRate</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSimulationRate() {
		return simulationRate;
	}
	
	/**
	 * <p>setVisible.</p>
	 *
	 * @param b a boolean.
	 */
	public void setVisible(final boolean b) {
		mainFrame.setVisible(true);
		
	}
	
	/**
	 * <p>createAndShowGUI.</p>
	 *
	 * @param frame a {@link net.cammann.tom.fyp.gui.SimulationFrame} object.
	 */
	public static void createAndShowGUI(final SimulationFrame frame) {
		frame.setVisible(true);
		frame.start();
	}
	
}
