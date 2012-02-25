package net.cammann.tom.fyp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import net.cammann.tom.fyp.basicLife.BasicLife;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.SimulationContext;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class SimulationFrame extends JFrame {
	private final SimulationContext sc;
	
	private final JMenu removeLife = new JMenu("Remove Genotype");
	private final JMenu cloneLife = new JMenu("Clone Genotype");
	
	private JCheckBoxMenuItem showLoggingFrame;
	
	public static LoggingFrame loggingFrame = null;
	
	public SimulationFrame(SimulationContext sc) {
		this.sc = sc;
		JPanel panel = new MapPanel(sc);
		sc.RegisterFrame(this, panel);
		this.setJMenuBar(createJMenuBar());
		this.setContentPane(panel);
		this.setSize(sc.getMapWidth() + 200, sc.getMapHeight() + 100);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loggingFrame = new LoggingFrame(this);
		
	}
	
	public JMenuBar createJMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		JMenu menu = new JMenu("Simulation");
		JMenuItem start = new JMenuItem("Start");
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.start();
				
			}
		});
		
		menu.add(start);
		JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.pause();
				
			}
		});
		
		menu.add(pause);
		
		JMenuItem stop = new JMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.stop();
				
			}
		});
		menu.add(stop);
		
		menu.addSeparator();
		
		menu.add(new JLabel("Simulation Speed"));
		
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem item = new JRadioButtonMenuItem("Super Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.setSimulationRate(1);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Fast");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.setSimulationRate(10);
			}
		});
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Normal");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.setSimulationRate(100);
			}
		});
		item.setSelected(true);
		
		group.add(item);
		menu.add(item);
		item = new JRadioButtonMenuItem("Slow");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sc.setSimulationRate(500);
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
				
				final ALife life = new BasicLife(genes, sc.getMap());
				life.setX(new Random()
						.nextInt((sc.getMap().getWidth() + 1) / 10) * 10);
				life.setY(new Random()
						.nextInt((sc.getMap().getHeight() + 2) / 10) * 10);
				sc.addLife(life);
				
				addLifeToMenu(life);
			}
		});
		menu.add(addLifeItem);
		
		for (final ALife i : sc.getLife()) {
			addLifeToMenu(i);
		}
		
		menu.add(cloneLife);
		menu.add(removeLife);
		
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
				clone.setX(new Random()
						.nextInt((sc.getMap().getWidth() + 1) / 10) * 10);
				clone.setY(new Random()
						.nextInt((sc.getMap().getHeight() + 2) / 10) * 10);
				sc.addLife(clone);
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
				sc.removeLife(life);
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
}
