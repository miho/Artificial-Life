package net.cammann.tom.fyp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.utils.MapUtils;

import org.apache.log4j.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class SimulationFrame {
	private final EnvironmentMap map;
	
	static Logger logger = Logger.getLogger(SimulationFrame.class);
	
	private final JMenu removeLife = new JMenu("Remove Genotype");
	private final JMenu cloneLife = new JMenu("Clone Genotype");
	
	private JCheckBoxMenuItem showLoggingFrame;
	
	private Timer timer = null;
	
	private int simulationRate;
	
	private int counter;
	private final JFrame mainFrame;
	public static LoggingFrame loggingFrame = null;
	
	public SimulationFrame(EnvironmentMap map) {
		this.map = map;
		
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTimerListener();
		
		JPanel panel = new MapPanel(map);
		mainFrame = new JFrame();
		mainFrame.setJMenuBar(createJMenuBar());
		mainFrame.setContentPane(panel);
		mainFrame.setSize(map.getWidth() + 200, map.getHeight() + 100);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		loggingFrame = new LoggingFrame(this);
		mainFrame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
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
	
	public JMenuBar createJMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		JMenu menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_S);
		
		JMenuItem start = new JMenuItem("Start");
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
				
			}
		});
		
		menu.add(start);
		JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pause();
				
			}
		});
		
		menu.add(pause);
		
		JMenuItem stop = new JMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
				
			}
		});
		stop.setMnemonic(KeyEvent.VK_S);
		menu.add(stop);
		
		menu.addSeparator();
		
		menu.add(new JLabel("Simulation Speed"));
		
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem item = new JRadioButtonMenuItem("Super Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSimulationRate(1);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSimulationRate(10);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Normal");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSimulationRate(100);
			}
		});
		item.setSelected(true);
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Slow");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setSimulationRate(500);
			}
		});
		
		group.add(item);
		menu.add(item);
		
		menu.addSeparator();
		JMenuItem exit = new JMenuItem("Exit");
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
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
			public void itemStateChanged(ItemEvent e) {
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
		
		JMenuItem exportItem = new JMenuItem("Export Geneotype");
		
		exportItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// Save to csv
			}
		});
		
		menu.add(exportItem);
		
		JMenuItem addLifeItem = new JMenuItem("Add Geneotype");
		addLifeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Load from csv
				// or enter string of genes
				// sc.stop();
				int[] genes = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
						15, 16, 17, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19 };
				
				final ALife life = new BasicLife(genes, map);
				life.setX(new Random().nextInt((map.getWidth() + 1) / 10) * 10);
				life.setY(new Random().nextInt((map.getHeight() + 2) / 10) * 10);
				map.addLife(life);
				
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
		
		JMenuItem exportMap = new JMenuItem("Export Map");
		exportMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String home = System.getProperty("user.home");
				JFileChooser jfc = new JFileChooser(home);
				int r = jfc.showSaveDialog(mainFrame);
				if (r == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					MapUtils.SaveMap(file, map);
				}
			}
		});
		
		menu.add(exportMap);
		JMenuItem importMap = new JMenuItem("Import Map");
		importMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String home = System.getProperty("user.home");
				JFileChooser jfc = new JFileChooser(home);
				int r = jfc.showOpenDialog(mainFrame);
				if (r == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					// TODO add back in
					// sc.setMap(MapUtils.LoadMap(file));
				}
			}
		});
		menu.add(importMap);
		JMenuItem mapEdit = new JMenuItem("Edit Map");
		
		bar.add(menu);
		
		return bar;
		
	}
	
	private void addLifeToMenu(final ALife life) {
		
		final int numItems = removeLife.getItemCount();
		final JMenuItem clone_item = new JMenuItem("Life " + numItems);
		final JMenuItem remove_item = new JMenuItem("Life " + numItems);
		
		clone_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ALife clone = life.clone();
				clone.setX(new Random().nextInt((map.getWidth() + 1) / 10) * 10);
				clone.setY(new Random().nextInt((map.getHeight() + 2) / 10) * 10);
				map.addLife(clone);
				addLifeToMenu(clone);
				if (!removeLife.isEnabled()) {
					cloneLife.setEnabled(true);
					removeLife.setEnabled(true);
				}
			}
		});
		cloneLife.add(clone_item);
		
		remove_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add this back in
				// sc.removeLife(life);
				cloneLife.remove(clone_item);
				removeLife.remove(remove_item);
				
				if (removeLife.getItemCount() == 0) {
					removeLife.setEnabled(false);
					cloneLife.setEnabled(false);
				}
			}
		});
		removeLife.add(remove_item);
		
		if (!removeLife.isEnabled()) {
			cloneLife.setEnabled(true);
			removeLife.setEnabled(true);
		}
	}
	
	public void hideLogFrame() {
		loggingFrame.setVisible(false);
		showLoggingFrame.setSelected(false);
	}
	
	public void showLogFrame() {
		loggingFrame.setVisible(true);
		showLoggingFrame.setSelected(true);
	}
	
	public void setTimerListener() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("tick.");
				
				counter++;
				
				map.incrementTimeFrame();
				
				logger.trace("About to...");
				mainFrame.repaint();
				logger.trace("Repaint.");
				
			}
		});
		
	}
	
	public boolean isStopped() {
		return !timer.isRunning();
	}
	
	public void moveOnce() {
		map.incrementTimeFrame();
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
		// initSimulation();
		
		mainFrame.repaint();
		
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
}
