package net.cammann.tom.fyp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.EvolutionModule;

/**
 * <p>
 * BestLifeLauncher class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class BestLifeLauncher extends JFrame {
	
	/**
	 * <p>
	 * Constructor for BestLifeLauncher.
	 * </p>
	 * 
	 * @param evoLab
	 *            a {@link net.cammann.tom.fyp.core.EvolutionModule} object.
	 * @param factory
	 *            a {@link net.cammann.tom.fyp.core.EvolutionFactory} object.
	 */
	public BestLifeLauncher(final EvolutionModule evoLab,
			final EvolutionFactory factory) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		final JPanel jp = new JPanel();
		// setUndecorated(true);
		final JButton jb = new JButton("Launch Best");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				if (evoLab.getFittestLife() != null) {
					final EnvironmentMap map = factory.createMap();
					map.addLife(factory.createLife(evoLab.getFittestLife(), map));
					final SimulationFrame frame = new SimulationFrame(map);
					
					frame.setTimerListener();
					
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							SimulationFrame.createAndShowGUI(frame);
						}
					});
				}
			}
		});
		jp.add(jb);
		
		final JButton exit = new JButton("End Simulation");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				System.exit(1);
				
			}
		});
		jp.add(exit);
		
		setContentPane(jp);
		
		setSize(200, 100);
		setLocationByPlatform(true);
		
	}
	
	/**
	 * <p>
	 * createAndShowGui.
	 * </p>
	 */
	public final void createAndShowGui() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				setVisible(true);
			}
			
		});
		
	}
}
