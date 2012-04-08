package net.cammann.tom.fyp.gp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.EvolutionModule;
import net.cammann.tom.fyp.core.SimulationContext;
import net.cammann.tom.fyp.gui.SimulationFrame;

public class BestLifeLauncher extends JFrame {
	
	private final EvolutionModule evoLab;
	
	// private final EvolutionFactory factory;
	
	public BestLifeLauncher(final EvolutionModule evoLab,
			final EvolutionFactory factory) {
		this.evoLab = evoLab;
		// this.factory = factory;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		JPanel jp = new JPanel();
		// setUndecorated(true);
		JButton jb = new JButton("Launch Best");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (evoLab.getFittestLife() != null) {
					EnvironmentMap map = factory.createMap();
					
					final SimulationContext sc = new SimulationContext(map);
					
					sc.addLife(factory.createLife(evoLab.getFittestLife(), map));
					
					sc.initSimulation();
					
					sc.setTimerListener();
					
					final SimulationFrame sf = new SimulationFrame(sc);
					
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							SimulationContext.createAndShowGUI(sf, sc);
						}
					});
				}
			}
		});
		jp.add(jb);
		
		setContentPane(jp);
		
		setSize(200, 80);
		setLocationByPlatform(true);
		
	}
	
	public void createAndShowGui() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				setVisible(true);
			}
			
		});
		
	}
}
